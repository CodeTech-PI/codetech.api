package tech.code.codetech.service.autenticacao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioLombardiTokenDto {

    private Long userId;
    private String nomme;
    private String email;
    private String token;
}
