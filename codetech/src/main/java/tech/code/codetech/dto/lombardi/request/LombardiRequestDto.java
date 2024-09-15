package tech.code.codetech.dto.lombardi.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LombardiRequestDto {

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(max = 30)
    private String senha;

    @NotBlank
    @Size(min = 3, max = 100)
    private String nome;
}
