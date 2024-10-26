package tech.code.codetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.code.codetech.model.Faturamento;

import java.util.Optional;

public interface FaturamentoRepository extends JpaRepository<Faturamento, Integer> {

    Optional<Faturamento> findByOrdemServicoId(Integer id);

}
