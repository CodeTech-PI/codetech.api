package tech.code.codetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.code.codetech.api.controller.generico.StatusUnidade;
import tech.code.codetech.model.Unidade;

import java.util.List;

public interface UnidadeRepository extends JpaRepository<Unidade, Integer> {

    List<Unidade> findAllByOrderByIdAsc();

    @Query(value = "SELECT * FROM endereco WHERE status_unidade = :statusUnidade", nativeQuery = true)
    List<Unidade> findAllByStatusUnidade(@Param("statusUnidade") StatusUnidade statusUnidade);

    @Query("SELECT COUNT(u) FROM Unidade u WHERE u.status = 'INOPERANTE'")
    int quantidadeDeUnidades();

}
