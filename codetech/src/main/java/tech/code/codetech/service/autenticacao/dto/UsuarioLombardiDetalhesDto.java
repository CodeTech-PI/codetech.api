package tech.code.codetech.service.autenticacao.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.code.codetech.model.UsuarioLombardi;

import java.util.Collection;
import java.util.List;

public class UsuarioLombardiDetalhesDto implements UserDetails {

    private final String nome;
    private final String email;
    private final String senha;

    public UsuarioLombardiDetalhesDto(UsuarioLombardi usuarioLombardi) {
        this.nome = usuarioLombardi.getNome();
        this.email = usuarioLombardi.getEmail();
        this.senha = usuarioLombardi.getSenha();
    }

    public String getNome() {
        return nome;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
