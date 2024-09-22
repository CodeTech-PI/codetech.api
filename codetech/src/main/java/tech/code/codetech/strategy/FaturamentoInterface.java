package tech.code.codetech.strategy;

import tech.code.codetech.model.Faturamento;

public interface FaturamentoInterface {

    Faturamento save(Faturamento faturamento);

    Faturamento findById(Integer id);

    Faturamento update(Integer id, Faturamento faturamento);

    boolean delete(Integer id);

}
