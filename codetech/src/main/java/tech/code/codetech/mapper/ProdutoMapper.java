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
                .unidadeMedida(dto.getUnidadeMedida())
                .preco(dto.getPreco())
                .categoria(dto.getCategoria())
                .build();
    }

    public static ProdutoResponseDto toResponseDto(Produto model){

//        ProdutoResponseDto.CategoriaResponseDto categoria = ProdutoResponseDto.CategoriaResponseDto.builder()
//                .id(model.getCategoria().getId())
//                .nome(model.getCategoria().getNome())
//                .build();

        return ProdutoResponseDto.builder()
                .id(model.getId())
                .quantidade(model.getQuantidade())
                .nome(model.getNome())
                .descricao(model.getDescricao())
//                .categoria(categoria)
                .unidadeMedida(model.getUnidadeMedida())
                .preco(model.getPreco())
                .categoria(model.getCategoria())
                .build();
    }
}