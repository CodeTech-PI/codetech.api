package tech.code.codetech.service.autenticacao;

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
public class AutenticacaoService implements UserDetailsService {
// UserDetailsService é uma interface
// Responsável por carregar informações do usuário durante o processo de autenticação

    @Autowired
    private UsuarioLombardiRepository usuarioLombardiRepository;

// Verifica se o email existe no banco e exibe as informações
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioLombardi> usuarioLombardiOptional = usuarioLombardiRepository.findByEmail(username);

        if (usuarioLombardiOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("usuário %s não encontrado", username));
        }
        return new UsuarioLombardiDetalhesDto(usuarioLombardiOptional.get());
    }


}
