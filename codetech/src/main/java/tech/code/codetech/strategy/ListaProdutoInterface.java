package tech.code.codetech.strategy;

import tech.code.codetech.model.ListaProduto;

public interface ListaProdutoInterface {

   ListaProduto save(ListaProduto listaProduto);

   ListaProduto findById(Integer id);

   ListaProduto update(Integer id, ListaProduto listaProduto);

   boolean delete(Integer id);

}