package tech.code.codetech.dto.produto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoResponseDto { // Backend -> Front end
    private Integer id;
    private Integer quantidade;
    private String nome;
    private String descricao;
    private String categoria;
    private String unidadeMedida;
    private Double preco;
}
