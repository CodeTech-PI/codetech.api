package tech.code.codetech.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.dto.ordem.OrdemServicoLucroDto;
import tech.code.codetech.exception.naoencontrado.OrdemServicoNaoEncontradaException;
import tech.code.codetech.model.ListaProduto;
import tech.code.codetech.model.OrdemServico;
import tech.code.codetech.repository.OrdemServicoRepository;
import tech.code.codetech.strategy.FaturamentoInterface;
import tech.code.codetech.strategy.OrdemServicoInterface;
import tech.code.codetech.strategy.ProdutoInterface;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    public OrdemServico save(OrdemServico ordemServico) {
        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServico findById(Integer id) {
        return ordemServicoRepository.findById(id).orElseThrow(OrdemServicoNaoEncontradaException::new);
    }

    public List<OrdemServico> findAll() {
        return ordemServicoRepository.findAll();
    }

    @Transactional
    public OrdemServico update(Integer id, OrdemServico ordemServico) {
        if (!ordemServicoRepository.existsById(id)) {
            throw new OrdemServicoNaoEncontradaException();
        }
        ordemServico.setId(id);
        return ordemServicoRepository.save(ordemServico);
    }

    @Transactional
    public boolean delete(Integer id) {
        if (!ordemServicoRepository.existsById(id)) {
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
        return buildOrdemServicoLucroDto(ordemServicoSalva, lucro);
    }

    @Override
    public OrdemServicoLucroDto atualizarOrdemServico(OrdemServico model, Integer id) {
        OrdemServico ordemServicoSalva = update(id, model);
        BigDecimal lucro = faturamentoService.recalcularLucro(ordemServicoSalva);
        return buildOrdemServicoLucroDto(ordemServicoSalva, lucro);
    }

    private OrdemServicoLucroDto buildOrdemServicoLucroDto(OrdemServico ordemServico, BigDecimal lucro) {
        return OrdemServicoLucroDto.builder()
                .ordemServico(ordemServico)
                .lucro(lucro)
                .build();
    }

    @Override
    public List<OrdemServicoLucroDto> buscarTodos() {
        List<OrdemServico> ordemServicos = findAll();
        if (ordemServicos.isEmpty()) {
            return new ArrayList<>();
        }
        List<OrdemServicoLucroDto> ordemServicoLucroDtos = new ArrayList<>();
        for (OrdemServico ordemServico : ordemServicos) {
            BigDecimal lucro = faturamentoService.buscarPorOrdemServico(ordemServico).getLucro();
            ordemServicoLucroDtos.add(buildOrdemServicoLucroDto(ordemServico, lucro));
        }
        return ordemServicoLucroDtos;
    }

    @Override
    public OrdemServicoLucroDto buscarPorId(Integer id) {
        OrdemServico ordemServico = findById(id);
        BigDecimal lucro = faturamentoService.buscarPorOrdemServico(ordemServico).getLucro();
        return buildOrdemServicoLucroDto(ordemServico, lucro);
    }
}