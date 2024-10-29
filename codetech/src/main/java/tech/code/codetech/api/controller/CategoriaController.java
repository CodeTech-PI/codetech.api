package tech.code.codetech.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.categoria.request.CategoriaRequestDto;
import tech.code.codetech.dto.categoria.response.CategoriaResponseDto;
import tech.code.codetech.mapper.CategoriaMapper;
import tech.code.codetech.model.Categoria;
import tech.code.codetech.service.CategoriaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag(name = "Categorias")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Listar todas as categorias", description = "Lista todas as categorias no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de categorias retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "No Content - Nenhuma categoria encontrada", content = @Content())
    })
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> listar() {
        List<Categoria> categorias = categoriaService.findAll();

        if (categorias.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<CategoriaResponseDto> resposta = new ArrayList<>();
        for (Categoria categoria : categorias) {
            resposta.add(CategoriaMapper.toResponseDto(categoria));
        }
        return ResponseEntity.status(200).body(resposta);
    }

    @Operation(summary = "Buscar categoria por id", description = "Retorna uma categoria por id específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Categoria encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Not Found - Categoria não encontrada", content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> buscarPorId(@PathVariable Integer id) {
        Categoria categoria = categoriaService.findById(id);

        if (Objects.isNull(categoria)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(CategoriaMapper.toResponseDto(categoria));
    }

    @Operation(summary = "Criar uma categoria", description = "Cria uma nova categoria no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created - Categoria criada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos fornecidos", content = @Content())
    })
    @PostMapping
    public ResponseEntity<CategoriaResponseDto> salvar(@RequestBody @Valid CategoriaRequestDto dto) {
        Categoria categoria = categoriaService.save(CategoriaMapper.toModel(dto));
        return ResponseEntity.status(201).body(CategoriaMapper.toResponseDto(categoria));
    }

    @Operation(summary = "Atualizar uma categoria", description = "Atualiza uma categoria por id específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Categoria atualizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos fornecidos", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not Found - Categoria não encontrada", content = @Content())
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> atualizar(@PathVariable Integer id, @RequestBody @Valid CategoriaRequestDto categoriaAtualizada) {
        Categoria categoria = categoriaService.update(id, CategoriaMapper.toModel(categoriaAtualizada));
        return ResponseEntity.status(200).body(CategoriaMapper.toResponseDto(categoria));
    }

    @Operation(summary = "Deletar uma categoria", description = "Deleta uma categoria por id específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - Categoria deletada com sucesso", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not Found - Categoria não encontrada", content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (Objects.isNull(id) || id < 0) {
            return ResponseEntity.status(404).build();
        }
        boolean isDeleted = categoriaService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}
