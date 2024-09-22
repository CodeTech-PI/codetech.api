package tech.code.codetech.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.ordem.request.OrdemServicoRequestDto;
import tech.code.codetech.dto.ordem.response.OrdemServicoResponseDto;
import tech.code.codetech.mapper.OrdemServicoMapper;
import tech.code.codetech.model.OrdemServico;
import tech.code.codetech.service.OrdemServicoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService ordemServicoService;

    @GetMapping
    public ResponseEntity<List<OrdemServicoResponseDto>> listar(){
        List<OrdemServico> listOrdemServico = ordemServicoService.findAll();

        if(listOrdemServico.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        List<OrdemServicoResponseDto> resposta = new ArrayList<>();

        for (OrdemServico ordemServico : listOrdemServico) {
            resposta.add(OrdemServicoMapper.toResponseDto(ordemServico));
        }
        return ResponseEntity.status(200).body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDto> encontrarPorId(@PathVariable int id){
        OrdemServico ordemServicoEncontrada = ordemServicoService.findById(id);

        if(Objects.isNull(ordemServicoEncontrada)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(OrdemServicoMapper.toResponseDto(ordemServicoEncontrada));
    }

    @PostMapping
    public ResponseEntity<OrdemServicoResponseDto> post(@RequestBody @Valid OrdemServicoRequestDto dto){
        OrdemServico ordemServicoSalva = ordemServicoService.save(OrdemServicoMapper.toModel(dto));
        return ResponseEntity.status(201).body(OrdemServicoMapper.toResponseDto(ordemServicoSalva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDto> atualizar(@PathVariable int id, @RequestBody @Valid OrdemServicoRequestDto ordemServicoAtualizada){

        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        } else if(Objects.isNull(ordemServicoAtualizada)){
            return ResponseEntity.status(400).build();
        }

        OrdemServico ordemServicoExiste = ordemServicoService.update(id, OrdemServicoMapper.toModel(ordemServicoAtualizada));

        if(Objects.isNull(ordemServicoExiste)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(OrdemServicoMapper.toResponseDto(ordemServicoExiste));
    }

    @DeleteMapping("/{id}")
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