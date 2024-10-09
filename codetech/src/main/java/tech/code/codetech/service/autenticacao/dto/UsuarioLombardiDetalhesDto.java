package tech.code.codetech.service.autenticacao.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.code.codetech.model.UsuarioLombardi;

import java.util.Collection;
import java.util.List;

public class UsuarioLombardiDetalhesDto implements UserDetails {

    private String email;
    private String senha;
    private String nome;

    public UsuarioLombardiDetalhesDto(UsuarioLombardi usuarioLombardi) {
        this.email = usuarioLombardi.getEmail();
        this.senha = usuarioLombardi.getSenha();
        this.nome = usuarioLombardi.getNome();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
