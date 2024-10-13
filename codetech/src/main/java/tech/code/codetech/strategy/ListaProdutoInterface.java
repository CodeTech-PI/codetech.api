package tech.code.codetech.strategy;

import tech.code.codetech.model.ListaProduto;

import java.util.List;

public interface ListaProdutoInterface {

   List<ListaProduto> saveAll(List<ListaProduto> listaProduto);

   ListaProduto findById(Integer id);

   List<ListaProduto> update(List<ListaProduto> listaProduto);

   boolean delete(Integer id);

   List<ListaProduto> buscarListaProdutosPeloAgendamento(Integer idAgendamento);
}