package tech.code.codetech.dto.faturamento.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import tech.code.codetech.model.OrdemServico;


@Data
@Builder
public class FaturamentoRequestDto {

    @NotNull
    private Double lucro;

    @NotNull
    private OrdemServico ordemServico;
}
