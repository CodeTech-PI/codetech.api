package tech.code.codetech.mapper;

import tech.code.codetech.dto.categoria.request.CategoriaRequestDto;
import tech.code.codetech.dto.categoria.response.CategoriaResponseDto;
import tech.code.codetech.dto.produto.response.ProdutoResponseDto;
import tech.code.codetech.model.Categoria;

import java.util.List;

public class CategoriaMapper {


    public static Categoria toModel(CategoriaRequestDto dto){
        return Categoria.builder()
                .nome(dto.getNome())
                .build();
    }

    public static CategoriaResponseDto toResponseDto(Categoria model){
        return CategoriaResponseDto.builder()
                .id(model.getId())
                .nome(model.getNome())
                .build();
    }

}
