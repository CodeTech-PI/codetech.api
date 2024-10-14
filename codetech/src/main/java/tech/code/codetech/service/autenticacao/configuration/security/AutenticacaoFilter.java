package tech.code.codetech.service.autenticacao.configuration.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.code.codetech.service.autenticacao.AutenticacaoService;
import tech.code.codetech.service.autenticacao.configuration.security.jwt.GerenciadorTokenJwt;

import java.io.IOException;
import java.util.Objects;

// Classe responsável por interceptar as requisições HTTP, para verificar a presença e a validade do token JWT

public class AutenticacaoFilter extends OncePerRequestFilter {
// OncePerRequestFilter garante que seja executada somente UMA VEZ por requisição

    //Logger registra eventos
    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoFilter.class);

    private final AutenticacaoService autenticacaoService;

    private final GerenciadorTokenJwt jwtTokenManager;

    public AutenticacaoFilter(AutenticacaoService autenticacaoService, GerenciadorTokenJwt jwtTokenManager) {
        this.autenticacaoService = autenticacaoService;
        this.jwtTokenManager = jwtTokenManager;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest requisicao, HttpServletResponse resposta, FilterChain filterChain) throws ServletException, IOException {
    // Pega o token
    //Requisição, resposta, permissão "compartilhar" ambas

        String username = null;
        String jwtToken = null;


        //Recupera o cabeçalho "Authorization" da requisição que possuí o token
        String requestTokenHeader = requisicao.getHeader("Authorization");

        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {

            //Remove o "Berer " e armazena somente o token
            jwtToken = requestTokenHeader.substring(7);

            try {

                // Obtem o nome de usuário associado ao token
                username = jwtTokenManager.getUsernameFromToken(jwtToken);

                //se o token estiver expirado
            } catch (ExpiredJwtException exception) {

                LOGGER.info("[FALHA AUTENTICACAO] - Token expirado, usuario: {} - {}",
                        exception.getClaims().getSubject(), exception.getMessage());

                LOGGER.trace("[FALHA AUTENTICACAO] - srack trace: %s", exception);

                resposta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }

            // Verifica se a autenticação já está presente
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                addUsernameInContext(requisicao, username, jwtToken);
            } else {
                resposta.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            filterChain.doFilter(requisicao, resposta);
        }

    }

    //Cria um objeto de autenticação                  requisição,         usuário,           token
    private void addUsernameInContext(HttpServletRequest request, String username, String jwtToken) {

        //Pega os dados do usuário
        UserDetails detalhesDoUsuario = autenticacaoService.loadUserByUsername(username);

        //Valida se o token é valido de acordo com os dados do usuário
        if (jwtTokenManager.validateToken(jwtToken, detalhesDoUsuario)) {

            //Cria um objeto com o Usuario, null, e as permissões dele
            UsernamePasswordAuthenticationToken usuarioAutenticado = new UsernamePasswordAuthenticationToken(
                    detalhesDoUsuario, null, detalhesDoUsuario.getAuthorities()
            );

            //Adiciona detalhes da requisição
            usuarioAutenticado.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usuarioAutenticado);
        }
    }
}
