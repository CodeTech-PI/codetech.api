package tech.code.codetech.mapper;

import tech.code.codetech.dto.produto.request.ProdutoRequestDto;
import tech.code.codetech.dto.produto.response.ProdutoResponseDto;
import tech.code.codetech.model.Produto;

public class ProdutoMapper {

    public static Produto toModel(ProdutoRequestDto dto){
        return Produto.builder()
                .quantidade(dto.getQuantidade())
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .categoria(dto.getCategoria())
                .unidadeDeMedida(dto.getUnidadeDeMedida())
                .preco(dto.getPreco())
                .build();
    }

    public static ProdutoResponseDto toResponseDto(Produto model){
        return ProdutoResponseDto.builder()
                .id(model.getId())
                .quantidade(model.getQuantidade())
                .nome(model.getNome())
                .descricao(model.getDescricao())
                .categoria(model.getCategoria())
                .unidadeDeMedida(model.getUnidadeDeMedida())
                .preco(model.getPreco())
                .build();
    }

}
