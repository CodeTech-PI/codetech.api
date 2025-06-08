package tech.code.codetech.dto.faturamento.response;


import lombok.Builder;
import lombok.Data;
import tech.code.codetech.model.OrdemServico;

import java.math.BigDecimal;

@Data
@Builder
public class FaturamentoResponseDto {

    private Integer id;
    private BigDecimal lucro;
    private OrdemServico ordemServico;
}
