package tech.code.codetech.mapper;

import tech.code.codetech.dto.ordem.OrdemServicoLucroDto;
import tech.code.codetech.dto.ordem.request.OrdemServicoRequestDto;
import tech.code.codetech.dto.ordem.response.OrdemServicoResponseDto;
import tech.code.codetech.model.OrdemServico;

public class OrdemServicoMapper {

        public static OrdemServico toModel(OrdemServicoRequestDto dto){
            return OrdemServico.builder()
                    .valorTatuagem(dto.getValorTatuagem())
                    .agendamento(dto.getAgendamento())
                    .build();
        }

        public static OrdemServicoResponseDto toResponseDto(OrdemServicoLucroDto dto){
            return OrdemServicoResponseDto.builder()
                    .id(dto.getOrdemServico().getId())
                    .valorTatuagem(dto.getOrdemServico().getValorTatuagem())
                    .agendamento(dto.getOrdemServico().getAgendamento())
                    .lucro(dto.getLucro())
                    .build();
        }

        public static OrdemServicoResponseDto toResponseDto(OrdemServico ordemServico){
            return OrdemServicoResponseDto.builder()
                    .id(ordemServico.getId())
                    .valorTatuagem(ordemServico.getValorTatuagem())
                    .agendamento(ordemServico.getAgendamento())
                    .build();
        }
}