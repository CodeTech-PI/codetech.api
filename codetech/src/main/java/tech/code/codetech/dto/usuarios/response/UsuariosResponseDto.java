package tech.code.codetech.dto.usuarios.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UsuariosResponseDto {
    private Integer id;
    private String nomeUsuario;
    private String cpf;
    private String telefone;
    private String email;
    private LocalDate dataNascimento;
}
