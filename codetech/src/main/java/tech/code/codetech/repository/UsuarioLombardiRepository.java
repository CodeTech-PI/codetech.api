package tech.code.codetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.code.codetech.model.UsuarioLombardi;

import java.util.Optional;

public interface UsuarioLombardiRepository extends JpaRepository<UsuarioLombardi, Integer> {
    UsuarioLombardi findByEmailAndSenha(String email, String password);

    Optional<UsuarioLombardi> findByEmail(String email);
}
