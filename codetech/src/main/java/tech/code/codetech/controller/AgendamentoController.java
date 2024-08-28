package tech.code.codetech.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.model.Agendamento;
import tech.code.codetech.service.AgendamentoService;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping({"/agendamentos"})
public class AgendamentoController {

    @Autowired
    public AgendamentoService agendamentoService;

    @GetMapping
    // metodo, do tipo lista e a lista é do tipo Agendamento;
    // ResponseEntity envia respostas HTTP
    public ResponseEntity<List<Agendamento>> listar() { //ResponseEntity é um metodo já existente
        List<Agendamento> agendamentos = agendamentoService.findAll();

        if (agendamentos.isEmpty()) { // a lista está vazia?
            return ResponseEntity.status(204).build(); // vazio
        }
        return ResponseEntity.status(200).body(agendamentos); // oky
    }


    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> encontrarPorId(@PathVariable Integer id) {
        Agendamento agendamentoEncontrado = agendamentoService.findById(id);

        // Objects.isNull(agendamentoEncontrado) || agendamentoEncontrado == null fazem a mesma coisa
        if (agendamentoEncontrado == null) {
            return ResponseEntity.status(404).build(); // não foi encontrado
        }
        return ResponseEntity.status(200).body(agendamentoEncontrado); // oky
    }

    //criando agendamento
    @PostMapping
    public ResponseEntity<Agendamento> agendar(@RequestBody @Valid Agendamento agendamento) {

        // Objects.isNull(agendamentoEncontrado) || agendamentoEncontrado == null fazem a mesma coisa
        if (Objects.isNull(agendamento)) {
            return ResponseEntity.status(400).build(); // não foi encontrado
        }
        Agendamento agendamentoConcluido = agendamentoService.save(agendamento);
        return ResponseEntity.status(201).body(agendamentoConcluido); // oky, foi criado
    }

    //remarcando
    @PutMapping("/{id}")
    public ResponseEntity<Agendamento> remarcar(@PathVariable Integer id, @RequestBody Agendamento agendamentoAtualizado) {

        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        } else if (Objects.isNull(agendamentoAtualizado)) {
            return ResponseEntity.status(400).build();
        }

        Agendamento agendamentoConcluido = agendamentoService.update(id, agendamentoAtualizado);

        if (Objects.isNull(agendamentoConcluido)) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(agendamentoConcluido);
    }

    //cancelando
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {

        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        }

        boolean isDeleted = agendamentoService.delete(id);
        if (!isDeleted) { // se for falso
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}
