package tech.code.codetech.dto.ordem.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import tech.code.codetech.model.Agendamento;
import tech.code.codetech.model.Produto;

import java.math.BigDecimal;

@Data
@Builder
public class OrdemServicoRequestDto {

    @NotNull
    @Positive
    private BigDecimal valorTatuagem;

    @NotNull
    private Agendamento agendamento;

}
