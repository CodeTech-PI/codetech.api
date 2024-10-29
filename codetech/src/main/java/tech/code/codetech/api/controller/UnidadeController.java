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

    @Operation(summary = "Listar todas as unidades", description = """
            # Listar todas as unidades
            ---
            Retorna uma lista de todas as unidades registradas no sistema.
            
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de unidades retornada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnidadeResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhuma unidade encontrada.")
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

    @Operation(summary = "Buscar uma unidade", description = """
            # Buscar uma unidade
            ---
            Retorna uma unidade pelo ID específico.
            .
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade encontrada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnidadeResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UnidadeResponseDto> buscarPorId(@PathVariable Integer id) {
        Unidade unidade = unidadeService.findById(id);

        if (Objects.isNull(unidade)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(UnidadeMapper.toResponseDto(unidade));
    }

    @Operation(summary = "Criar uma unidade", description = """
            # Criar uma unidade
            ---
            Cria uma nova unidade com os dados fornecidos.
            
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Unidade criada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnidadeResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação da unidade.")
    })
    @PostMapping
    public ResponseEntity<UnidadeResponseDto> post(@RequestBody @Valid UnidadeRequestDto dto) {
        Unidade unidade = unidadeService.save(UnidadeMapper.toModel(dto));
        return ResponseEntity.status(201).body(UnidadeMapper.toResponseDto(unidade));
    }

    @Operation(summary = "Atualizar uma unidade", description = """
            # Atualizar uma unidade
            ---
            Atualiza os dados de uma unidade existente pelo ID específico.
            .
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade atualizada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnidadeResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização da unidade.")
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

    @Operation(summary = "Deletar uma unidade", description = """
            # Deletar uma unidade
            ---
            Remove uma unidade do pelo ID específico.
           
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Unidade deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        }

        boolean isDeleted = unidadeService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}
