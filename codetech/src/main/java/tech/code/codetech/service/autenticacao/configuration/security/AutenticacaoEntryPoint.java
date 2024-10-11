package tech.code.codetech.service.autenticacao.configuration.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AutenticacaoEntryPoint implements AuthenticationEntryPoint {
// Interface do Spring Security
// Gerencia a resposta quando a tentativa de autenticação falha

    //Verifica o tipo da exceção que foi lançada quando a autenticação falhar
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        if (authException.getClass().equals(BadCredentialsException.class) || authException.getClass().equals(InsufficientAuthenticationException.class)) {

            //Se falhou por insuficiencia ou credenciais inválidas
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {

            // Falhou por outro motivo
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
