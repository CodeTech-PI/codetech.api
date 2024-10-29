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
import tech.code.codetech.dto.faturamento.request.FaturamentoRequestDto;
import tech.code.codetech.dto.faturamento.response.FaturamentoResponseDto;
import tech.code.codetech.mapper.FaturamentoMapper;
import tech.code.codetech.model.Faturamento;
import tech.code.codetech.service.FaturamentoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag(name = "Faturamento")
@RestController
@RequestMapping("/faturamentos")
public class FaturamentoController {

    @Autowired
    private FaturamentoService faturamentoService;

    @Operation(summary = "Listar todos os faturamentos", description = "Lista todos os faturamentos no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de faturamentos retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FaturamentoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "No Content - Nenhum faturamento encontrado", content = @Content())
    })
    @GetMapping
    public ResponseEntity<List<FaturamentoResponseDto>> listar() {
        List<Faturamento> listFaturamento = faturamentoService.findAll();

        if (listFaturamento.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<FaturamentoResponseDto> resposta = new ArrayList<>();
        for (Faturamento faturamento : listFaturamento) {
            resposta.add(FaturamentoMapper.toResponseDto(faturamento));
        }
        return ResponseEntity.status(200).body(resposta);
    }

    @Operation(summary = "Buscar faturamento por id", description = "Retorna um faturamento por id específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Faturamento encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FaturamentoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Not Found - Faturamento não encontrado", content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<FaturamentoResponseDto> encontrarPorId(@PathVariable int id) {
        Faturamento faturamentoEncontrado = faturamentoService.findById(id);

        if (Objects.isNull(faturamentoEncontrado)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(FaturamentoMapper.toResponseDto(faturamentoEncontrado));
    }

    @Operation(summary = "Criar um faturamento", description = "Cria um novo faturamento no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created - Faturamento criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FaturamentoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos fornecidos", content = @Content())
    })
    @PostMapping
    public ResponseEntity<FaturamentoResponseDto> post(@RequestBody @Valid FaturamentoRequestDto dto) {
        Faturamento faturamentoSalvo = faturamentoService.save(FaturamentoMapper.toModel(dto));
        return ResponseEntity.status(201).body(FaturamentoMapper.toResponseDto(faturamentoSalvo));
    }

    @Operation(summary = "Atualizar um faturamento", description = "Atualiza um faturamento por id específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Faturamento atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FaturamentoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos fornecidos", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not Found - Faturamento não encontrado", content = @Content())
    })
    @PutMapping("/{id}")
    public ResponseEntity<FaturamentoResponseDto> atualizar(@PathVariable int id, @RequestBody @Valid FaturamentoRequestDto faturamentoAtualizado) {
        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        } else if (Objects.isNull(faturamentoAtualizado)) {
            return ResponseEntity.status(400).build();
        }

        Faturamento faturamentoExiste = faturamentoService.update(id, FaturamentoMapper.toModel(faturamentoAtualizado));

        if (Objects.isNull(faturamentoExiste)) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(FaturamentoMapper.toResponseDto(faturamentoExiste));
    }

    @Operation(summary = "Deletar um faturamento", description = "Deleta um faturamento por id específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - Faturamento deletado com sucesso", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not Found - Faturamento não encontrado", content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        }
        boolean isDeleted = faturamentoService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}
