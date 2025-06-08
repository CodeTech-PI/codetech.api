package tech.code.codetech.mapper;

import tech.code.codetech.dto.faturamento.request.FaturamentoRequestDto;
import tech.code.codetech.dto.faturamento.response.FaturamentoResponseDto;
import tech.code.codetech.model.Faturamento;

public class FaturamentoMapper {

    public static  Faturamento toModel(FaturamentoRequestDto dto){
        return Faturamento.builder()
                .lucro(dto.getLucro())
                .ordemServico(dto.getOrdemServico())
                .build();
    }

    public static FaturamentoResponseDto toResponseDto(Faturamento model){
        return FaturamentoResponseDto.builder()
                .id(model.getId())
                .lucro(model.getLucro())
                .ordemServico(model.getOrdemServico())
                .build();
    }
}
