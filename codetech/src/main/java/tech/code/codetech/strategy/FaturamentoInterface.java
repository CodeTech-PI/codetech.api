package tech.code.codetech.strategy;

import tech.code.codetech.model.Faturamento;
import tech.code.codetech.model.OrdemServico;

import java.math.BigDecimal;

public interface FaturamentoInterface {

    Faturamento save(Faturamento faturamento);

    Faturamento findById(Integer id);

    Faturamento update(Integer id, Faturamento faturamento);

    boolean delete(Integer id);

    BigDecimal calcularLucro(OrdemServico ordemServico);

    BigDecimal recalcularLucro(OrdemServico ordemServico);

    BigDecimal calcularLucro(OrdemServico ordemServico, Integer id);

    Faturamento buscarPorOrdemServico (OrdemServico ordemServico);

}
