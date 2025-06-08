package tech.code.codetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.code.codetech.model.Usuario;
import tech.code.codetech.model.UsuarioLombardi;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
