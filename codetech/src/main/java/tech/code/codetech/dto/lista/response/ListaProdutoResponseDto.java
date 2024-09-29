package tech.code.codetech.dto.lista.response;

import lombok.Builder;
import lombok.Data;
import tech.code.codetech.model.Agendamento;
import tech.code.codetech.model.Produto;

@Data
@Builder
public class ListaProdutoResponseDto {
    private Integer id;
    private Integer quantidade;
    private Produto produto;
    private Agendamento agendamento;
}
