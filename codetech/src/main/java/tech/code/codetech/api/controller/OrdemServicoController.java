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
import tech.code.codetech.dto.ordem.OrdemServicoLucroDto;
import tech.code.codetech.dto.ordem.request.OrdemServicoRequestDto;
import tech.code.codetech.dto.ordem.response.OrdemServicoResponseDto;
import tech.code.codetech.mapper.OrdemServicoMapper;
import tech.code.codetech.strategy.OrdemServicoInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag(name = "Ordem de Serviço")
@RestController
@RequestMapping("/ordens-servicos")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoInterface ordemServicoService;

    @Operation(summary = "Listar todas as ordens de serviço", description = "Lista todas as ordens de serviço no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Lista de ordens de serviço retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdemServicoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "No Content - Nenhuma ordem de serviço encontrada", content = @Content())
    })
    @GetMapping
    public ResponseEntity<List<OrdemServicoResponseDto>> listar() {
        List<OrdemServicoLucroDto> listOrdemServico = ordemServicoService.buscarTodos();

        if (listOrdemServico.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<OrdemServicoResponseDto> resposta = new ArrayList<>();
        for (OrdemServicoLucroDto ordemServico : listOrdemServico) {
            resposta.add(OrdemServicoMapper.toResponseDto(ordemServico));
        }
        return ResponseEntity.status(200).body(resposta);
    }

    @Operation(summary = "Buscar uma ordem de serviço por id", description = "Retorna uma ordem de serviço por id específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Ordem de serviço encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdemServicoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Not Found - Ordem de serviço não encontrada", content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDto> encontrarPorId(@PathVariable int id) {
        OrdemServicoLucroDto ordemServicoEncontrada = ordemServicoService.buscarPorId(id);

        if (Objects.isNull(ordemServicoEncontrada)) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(OrdemServicoMapper.toResponseDto(ordemServicoEncontrada));
    }

    @Operation(summary = "Criar uma ordem de serviço", description = "Cria uma nova ordem de serviço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created - Ordem de serviço criada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdemServicoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos fornecidos", content = @Content())
    })
    @PostMapping
    public ResponseEntity<OrdemServicoResponseDto> post(@RequestBody @Valid OrdemServicoRequestDto dto) {
        OrdemServicoLucroDto ordemServicoSalva = ordemServicoService.executarOrdemServico(OrdemServicoMapper.toModel(dto));
        return ResponseEntity.status(201).body(OrdemServicoMapper.toResponseDto(ordemServicoSalva));
    }

    @Operation(summary = "Atualizar uma ordem de serviço", description = "Atualiza uma ordem de serviço por id específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Ordem de serviço atualizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdemServicoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos fornecidos", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not Found - Ordem de serviço não encontrada", content = @Content())
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDto> atualizar(@PathVariable int id, @RequestBody @Valid OrdemServicoRequestDto ordemServico) {
        OrdemServicoLucroDto ordemServicoAtualizada = ordemServicoService.atualizarOrdemServico(OrdemServicoMapper.toModel(ordemServico), id);

        if (Objects.isNull(ordemServicoAtualizada)) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(OrdemServicoMapper.toResponseDto(ordemServicoAtualizada));
    }

    // Implementação do método DELETE se necessário
    /*
    @Operation(summary = "Deletar uma ordem de serviço", description = "Deleta uma ordem de serviço por id específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - Ordem de serviço deletada com sucesso", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not Found - Ordem de serviço não encontrada", content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        }
        boolean isDeleted = ordemServicoService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
    */
}
