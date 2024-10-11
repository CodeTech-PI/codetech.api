package tech.code.codetech.service.autenticacao.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.code.codetech.model.UsuarioLombardi;

import java.util.Collection;
import java.util.List;

public class UsuarioLombardiDetalhesDto implements UserDetails {
// UserDetails é uma interface
// Responsável por encapsular dados

    private String email;
    private String senha;
    private String nome;

    public UsuarioLombardiDetalhesDto(UsuarioLombardi usuarioLombardi) {
        this.email = usuarioLombardi.getEmail();
        this.senha = usuarioLombardi.getSenha();
        this.nome = usuarioLombardi.getNome();
    }

    //Retorna as permissões do usuuário
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

    //Indica se a conta do usuário foi expirada ou não
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //Indica se a conta foi bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //Indica se as credenciais do usuário (senha) não estão expiradas
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //Indica se a conta do usuário está habilitada
    @Override
    public boolean isEnabled() {
        return true;
    }
}
