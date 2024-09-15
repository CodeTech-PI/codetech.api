package tech.code.codetech.dto.ordem.response;

import lombok.Builder;
import lombok.Data;
import tech.code.codetech.model.Agendamento;
import tech.code.codetech.model.Produto;

@Data
@Builder
public class OrdemServicoResponseDto {
    private Integer id;
    private Double valorAgendamento;
    private Agendamento agendamento;
    private Produto produto;
}
