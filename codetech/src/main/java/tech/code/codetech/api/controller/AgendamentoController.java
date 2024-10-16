package tech.code.codetech.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.agendamento.request.AgendamentoRequestDto;
import tech.code.codetech.dto.agendamento.response.AgendamentoResponseDto;
import tech.code.codetech.dto.produto.response.ProdutoResponseDto;
import tech.code.codetech.mapper.AgendamentoMapper;
import tech.code.codetech.model.Agendamento;
import tech.code.codetech.service.AgendamentoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag(name = "Agendamento")
@RestController
@RequestMapping({"/agendamentos"})
public class AgendamentoController {

    @Autowired
    public AgendamentoService agendamentoService;

    @Operation(summary = "", description = """
            # Listar todos os agendamentos
            ---
            Lista todos os agendamentos no banco de dados
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
    public ResponseEntity<List<AgendamentoResponseDto>> listar() {
        List<Agendamento> agendamentos = agendamentoService.findAll();

        if (agendamentos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<AgendamentoResponseDto> resposta = new ArrayList<>();
        for (Agendamento agendamento : agendamentos) {
            resposta.add(AgendamentoMapper.toResponseDto(agendamento));
        }
        return ResponseEntity.status(200).body(resposta);
    }
    @Operation(summary = "", description = """
            # Buscar agendamento por id
            ---
            Retorna um agendamento por id específico
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
    public ResponseEntity<AgendamentoResponseDto> encontrarPorId(@PathVariable Integer id) {
        Agendamento agendamentoEncontrado = agendamentoService.findById(id);

        if (agendamentoEncontrado == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(AgendamentoMapper.toResponseDto(agendamentoEncontrado));
    }
    @Operation(summary = "", description = """
            # Criar um agendamento
            ---
            Cria um novo agendamento no banco de dados
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
    public ResponseEntity<AgendamentoResponseDto> agendar(@RequestBody @Valid AgendamentoRequestDto agendamento) {
        List<Agendamento> agendamentosExistentes = agendamentoService.findAll();

        for (Agendamento agendamentoExistente : agendamentosExistentes) {
            boolean existeAgendamentoNaData = agendamentoExistente.getDt().equals(agendamento.getDt());
            boolean existeAgendamentoNaHora = agendamentoExistente.getHorario().equals(agendamento.getHorario());

            if (existeAgendamentoNaData && existeAgendamentoNaHora) {
                return ResponseEntity.status(409).build();
            }
        }

        Agendamento agendamentoConcluido = agendamentoService.save(AgendamentoMapper.toModel(agendamento));
        return ResponseEntity.status(201).body(AgendamentoMapper.toResponseDto(agendamentoConcluido));
    }

    @Operation(summary = "", description = """
            # Atualizar um agendamento
            ---
            Atualiza um agendamento por id específico
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
    public ResponseEntity<AgendamentoResponseDto> remarcar(@PathVariable Integer id, @RequestBody AgendamentoRequestDto agendamentoAtualizado) {

        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        } else if (Objects.isNull(agendamentoAtualizado)) {
            return ResponseEntity.status(400).build();
        }

        List<Agendamento> agendamentosExistentes = agendamentoService.findAll();
        for (Agendamento agendamento : agendamentosExistentes) {

            boolean mesmoId = agendamento.getId().equals(id);
            boolean existeAgendamentoNaData = agendamento.getDt().equals(agendamentoAtualizado.getDt());
            boolean existeAgendamentoNaHora = agendamento.getHorario().equals(agendamentoAtualizado.getHorario());

                if (!mesmoId && existeAgendamentoNaData && existeAgendamentoNaHora) {
                    return ResponseEntity.status(409).build();
                }
            }

            Agendamento agendamentoConcluido = agendamentoService.update(id, AgendamentoMapper.toModel(agendamentoAtualizado));

            if (Objects.isNull(agendamentoConcluido)) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(AgendamentoMapper.toResponseDto(agendamentoConcluido));
    }
    @Operation(summary = "", description = """
            # Deletar um agendamento
            ---
            Deleta um agendamento por id específico
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

        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        }

        boolean isDeleted = agendamentoService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}