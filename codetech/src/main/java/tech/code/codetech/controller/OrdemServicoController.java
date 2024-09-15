package tech.code.codetech.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.model.OrdemServico;
import tech.code.codetech.service.OrdemServicoService;
import java.util.Objects;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService ordemServicoService;

    @GetMapping("{id}")
    public ResponseEntity<OrdemServico> encontrarPorId(@PathVariable int id){
        OrdemServico ordemServicoEncontrada = ordemServicoService.findById(id);

        if(Objects.isNull(ordemServicoEncontrada)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(ordemServicoEncontrada);
    }

    @PostMapping
    public ResponseEntity<OrdemServico> post(@RequestBody @Valid OrdemServico ordemServico){
        if(Objects.isNull(ordemServico)){
            return ResponseEntity.status(400).build();
        }
        OrdemServico ordemServicoSalva = ordemServicoService.save(ordemServico);
        return ResponseEntity.status(201).body(ordemServicoSalva);
    }

    @PutMapping("{id}")
    public ResponseEntity<OrdemServico> atualizar(@PathVariable int id, @RequestBody @Valid OrdemServico ordemServicoAtualizada){

        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        } else if(Objects.isNull(ordemServicoAtualizada)){
            return ResponseEntity.status(400).build();
        }

        OrdemServico ordemServicoExiste = ordemServicoService.update(id, ordemServicoAtualizada);

        if(Objects.isNull(ordemServicoExiste)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(ordemServicoExiste);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        }
        boolean isDeleted = ordemServicoService.delete(id);
        if(!isDeleted){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}