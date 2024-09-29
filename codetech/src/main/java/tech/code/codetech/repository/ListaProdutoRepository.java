package tech.code.codetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.code.codetech.model.ListaProduto;

public interface ListaProdutoRepository extends JpaRepository<ListaProduto, Integer> {
}
