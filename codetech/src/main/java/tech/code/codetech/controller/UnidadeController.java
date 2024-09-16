package tech.code.codetech.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.unidade.request.UnidadeRequestDto;
import tech.code.codetech.dto.unidade.response.UnidadeResponseDto;
import tech.code.codetech.mapper.UnidadeMapper;
import tech.code.codetech.model.Unidade;
import tech.code.codetech.service.UnidadeService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    @Autowired
    private UnidadeService unidadeService;

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

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeResponseDto> buscarPorId(@PathVariable Integer id) {
        Unidade unidade = unidadeService.findById(id);

        if (Objects.isNull(unidade)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(UnidadeMapper.toResponseDto(unidade));
    }

    @PostMapping
    public ResponseEntity<UnidadeResponseDto> post(@RequestBody @Valid UnidadeRequestDto dto) {
        Unidade unidade = UnidadeMapper.toModel(dto);
        return ResponseEntity.status(201).body(UnidadeMapper.toResponseDto(unidade));
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        }

        boolean isDeleted = unidadeService.delete(id);
        if(!isDeleted){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}