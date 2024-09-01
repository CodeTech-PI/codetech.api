package tech.code.codetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.code.codetech.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {
}
