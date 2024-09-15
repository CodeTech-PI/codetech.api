package tech.code.codetech.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.usuarios.request.UsuariosRequestDto;
import tech.code.codetech.dto.usuarios.response.UsuariosResponseDto;
import tech.code.codetech.mapper.ProdutoMapper;
import tech.code.codetech.mapper.UsuarioMapper;
import tech.code.codetech.model.Produto;
import tech.code.codetech.model.Usuario;
import tech.code.codetech.service.UsuarioService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuariosResponseDto>> listar() {
        List<Usuario> listUsuarios = usuarioService.findAll();

        if (listUsuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<UsuariosResponseDto> usuarios = new ArrayList<>();
        for (Usuario usuario : listUsuarios) {
            usuarios.add(UsuarioMapper.toResponseDto(usuario));
        }
        return ResponseEntity.status(200).body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuariosResponseDto> encontrarPorId(@PathVariable Integer id) {
        Usuario usuarioEncontrado = usuarioService.findById(id);

        if (Objects.isNull(usuarioEncontrado)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(UsuarioMapper.toResponseDto(usuarioEncontrado));
    }

    @PostMapping
    public ResponseEntity<UsuariosResponseDto> post(@RequestBody @Valid UsuariosRequestDto dto) {
        Usuario usuarioSaved = usuarioService.save(UsuarioMapper.toModel(dto));
        return ResponseEntity.status(201).body(UsuarioMapper.toResponseDto(usuarioSaved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuariosResponseDto> atualizar(@PathVariable Integer id, @RequestBody @Valid UsuariosRequestDto usuarioAtualizado) {
        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        } else if (Objects.isNull(usuarioAtualizado)) {
            return ResponseEntity.status(400).build();
        }

        Usuario usuarioExists = usuarioService.update(id, UsuarioMapper.toModel(usuarioAtualizado));

        if (Objects.isNull(usuarioExists)) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(UsuarioMapper.toResponseDto(usuarioExists));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (Objects.isNull(id) || id < 0) {
            ResponseEntity.status(404).build();
        }
        boolean isDeleted = usuarioService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();

    }

}
