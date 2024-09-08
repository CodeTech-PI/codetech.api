package tech.code.codetech.strategy;

import tech.code.codetech.model.UsuarioLombardi;

public interface UsuarioLombardiInterface {

    UsuarioLombardi save(UsuarioLombardi usuarioLombardi);
    UsuarioLombardi update(Integer id, UsuarioLombardi usuarioLombardi);
    boolean delete(Integer id);
    UsuarioLombardi findById(Integer id);
}
