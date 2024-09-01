package tech.code.codetech.strategy;

import tech.code.codetech.model.Agendamento;
import java.util.List;
public interface AgendamentoInterface {
    public List<Agendamento> findAll();
    public Agendamento findById(Integer id);
    public Agendamento update(Integer id, Agendamento agendamento);
    public boolean delete(Integer id);
}
