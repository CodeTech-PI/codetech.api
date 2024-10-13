package tech.code.codetech.dto.unidade.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnidadeResponseDto {
    private Integer id;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;
    private Integer num;
}
