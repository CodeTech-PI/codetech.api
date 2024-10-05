package tech.code.codetech.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.dto.ordem.OrdemServicoLucroDto;
import tech.code.codetech.model.OrdemServico;
import tech.code.codetech.repository.OrdemServicoRepository;
import tech.code.codetech.strategy.FaturamentoInterface;
import tech.code.codetech.strategy.OrdemServicoInterface;
import tech.code.codetech.strategy.ProdutoInterface;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrdemServicoService implements OrdemServicoInterface {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ProdutoInterface produtoService;

    @Autowired
    private FaturamentoInterface faturamentoService;

    @Transactional
    public OrdemServico save(OrdemServico ordemServico){
        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServico findById(Integer id){
        return ordemServicoRepository.findById(id).orElse(null);
    }

    public List<OrdemServico> findAll(){
        return ordemServicoRepository.findAll();
    }

    @Transactional
    public OrdemServico update(Integer id, OrdemServico ordemServico){
        if(!ordemServicoRepository.existsById(id)){
            return null;
        }
        ordemServico.setId(id);
        return ordemServicoRepository.save(ordemServico);
    }

    @Transactional
    public boolean delete(Integer id){
        if(!ordemServicoRepository.existsById(id)){
            return false;
        }
        ordemServicoRepository.deleteById(id);
        return true;
    }

    @Override
    public OrdemServicoLucroDto executarOrdemServico(OrdemServico ordemServico) {
        OrdemServico ordemServicoSalva = save(ordemServico);
        produtoService.darBaixaEstoque(ordemServicoSalva.getAgendamento().getId());
        BigDecimal lucro = faturamentoService.calcularLucro(ordemServicoSalva);

        return OrdemServicoLucroDto.builder()
                .ordemServico(ordemServicoSalva)
                .lucro(lucro)
                .build();
    }
}