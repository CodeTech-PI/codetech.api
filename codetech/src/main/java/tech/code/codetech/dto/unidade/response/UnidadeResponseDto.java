package tech.code.codetech.dto.unidade.response;


import lombok.Builder;
import lombok.Data;
import tech.code.codetech.api.controller.generico.StatusUnidade;

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
    private StatusUnidade status;
}
