package tech.code.codetech.dto.unidade.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnidadeRequestDto {

    @NotBlank
    private String cep;

    @NotBlank
    @Size(max = 75)
    private String logradouro;

    @NotBlank
    @Size(max = 75)
    private String bairro;

    @NotBlank
    @Size(max = 50)
    private String cidade;

    @NotBlank
    @Size(max = 50)
    private String estado;

    @NotBlank
    private String complemento;

    @NotNull
    private Integer num;
}
