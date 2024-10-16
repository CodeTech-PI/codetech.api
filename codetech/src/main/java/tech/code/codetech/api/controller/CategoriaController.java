package tech.code.codetech.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.categoria.request.CategoriaRequestDto;
import tech.code.codetech.dto.categoria.response.CategoriaResponseDto;
import tech.code.codetech.dto.produto.request.ProdutoRequestDto;
import tech.code.codetech.mapper.CategoriaMapper;
import tech.code.codetech.model.Categoria;
import tech.code.codetech.service.CategoriaService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    public CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> listar() {
        List<Categoria> categorias = categoriaService.findAll();

        if (categorias.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<CategoriaResponseDto> resposta = new ArrayList<>();

        for (Categoria categoria : categorias) {
            resposta.add(CategoriaMapper.toResponseDto(categoria));
        }
        return ResponseEntity.status(200).body(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> buscarPorId(@PathVariable Integer id) {
        Categoria categoria = categoriaService.findById(id);

        if (Objects.isNull(categoria)) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(CategoriaMapper.toResponseDto(categoria));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDto> salvar(@RequestBody @Valid CategoriaRequestDto dto) {
        Categoria categoria = categoriaService.save(CategoriaMapper.toModel(dto));
        return ResponseEntity.status(201).body(CategoriaMapper.toResponseDto(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> atualizar(@PathVariable Integer id, @RequestBody @Valid CategoriaRequestDto categoriaAtualizada) {
        Categoria categoria = categoriaService.update(id, CategoriaMapper.toModel(categoriaAtualizada));
        return ResponseEntity.status(200).body(CategoriaMapper.toResponseDto(categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {

        if (Objects.isNull(id) || id < 0) {
            return ResponseEntity.status(404).build();
        }
        boolean isDeleted = categoriaService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}
