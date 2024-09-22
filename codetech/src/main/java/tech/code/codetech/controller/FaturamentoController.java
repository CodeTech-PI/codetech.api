package tech.code.codetech.controller;

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

@RestController
@RequestMapping("/faturamentos")
public class FaturamentoController {

    @Autowired
    private FaturamentoService faturamentoService;

    @GetMapping
   public ResponseEntity<List<FaturamentoResponseDto>> listar(){
        List<Faturamento> listFaturamento = faturamentoService.findAll();

        if(listFaturamento.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        List<FaturamentoResponseDto> resposta = new ArrayList<>();

        for (Faturamento faturamento : listFaturamento) {
            resposta.add(FaturamentoMapper.toResponseDto(faturamento));
        }
        return ResponseEntity.status(200).body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaturamentoResponseDto> encontrarPorId(@PathVariable int id){
        Faturamento faturamentoEncontrado = faturamentoService.findById(id);

        if(Objects.isNull(faturamentoEncontrado)){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(FaturamentoMapper.toResponseDto(faturamentoEncontrado));
    }

    @PostMapping
    public ResponseEntity<FaturamentoResponseDto> post(@RequestBody @Valid FaturamentoRequestDto dto){
        Faturamento faturamentoSalvo = faturamentoService.save(FaturamentoMapper.toModel(dto));
        return ResponseEntity.status(201).body(FaturamentoMapper.toResponseDto(faturamentoSalvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FaturamentoResponseDto> atualizar(@PathVariable int id, @RequestBody @Valid FaturamentoRequestDto faturamentoAtualizado){

        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        } else if(Objects.isNull(faturamentoAtualizado)){
            return ResponseEntity.status(400).build();
        }

        Faturamento faturamentoExiste = faturamentoService.update(id, FaturamentoMapper.toModel(faturamentoAtualizado));

        if(Objects.isNull(faturamentoExiste)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(FaturamentoMapper.toResponseDto(faturamentoExiste));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        }
        faturamentoService.delete(id);
        return ResponseEntity.status(204).build();
    }

}