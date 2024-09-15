package tech.code.codetech.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.lombardi.request.LombardiRequestDto;
import tech.code.codetech.dto.lombardi.response.LombardiResponseDto;
import tech.code.codetech.mapper.UsuarioLombardiMapper;
import tech.code.codetech.model.UsuarioLombardi;
import tech.code.codetech.service.UsuarioLombardiService;
import java.util.Objects;

@RestController
@RequestMapping("/lombardi")
public class UsuarioLombardiController {

    @Autowired
    private UsuarioLombardiService usuarioLombardiService;

    @GetMapping("/{id}")
    public ResponseEntity<LombardiResponseDto> encontrarPorId(@PathVariable Integer id){
        UsuarioLombardi usuarioLombardi = usuarioLombardiService.findById(id);

        if(Objects.isNull(usuarioLombardi)){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(UsuarioLombardiMapper.toResponseDto(usuarioLombardi));
    }

    @PostMapping
    public ResponseEntity<UsuarioLombardi> save(@RequestBody @Valid UsuarioLombardi usuarioLombardi){
         usuarioLombardi.setId(null);
        return ResponseEntity.status(201).body(usuarioLombardiService.save(usuarioLombardi));
    }

    @PostMapping("/login")
    public ResponseEntity<LombardiResponseDto> post(@RequestBody @Valid LombardiResponseDto dto) {
       UsuarioLombardi usuario = usuarioLombardiService.findByEmailAndSenha(dto.getEmail(), dto.getSenha());

        if (usuario == null) {
            return ResponseEntity.status(404).build();
        }
            return ResponseEntity.status(200).body(UsuarioLombardiMapper.toResponseDto(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LombardiResponseDto> atualizar(@PathVariable Integer id, @RequestBody @Valid LombardiRequestDto lombardiAtualizado){

        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        } else if(Objects.isNull(lombardiAtualizado)){
            return ResponseEntity.status(400).build();
        }

        UsuarioLombardi userLombardiExists = usuarioLombardiService.update(id, UsuarioLombardiMapper.toModel(lombardiAtualizado));

        if(Objects.isNull(userLombardiExists)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(UsuarioLombardiMapper.toResponseDto(userLombardiExists));
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
