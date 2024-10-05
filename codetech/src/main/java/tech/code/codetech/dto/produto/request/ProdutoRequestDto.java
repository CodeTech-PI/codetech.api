package tech.code.codetech.dto.produto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder
public class ProdutoRequestDto { //Front -> Backend

    @NotNull
    @PositiveOrZero
    private Integer quantidade;

    @NotBlank
    @Size(max = 100)
    private String nome;

    @NotBlank
    @Size(max = 75)
    private String descricao;

    @NotBlank
    @Size(max = 30)
    private String categoria;

    @NotBlank
    private String unidadeMedida;

    @NotNull
    @Positive
    private BigDecimal preco;
}
