package tech.code.codetech.repository;

import tech.code.codetech.model.Product;

import java.util.List;

public interface ProdutoInterface {
    public Product save(Product produto);

    public List<Product> findAll();

    public Product findById(Integer id);

    public Product update(Integer id, Product product);

    public boolean delete(Integer id);
}
