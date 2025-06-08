package tech.code.codetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.code.codetech.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    // JpaRepository: Biblioteca do Spring boot que simplifica a interação com bancos de dados
    // Product: É a entity do banco de dados
    // Integer: São os IDs (inteiros)
}
