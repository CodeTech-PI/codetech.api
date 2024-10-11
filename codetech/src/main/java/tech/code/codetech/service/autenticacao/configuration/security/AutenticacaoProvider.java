package tech.code.codetech.service.autenticacao.configuration.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import tech.code.codetech.service.autenticacao.AutenticacaoService;

public class AutenticacaoProvider implements AuthenticationProvider {
// Define como realizar a autenticação
// Responsável por validar as credenciais

    private final AutenticacaoService usuarioAutorizacaoService;
    private final PasswordEncoder passwordEncoder;

    public AutenticacaoProvider(AutenticacaoService usuarioAutorizacaoService, PasswordEncoder passwordEncoder) {
        this.usuarioAutorizacaoService = usuarioAutorizacaoService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
    //Recebe um objeto que contém o nome de usuário e a senha.

        final String username = authentication.getName();
        final String passwprd = authentication.getCredentials().toString();

        UserDetails detalhesDoUsuario = this.usuarioAutorizacaoService.loadUserByUsername(username);

        // A senha do usuario é igual a senha pega pelo Authentication
        if (this.passwordEncoder.matches(passwprd, detalhesDoUsuario.getPassword())){

            //Cria um usuário autenticado
            return new UsernamePasswordAuthenticationToken(detalhesDoUsuario, null, detalhesDoUsuario.getAuthorities());
        } else {
            throw new BadCredentialsException("Usuário ou senha inválidos");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
