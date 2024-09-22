package tech.code.codetech.dto.faturamento.response;


import lombok.Builder;
import lombok.Data;
import tech.code.codetech.model.OrdemServico;

@Data
@Builder
public class FaturamentoResponseDto {

    private Integer id;
    private Double lucro;
    private OrdemServico ordemServico;
}
