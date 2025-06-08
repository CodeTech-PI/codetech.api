package tech.code.codetech.service.usuario.autenticacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.code.codetech.model.UsuarioLombardi;
import tech.code.codetech.repository.UsuarioLombardiRepository;
import tech.code.codetech.service.autenticacao.dto.UsuarioLombardiDetalhesDto;
import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService  {

    @Autowired
    private UsuarioLombardiRepository usuarioLombardiRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioLombardi> usuarioOpt = usuarioLombardiRepository.findByEmail(username);

        if (usuarioOpt.isEmpty()) {
            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
        }

        return new UsuarioLombardiDetalhesDto(usuarioOpt.get());
    }
}
