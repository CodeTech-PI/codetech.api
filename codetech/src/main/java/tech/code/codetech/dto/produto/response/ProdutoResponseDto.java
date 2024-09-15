package tech.code.codetech.dto.produto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProdutoResponseDto { // Backend -> Front end
    private Integer id;
    private Integer quantidade;
    private String nome;
    private String descricao;
    private String categoria;
    private String unidadeDeMedida;
    private Double preco;
}
