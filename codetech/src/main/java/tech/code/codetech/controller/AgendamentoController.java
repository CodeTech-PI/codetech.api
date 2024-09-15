package tech.code.codetech.controller;

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

@RestController
@RequestMapping({"/agendamentos"})
public class AgendamentoController {

    @Autowired
    public AgendamentoService agendamentoService;

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

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponseDto> encontrarPorId(@PathVariable Integer id) {
        Agendamento agendamentoEncontrado = agendamentoService.findById(id);

        if (agendamentoEncontrado == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(AgendamentoMapper.toResponseDto(agendamentoEncontrado));
    }

    @PostMapping
    public ResponseEntity<AgendamentoResponseDto> agendar(@RequestBody @Valid AgendamentoRequestDto agendamento) {
        Agendamento agendamentoConcluido = agendamentoService.save(AgendamentoMapper.toModel(agendamento));
        return ResponseEntity.status(201).body(AgendamentoMapper.toResponseDto(agendamentoConcluido));
    }

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
            boolean existeAgendamentoNaData = agendamento.getData().equals(agendamentoAtualizado.getData());
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