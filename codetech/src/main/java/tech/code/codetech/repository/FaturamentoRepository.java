package tech.code.codetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.code.codetech.model.Faturamento;

public interface FaturamentoRepository extends JpaRepository<Faturamento, Integer> {

}
