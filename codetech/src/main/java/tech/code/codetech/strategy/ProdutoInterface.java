package tech.code.codetech.strategy;

import tech.code.codetech.model.Produto;
import java.util.List;

public interface ProdutoInterface extends Ordenavel {
    @Override
    <E extends Comparable<E>> List<E> ordenar(List<E> lista);
    Produto save(Produto produto);
    List<Produto> findAll();
    Produto findById(Integer id);
    Produto update(Integer id, Produto product);
    boolean delete(Integer id);
}