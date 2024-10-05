package tech.code.codetech.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.model.Faturamento;
import tech.code.codetech.model.ListaProduto;
import tech.code.codetech.model.OrdemServico;
import tech.code.codetech.repository.FaturamentoRepository;
import tech.code.codetech.strategy.FaturamentoInterface;
import tech.code.codetech.strategy.ListaProdutoInterface;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class FaturamentoService implements FaturamentoInterface {

    @Autowired
    private FaturamentoRepository faturamentoRepository;

    @Autowired
    private ListaProdutoInterface listaProdutoService;

    @Transactional
    public Faturamento save(Faturamento faturamento) {
        return faturamentoRepository.save(faturamento);
    }

    public Faturamento findById(Integer id) {
        return faturamentoRepository.findById(id).orElse(null);
    }

    public List<Faturamento> findAll() {
        return faturamentoRepository.findAll();
    }

    public Faturamento update(Integer id, Faturamento faturamento) {
        if (!faturamentoRepository.existsById(id)) {
            return null;
        }
        faturamento.setId(id);
        return faturamentoRepository.save(faturamento);
    }

    public boolean delete(Integer id) {
        if (!faturamentoRepository.existsById(id)) {
            return false;
        }
        faturamentoRepository.deleteById(id);
        return true;
    }

    @Override
    public BigDecimal calcularLucro(OrdemServico ordemServico) {
        List<ListaProduto> listaProdutos = listaProdutoService
                .buscarListaProdutosPeloAgendamento(ordemServico.getAgendamento().getId());

        BigDecimal gastoTotal = calcularGastoTotal(listaProdutos);

        BigDecimal lucro = ordemServico.getValorTatuagem().subtract(gastoTotal);

        salvarFaturamento(lucro, ordemServico);

        return lucro;
    }

    private BigDecimal calcularGastoTotal(List<ListaProduto> listaProdutos) {
        BigDecimal gastoTotal = BigDecimal.ZERO;

        for (ListaProduto listaProduto : listaProdutos) {
            BigDecimal preco = listaProduto.getProduto().getPreco();
            BigDecimal quantidade = BigDecimal.valueOf(listaProduto.getQuantidadeProdutos());
            BigDecimal gastoProduto = preco.multiply(quantidade);
            gastoTotal = gastoTotal.add(gastoProduto);
        }

        return gastoTotal;
    }

    private void salvarFaturamento(BigDecimal lucro, OrdemServico ordemServico) {
        Faturamento faturamento = Faturamento.builder()
                .lucro(lucro)
                .ordemServico(ordemServico)
                .build();
        save(faturamento);
    }
}
