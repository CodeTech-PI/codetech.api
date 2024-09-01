package tech.code.codetech.strategy;

import tech.code.codetech.model.Produto;

import java.util.List;

public interface ProdutoInterface {
    public Produto save(Produto produto);

    public List<Produto> findAll();

    public Produto findById(Integer id);

    public Produto update(Integer id, Produto product);

    public boolean delete(Integer id);
}
