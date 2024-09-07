package tech.code.codetech.strategy;

import tech.code.codetech.model.UsuarioLombardi;

public interface UsuarioLombardiInterface {

    public UsuarioLombardi save(UsuarioLombardi usuarioLombardi);
    public UsuarioLombardi update(Integer id, UsuarioLombardi usuarioLombardi);
    public boolean delete(Integer id);
    public UsuarioLombardi findById(Integer id);



}
