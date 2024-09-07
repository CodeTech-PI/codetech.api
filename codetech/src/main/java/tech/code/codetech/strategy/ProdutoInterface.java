package tech.code.codetech.strategy;

import tech.code.codetech.model.Produto;

import java.util.List;

public interface ProdutoInterface {
    Produto save(Produto produto);

    List<Produto> findAll();

    Produto findById(Integer id);

    Produto update(Integer id, Produto product);

    boolean delete(Integer id);
}
