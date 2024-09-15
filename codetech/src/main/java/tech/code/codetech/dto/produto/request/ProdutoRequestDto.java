package tech.code.codetech.dto.produto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class ProdutoRequestDto { //Front -> Backend

    @NotNull
    @PositiveOrZero
    private Integer quantidade;

    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @NotBlank
    private String categoria;

    @NotBlank
    private String unidadeDeMedida;

    @NotNull
    @Positive
    private Double preco;
}
