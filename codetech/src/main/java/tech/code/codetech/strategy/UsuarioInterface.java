package tech.code.codetech.strategy;

import tech.code.codetech.model.Usuario;

import java.util.List;

public interface UsuarioInterface {
    Usuario save(Usuario usuario);
    List<Usuario> findAll();
    Usuario findById(Integer id);
    Usuario update(Integer id, Usuario usuario);
    boolean delete(Integer id);
}
