package tech.code.codetech.service.autenticacao.dto;

import org.springframework.security.core.userdetails.UserDetails;
import tech.code.codetech.model.UsuarioLombardi;

public class LombardiDetalhesDto implements UserDetails {

    private String email;
    private String senha;
    private String nome;

    public LombardiDetalhesDto(UsuarioLombardi usuarioLombardi) {
        this.email = usuarioLombardi.getEmail();
        this.senha = usuarioLombardi.getSenha();
        this.nome = usuarioLombardi.getNome();
    }
}
