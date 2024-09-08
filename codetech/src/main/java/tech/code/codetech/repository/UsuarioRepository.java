package tech.code.codetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.code.codetech.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
