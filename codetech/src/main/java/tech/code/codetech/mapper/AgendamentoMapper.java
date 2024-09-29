package tech.code.codetech.mapper;

import tech.code.codetech.dto.agendamento.request.AgendamentoRequestDto;
import tech.code.codetech.dto.agendamento.response.AgendamentoResponseDto;
import tech.code.codetech.model.Agendamento;

public class AgendamentoMapper {

    public static Agendamento toModel(AgendamentoRequestDto dto){
        return Agendamento.builder()
                .dt(dto.getDt())
                .horario(dto.getHorario())
                .cancelado(dto.isCancelado())
                .usuario(dto.getUsuario())
                .build();
    }

    public static AgendamentoResponseDto toResponseDto(Agendamento model){
        return AgendamentoResponseDto.builder()
                .id(model.getId())
                .dt(model.getDt())
                .horario(model.getHorario())
                .cancelado(model.isCancelado())
                .usuario(model.getUsuario())
                .build();
    }
}
