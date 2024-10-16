package tech.code.codetech.api.configuration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.code.codetech.api.configuration.security.jwt.GerenciadorTokenJwt;
import tech.code.codetech.service.usuario.autenticacao.AutenticacaoService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AutenticacaoService autenticacaoService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public JwtAuthFilter(GerenciadorTokenJwt gerenciadorTokenJwt, AutenticacaoService autenticacaoService) {
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
        this.autenticacaoService = autenticacaoService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            // Verifique se o token está vazio
            if (token.isEmpty()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token não fornecido");
                return;
            }

            try {
                // Verifica se o token é válido
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtSecret) // Usando a chave secreta para validar o token
                        .parseClaimsJws(token) // Tenta analisar o token
                        .getBody();

                String username = claims.getSubject(); // Obtém o nome de usuário a partir dos claims

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.autenticacaoService.loadUserByUsername(username);

                    // Valida o token com o método que você já tem
                    if (gerenciadorTokenJwt.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    } else {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                        return;
                    }
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
