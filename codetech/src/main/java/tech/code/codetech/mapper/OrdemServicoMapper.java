package tech.code.codetech.mapper;

import tech.code.codetech.dto.ordem.request.OrdemServicoRequestDto;
import tech.code.codetech.dto.ordem.response.OrdemServicoResponseDto;
import tech.code.codetech.model.OrdemServico;

public class OrdemServicoMapper {

        public static OrdemServico toModel(OrdemServicoRequestDto dto){
            return OrdemServico.builder()
                    .valorAgendamento(dto.getValorAgendamento())
                    .agendamento(dto.getAgendamento())
                    .build();
        }

        public static OrdemServicoResponseDto toResponseDto(OrdemServico model){
            return OrdemServicoResponseDto.builder()
                    .id(model.getId())
                    .valorAgendamento(model.getValorAgendamento())
                    .agendamento(model.getAgendamento())
                    .build();
        }
}