package tech.code.codetech.mapper;

import tech.code.codetech.dto.lista.request.ListaProdutoRequestDto;
import tech.code.codetech.dto.lista.response.ListaProdutoResponseDto;
import tech.code.codetech.model.ListaProduto;

public class ListaProdutoMapper {

        public static ListaProduto toModel(ListaProdutoRequestDto dto){
            return ListaProduto.builder()
                    .quantidadeProdutos(dto.getQuantidade())
                    .agendamento(dto.getAgendamento())
                    .produto(dto.getProduto())
                    .build();
        }

        public static ListaProdutoResponseDto toResponseDto(ListaProduto model){
            return ListaProdutoResponseDto.builder()
                    .id(model.getId())
                    .quantidade(model.getQuantidadeProdutos())
                    .agendamento(model.getAgendamento())
                    .produto(model.getProduto())
                    .build();
        }
}