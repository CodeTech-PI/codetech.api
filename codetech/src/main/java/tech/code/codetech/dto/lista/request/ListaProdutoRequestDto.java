package tech.code.codetech.dto.lista.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import tech.code.codetech.model.Agendamento;
import tech.code.codetech.model.Produto;

@Data
@Builder
public class ListaProdutoRequestDto {

    @NotNull
    @Positive
    private Integer quantidade;

    @NotNull
    private Agendamento agendamento;

    @NotNull
    private Produto produto;

}
