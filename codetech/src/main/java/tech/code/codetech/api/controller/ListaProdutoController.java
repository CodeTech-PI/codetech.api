package tech.code.codetech.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.lista.request.ListaProdutoAtualizacaoRequestDto;
import tech.code.codetech.dto.lista.request.ListaProdutoDeleteRequestDto;
import tech.code.codetech.dto.lista.request.ListaProdutoRequestDto;
import tech.code.codetech.dto.lista.response.ListaProdutoResponseDto;
import tech.code.codetech.mapper.ListaProdutoMapper;
import tech.code.codetech.model.ListaProduto;
import tech.code.codetech.service.ListaProdutoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag(name = "Lista de Produtos")
@RestController
@RequestMapping("/lista-produtos")
public class ListaProdutoController {

    @Autowired
    private ListaProdutoService listaProdutoService;

    @Operation(summary = "Listar todos os produtos na lista", description = "Lista todos os produtos cadastrados na lista de produtos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar todos os produtos na lista",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ListaProdutoResponseDto.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Quando não há produtos cadastrados na lista", content = @Content())
    })
    @GetMapping
    public ResponseEntity<List<ListaProdutoResponseDto>> listar() {
        List<ListaProduto> listProdutos = listaProdutoService.findAll();

        if (listProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<ListaProdutoResponseDto> resposta = new ArrayList<>();

        for (ListaProduto listaProduto : listProdutos) {
            resposta.add(ListaProdutoMapper.toResponseDto(listaProduto));
        }
        return ResponseEntity.status(200).body(resposta);
    }

    @Operation(summary = "Buscar produto na lista por id", description = "Retorna um produto na lista por id específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mostra um produto específico na lista",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ListaProdutoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "O produto na lista não foi encontrado", content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<ListaProdutoResponseDto> encontrarPorId(@PathVariable int id) {
        ListaProduto listaProdutoEncontrada = listaProdutoService.findById(id);

        if (Objects.isNull(listaProdutoEncontrada)) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(ListaProdutoMapper.toResponseDto(listaProdutoEncontrada));
    }

    @Operation(summary = "Criar produtos na lista", description = "Cria novos produtos na lista de produtos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ListaProdutoResponseDto.class))
                    )
            )
    })
    @PostMapping
    public ResponseEntity<List<ListaProdutoResponseDto>> post(@RequestBody @Valid ListaProdutoRequestDto dto) {
        List<ListaProduto> listaSalva = listaProdutoService.saveAll(ListaProdutoMapper.toModel(dto));
        return ResponseEntity.status(201).body(ListaProdutoMapper.toResponseDto(listaSalva));
    }

    @Operation(summary = "Atualizar produtos na lista", description = "Atualiza produtos existentes na lista de produtos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ListaProdutoResponseDto.class))
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content())
    })
    @PutMapping
    public ResponseEntity<List<ListaProdutoResponseDto>> atualizar(@RequestBody @Valid ListaProdutoAtualizacaoRequestDto listaAtualizada) {
        List<ListaProduto> listaExiste = listaProdutoService.update(ListaProdutoMapper.toModel(listaAtualizada));

        if (listaExiste.isEmpty()) {
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(200).body(ListaProdutoMapper.toResponseDto(listaExiste));
    }

    @Operation(summary = "Deletar produtos da lista", description = "Deleta produtos da lista por ids específicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content())
    })
    @DeleteMapping
    public ResponseEntity<Void> deletar(@RequestBody ListaProdutoDeleteRequestDto listaProdutoDeleteRequestDto) {
        listaProdutoService.deleteAll(listaProdutoDeleteRequestDto.getProdutosIds());
        return ResponseEntity.status(204).build();
    }
}
