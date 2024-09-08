package tech.code.codetech.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.model.UsuarioLombardi;
import tech.code.codetech.service.UsuarioLombardiService;
import java.util.Objects;

@RestController
@RequestMapping("/lombardi")
public class UsuarioLombardiController {

    @Autowired
    private UsuarioLombardiService usuarioLombardiService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioLombardi> encontrarPorId(@PathVariable Integer id){
        UsuarioLombardi usuarioLombardi = usuarioLombardiService.findById(id);

        if(Objects.isNull(usuarioLombardi)){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(usuarioLombardi);
    }

    @PostMapping
    public ResponseEntity<UsuarioLombardi> save(@RequestBody @Valid UsuarioLombardi usuarioLombardi){
         usuarioLombardi.setId(null);
        return ResponseEntity.status(201).body(usuarioLombardiService.save(usuarioLombardi));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioLombardi> post(@RequestBody @Valid UsuarioLombardi usuarioLombardi) {
       UsuarioLombardi usuario = usuarioLombardiService.findByEmailAndSenha(usuarioLombardi.getEmail(), usuarioLombardi.getSenha());

        if (usuario == null) {
            return ResponseEntity.status(404).build();
        }
            return ResponseEntity.status(200).body(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioLombardi> atualizar(@PathVariable Integer id, @RequestBody @Valid UsuarioLombardi usuarioLombardi){

        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        } else if(Objects.isNull(usuarioLombardi)){
            return ResponseEntity.status(400).build();
        }

        UsuarioLombardi userLombardiExists = usuarioLombardiService.update(id, usuarioLombardi);

        if(Objects.isNull(userLombardiExists)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(userLombardiExists);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){

        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        }
        boolean isDeleted =  usuarioLombardiService.delete(id);
        if(!isDeleted){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}
