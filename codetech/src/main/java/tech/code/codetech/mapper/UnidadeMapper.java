package tech.code.codetech.mapper;

import tech.code.codetech.dto.unidade.request.UnidadeRequestDto;
import tech.code.codetech.dto.unidade.response.UnidadeResponseDto;
import tech.code.codetech.model.Unidade;

public class UnidadeMapper {

    public static Unidade toModel(UnidadeRequestDto dto){
        return Unidade.builder()
                .cep(dto.getCep())
                .logradouro(dto.getLogradouro())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .complemento(dto.getComplemento())
                .num(dto.getNum())
                .build();
    }

    public static UnidadeResponseDto toResponseDto(Unidade model){
        return UnidadeResponseDto.builder()
                .id(model.getId())
                .cep(model.getCep())
                .logradouro(model.getLogradouro())
                .bairro(model.getBairro())
                .cidade(model.getCidade())
                .estado(model.getEstado())
                .complemento(model.getComplemento())
                .num(model.getNum())
                .build();
    }
}