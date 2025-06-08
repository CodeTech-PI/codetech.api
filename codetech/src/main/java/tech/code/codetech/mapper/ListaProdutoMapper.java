package tech.code.codetech.mapper;

import tech.code.codetech.dto.lista.request.ListaProdutoAtualizacaoRequestDto;
import tech.code.codetech.dto.lista.request.ListaProdutoRequestDto;
import tech.code.codetech.dto.lista.response.ListaProdutoResponseDto;
import tech.code.codetech.model.ListaProduto;

import java.util.List;

public class ListaProdutoMapper {

    public static List<ListaProduto> toModel(ListaProdutoRequestDto dto) {
        return dto.getProdutos().stream()
                .map(ListaProdutoMapper::toModel)
                .toList();
    }

    public static ListaProduto toModel(ListaProdutoRequestDto.ProdutoRequestDto dto) {
        return ListaProduto.builder()
                .quantidadeProdutos(dto.getQuantidade())
                .agendamento(dto.getAgendamento())
                .produto(dto.getProduto())
                .build();
    }

    public static List<ListaProdutoResponseDto> toResponseDto(List<ListaProduto> model) {
        return model.stream()
                .map(ListaProdutoMapper::toResponseDto)
                .toList();
    }

    public static ListaProdutoResponseDto toResponseDto(ListaProduto model) {
        return ListaProdutoResponseDto.builder()
                .id(model.getId())
                .quantidade(model.getQuantidadeProdutos())
                .agendamento(model.getAgendamento())
                .produto(model.getProduto())
                .build();
    }

    public static List<ListaProduto> toModel(ListaProdutoAtualizacaoRequestDto dto) {
        return dto.getProdutos().stream()
                .map(ListaProdutoMapper::toModel)
                .toList();
    }

    public static ListaProduto toModel(ListaProdutoAtualizacaoRequestDto.ProdutoRequestDto dto) {
        return ListaProduto.builder()
                .id(dto.getId())
                .quantidadeProdutos(dto.getQuantidade())
                .agendamento(dto.getAgendamento())
                .produto(dto.getProduto())
                .build();
    }
}