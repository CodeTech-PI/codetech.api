package tech.code.codetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.code.codetech.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    // JpaRepository: Biblioteca do Spring boot que simplifica a interação com bancos de dados
    // Product: É a entity do banco de dados
    // Integer: São os IDs (inteiros)
}
