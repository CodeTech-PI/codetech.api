package tech.code.codetech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.code.codetech.dto.categoria.response.CategoriaResponseDto;
import tech.code.codetech.mapper.CategoriaMapper;
import tech.code.codetech.model.Categoria;
import tech.code.codetech.service.CategoriaService;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/categorias"})
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
}
