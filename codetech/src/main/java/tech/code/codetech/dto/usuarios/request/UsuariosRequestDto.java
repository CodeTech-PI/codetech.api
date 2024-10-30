package tech.code.codetech.dto.usuarios.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
@Builder
public class UsuariosRequestDto {

    @NotBlank
    @Size(min = 3, max = 100)
    private String nome;

    @NotBlank
    @Size(max = 14)
//    @CPF
    private String cpf;

    @NotBlank
    @Size(max = 11)
    private String telefone;

    @NotBlank
    @Size(max = 100)
    private String email;

    @NotNull
    private LocalDate dataNascimento;
}
