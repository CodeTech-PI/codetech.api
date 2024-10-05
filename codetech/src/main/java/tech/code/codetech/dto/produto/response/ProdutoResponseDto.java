package tech.code.codetech.dto.produto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProdutoResponseDto { // Backend -> Front end
    private Integer id;
    private Integer quantidade;
    private String nome;
    private String descricao;
    private String categoria;
    private String unidadeMedida;
    private BigDecimal preco;
}
