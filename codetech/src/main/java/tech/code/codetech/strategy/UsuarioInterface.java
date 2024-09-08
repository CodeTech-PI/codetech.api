package tech.code.codetech.strategy;

import tech.code.codetech.model.Usuario;

import java.util.List;

public interface UsuarioInterface {
    public Usuario save(Usuario usuario);

    public List<Usuario> findAll();

    public Usuario findById(Integer id);

    public Usuario update(Integer id, Usuario usuario);

    public boolean delete(Integer id);

}
