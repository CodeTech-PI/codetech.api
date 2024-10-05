package tech.code.codetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.code.codetech.model.ListaProduto;

import java.util.List;

public interface ListaProdutoRepository extends JpaRepository<ListaProduto, Integer> {

    List<ListaProduto> findByAgendamentoId (Integer id);
}
