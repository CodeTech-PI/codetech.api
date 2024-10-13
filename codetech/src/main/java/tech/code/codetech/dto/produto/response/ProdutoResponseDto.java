package tech.code.codetech.dto.produto.response;

import lombok.Builder;
import lombok.Data;
import tech.code.codetech.model.Categoria;

import java.math.BigDecimal;

@Data
@Builder
public class ProdutoResponseDto { // Backend -> Front end
    private Integer id;
    private Integer quantidade;
    private String nome;
    private String descricao;
    private String unidadeMedida;
    private BigDecimal preco;
    private Categoria categoria;

    
//    private CategoriaResponseDto categoria;

//    @Data
//    @Builder
//    public static class CategoriaResponseDto {
//        private Integer id;
//        private String nome;
//    }
}