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

    //CONFIGURAÇÂO SWAGGER listar()
    @Operation(summary = "Listar agendamento", description = """
            Esse endpoint permite a listagem de todos os agendamentos cadastrados no sistema:
            
            - Retorna uma lista de objetos representando cada agendamento.
            
            Respostas:
            
            - 200: Requisição sucedida. Retorna a lista de agendamentos em JSON.
            - 204: Nenhum agendamento encontrado.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AgendamentoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhum agendamento encontrado")
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

    //CONFIGURAÇÂO SWAGGER encontrarPorId()
    @Operation(summary = "Buscar agendamento por id", description = """
            Esse endpoint permite buscar um agendamento específico pelo seu ID.
            
            - Retorna o objeto `Agendamento` correspondente ao ID fornecido como parâmetro.
            
            Respostas:
            - 200: Requisição sucedida. Retorna o agendamento solicitado em JSON.
            - 404: Agendamento não encontrado para o ID informado.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AgendamentoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> encontrarPorId(@PathVariable Integer id) {
        Agendamento agendamentoEncontrado = agendamentoService.findById(id);

        if (agendamentoEncontrado == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(AgendamentoMapper.toResponseDto(agendamentoEncontrado));
    }

    //CONFIGURAÇÂO SWAGGER agendar()
    @Operation(summary = "Criar novo agendamento", description = """
            Esse endpoint permite a criação de um novo agendamento no sistema.
                                                              
            - Recebe os dados do novo agendamento em um objeto `Agendamento`.
            - Verifica se já existe um agendamento na mesma data e horário.
            
            Respostas:
            - 201: Agendamento criado com sucesso.
            - 409: Já existe um agendamento para a data e horário especificados.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AgendamentoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "409", description = "Conflito: já existe um agendamento para a data e horário especificados")
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

    //CONFIGURAÇÃO SWAGGER remarcar()
    @Operation(summary = "Atualizar agendamento existente", description = """
            Esse endpoint permite atualizar os dados de um agendamento existente com base no ID fornecido.

            - Recebe os dados atualizados em um objeto `Agendamento`.
            - Verifica se o ID é válido e se já existe um agendamento na mesma data e horário.
            
            Respostas:
            - 200: Agendamento atualizado com sucesso.
            - 400: Dados inválidos para atualização.
            - 404: Agendamento não encontrado para o ID especificado.
            - 409: Conflito, já existe um agendamento para a data e horário especificados.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AgendamentoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "409", description = "Conflito, já existe um agendamento para a data e horário especificados")
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

    //CONFIGURAÇÃO SWAGGER deletar()
    @Operation(summary = "Deletar agendamento", description = """
            Esse endpoint permite a exclusão de um agendamento específico com base no ID fornecido.

            - Verifica se o ID é válido e se o agendamento existe no sistema.
            
            Respostas:
            - 204: Agendamento excluído com sucesso.
            - 404: Agendamento não encontrado para o ID especificado.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado")
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