package tech.code.codetech.mapper;

import tech.code.codetech.api.controller.generico.StatusUnidade;
import tech.code.codetech.dto.unidade.request.UnidadeRequestDto;
import tech.code.codetech.dto.unidade.response.UnidadeResponseDto;
import tech.code.codetech.model.Unidade;

public class UnidadeMapper {

    public static Unidade toModel(UnidadeRequestDto dto){
        StatusUnidade status = dto.getStatus();  // Pegando o valor no tipo StatusUnidade


        return Unidade.builder()
                .cep(dto.getCep())
                .logradouro(dto.getLogradouro())
                .bairro(dto.getBairro())
                .cidade(dto.getCidade())
                .estado(dto.getEstado())
                .complemento(dto.getComplemento())
                .num(dto.getNum())
                .status(status)
                .build();
    }

    public static UnidadeResponseDto toResponseDto(Unidade unidade) {
        return UnidadeResponseDto.builder()
                .id(unidade.getId())
                .cep(unidade.getCep())
                .logradouro(unidade.getLogradouro())
                .bairro(unidade.getBairro())
                .cidade(unidade.getCidade())
                .estado(unidade.getEstado())
                .complemento(unidade.getComplemento())
                .num(unidade.getNum())
                .status(unidade.getStatus())
                .build();
    }
}