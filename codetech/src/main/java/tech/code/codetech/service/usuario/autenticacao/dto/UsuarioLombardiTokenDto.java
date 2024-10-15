package tech.code.codetech.service.autenticacao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLombardiTokenDto {

    private Integer userId;
    private String nome;
    private String email;
    private String token;
}
