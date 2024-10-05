package tech.code.codetech.dto.ordem.response;

import lombok.Builder;
import lombok.Data;
import tech.code.codetech.model.Agendamento;
import tech.code.codetech.model.Produto;

import java.math.BigDecimal;

@Data
@Builder
public class OrdemServicoResponseDto {
    private Integer id;
    private BigDecimal valorTatuagem;
    private BigDecimal lucro;
    private Agendamento agendamento;
}
