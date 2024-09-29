package tech.code.codetech.dto.agendamento.response;

import lombok.Builder;
import lombok.Data;
import tech.code.codetech.model.Usuario;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class AgendamentoResponseDto {
    private Integer id;
    private LocalDate dt;
    private LocalTime horario;
    private boolean cancelado = false;
    private Usuario usuario;
}