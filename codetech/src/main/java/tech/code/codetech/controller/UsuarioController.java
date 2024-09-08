package tech.code.codetech.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.model.Produto;
import tech.code.codetech.model.Usuario;
import tech.code.codetech.service.UsuarioService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> listUsuarios = usuarioService.findAll();

        if (listUsuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> encontrarPorId(@PathVariable Integer id) {
        Usuario usuarioEncontrado = usuarioService.findById(id);

        if (Objects.isNull(usuarioEncontrado)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(usuarioEncontrado);
    }

    @PostMapping
    public ResponseEntity<Usuario> post(@RequestBody Usuario usuario) {
        if (Objects.isNull(usuario)) {
            return ResponseEntity.status(400).build();
        }
        Usuario usuarioSaved = usuarioService.save(usuario);
        return ResponseEntity.status(201).body(usuarioSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Integer id, @RequestBody Usuario usuarioAtualizado) {
        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        } else if (Objects.isNull(usuarioAtualizado)) {
            return ResponseEntity.status(400).build();
        }

        Usuario usuarioExists = usuarioService.update(id, usuarioAtualizado);

        if (Objects.isNull(usuarioExists)) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(usuarioExists);
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
