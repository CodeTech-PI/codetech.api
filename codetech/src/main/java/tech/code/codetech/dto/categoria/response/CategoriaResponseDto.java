package tech.code.codetech.dto.categoria.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoriaResponseDto {
    private Integer id;
    private String nome;

}
