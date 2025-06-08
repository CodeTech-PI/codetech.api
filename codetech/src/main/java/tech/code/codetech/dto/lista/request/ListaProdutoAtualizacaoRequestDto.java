package tech.code.codetech.dto.lista.request;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.code.codetech.model.Agendamento;
import tech.code.codetech.model.Produto;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListaProdutoAtualizacaoRequestDto {

    private List<ProdutoRequestDto> produtos;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProdutoRequestDto {

        @NotNull
        @Positive
        private Integer id;

        @NotNull
        @Positive
        private Integer quantidade;

        @NotNull
        private Agendamento agendamento;

        @NotNull
        private Produto produto;
    }
}
