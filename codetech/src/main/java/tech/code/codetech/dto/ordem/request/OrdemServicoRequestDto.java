package tech.code.codetech.dto.ordem.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import tech.code.codetech.model.Agendamento;
import tech.code.codetech.model.Produto;

@Data
@Builder
public class OrdemServicoRequestDto {

    @NotNull
    @Positive
    private Double valorAgendamento;

    @NotNull
    private Agendamento agendamento;

    @NotNull
    private Produto produto;

}
