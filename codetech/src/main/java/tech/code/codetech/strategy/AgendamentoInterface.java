package tech.code.codetech.strategy;

import tech.code.codetech.model.Agendamento;
import java.util.List;
public interface AgendamentoInterface {
    List<Agendamento> findAll();
    Agendamento findById(Integer id);
    Agendamento update(Integer id, Agendamento agendamento);
    boolean delete(Integer id);
}
