package tech.code.codetech.strategy;

import tech.code.codetech.model.Categoria;

import java.util.List;

public interface CategoriaInterface {

    Categoria save(Categoria categoria);

    List<Categoria> findAll();

    Categoria findById(Integer id);

    Categoria update(Integer id, Categoria categoria);

    boolean delete(Integer id);

}
