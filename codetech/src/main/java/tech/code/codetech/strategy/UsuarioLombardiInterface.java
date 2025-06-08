package tech.code.codetech.strategy;

import org.springframework.data.jpa.repository.Query;
import tech.code.codetech.model.UsuarioLombardi;

public interface UsuarioLombardiInterface {

    UsuarioLombardi save(UsuarioLombardi usuarioLombardi);
    UsuarioLombardi update(Integer id, UsuarioLombardi usuarioLombardi);
    boolean delete(Integer id);
    UsuarioLombardi findById(Integer id);

}
