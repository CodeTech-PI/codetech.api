package tech.code.codetech.dto.agendamento.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import tech.code.codetech.model.Usuario;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class AgendamentoRequestDto {

    @NotNull
    private LocalDate dt;

    @NotNull
    private LocalTime horario;

    @NotNull
    private boolean cancelado = false;

    @NotNull
    private Usuario usuario;
}