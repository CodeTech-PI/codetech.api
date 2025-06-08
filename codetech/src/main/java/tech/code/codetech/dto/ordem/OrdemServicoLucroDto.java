package tech.code.codetech.dto.ordem;

import lombok.Builder;
import lombok.Data;
import tech.code.codetech.model.OrdemServico;

import java.math.BigDecimal;

@Data
@Builder
public class OrdemServicoLucroDto {

    private OrdemServico ordemServico;
    private BigDecimal lucro;
}
