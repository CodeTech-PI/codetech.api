package tech.code.codetech.strategy;
import tech.code.codetech.model.Agendamento;
import java.util.List;
public interface AgendamentoInterface {
    List<Agendamento> findAll();
    Agendamento findById(Integer id);
    Agendamento update(Integer id, Agendamento agendamento);
    boolean delete(Integer id);
import tech.code.codetech.model.Agendamento;
import java.util.List;

public interface AgendamentoInterface {
    // SELECT * FROM Agendamento;
    public List<Agendamento> findAll();

    // SELECT * FROM Agendamento WHERE ID = ?
    public Agendamento findById(Integer id);

    // UPDATE * FROM Agendamento  WHERE ID = ?
    public Agendamento update(Integer id, Agendamento agendamento);
    public boolean delete(Integer id);