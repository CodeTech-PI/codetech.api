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
import tech.code.codetech.dto.produto.response.ProdutoResponseDto;
import tech.code.codetech.dto.unidade.request.UnidadeRequestDto;
import tech.code.codetech.dto.unidade.response.UnidadeResponseDto;
import tech.code.codetech.mapper.UnidadeMapper;
import tech.code.codetech.model.Unidade;
import tech.code.codetech.service.UnidadeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Tag(name = "Unidade")
@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    @Autowired
    private UnidadeService unidadeService;

    @Operation(summary = "", description = """
            # Listar todas as unidades
            ---
            Lista todas as undidades no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<UnidadeResponseDto>> listar() {
        List<Unidade> listUnidades = unidadeService.findAll();

        if (listUnidades.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<UnidadeResponseDto> resposta = new ArrayList<>();

        for (Unidade unidade : listUnidades) {
            resposta.add(UnidadeMapper.toResponseDto(unidade));
        }
        return ResponseEntity.status(200).body(resposta);
    }

    @Operation(summary = "", description = """
            # Buscar uma unidade
            ---
            Retorna uma unidade por id específico
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UnidadeResponseDto> buscarPorId(@PathVariable Integer id) {
        Unidade unidade = unidadeService.findById(id);

        if (Objects.isNull(unidade)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(UnidadeMapper.toResponseDto(unidade));
    }
    @Operation(summary = "", description = """
            # Criar uma unidade
            ---
            Cria uma nova unidade no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<UnidadeResponseDto> post(@RequestBody @Valid UnidadeRequestDto dto) {
        Unidade unidade = unidadeService.save(UnidadeMapper.toModel(dto));
        return ResponseEntity.status(201).body(UnidadeMapper.toResponseDto(unidade));
    }
    @Operation(summary = "", description = """
            # Atualizar uma unidade
            ---
            Atualiza uma unidade por id específico
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UnidadeResponseDto> atualizar(@PathVariable Integer id, @RequestBody @Valid UnidadeRequestDto unidadeAtualizada) {

        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        } else if (Objects.isNull(unidadeAtualizada)) {
            return ResponseEntity.status(400).build();
        }

        Unidade unidadeExists = unidadeService.update(id, UnidadeMapper.toModel(unidadeAtualizada));

        if (Objects.isNull(unidadeExists)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(UnidadeMapper.toResponseDto(unidadeExists));
    }
    @Operation(summary = "", description = """
            # Deletar uma unidade
            ---
            Deleta uma unidade por id específico
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        }

        boolean isDeleted = unidadeService.delete(id);
        if(!isDeleted){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}