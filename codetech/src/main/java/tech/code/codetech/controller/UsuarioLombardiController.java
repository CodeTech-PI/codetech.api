package tech.code.codetech.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.code.codetech.dto.lombardi.request.LombardiRequestDto;
import tech.code.codetech.dto.lombardi.response.LombardiResponseDto;
import tech.code.codetech.mapper.UsuarioLombardiMapper;
import tech.code.codetech.model.UsuarioLombardi;
import tech.code.codetech.service.UsuarioLombardiService;
import tech.code.codetech.service.autenticacao.dto.UsuarioLombardiLoginDto;
import tech.code.codetech.service.autenticacao.dto.UsuarioLombardiTokenDto;

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

//    @PostMapping("/login")
//    public ResponseEntity<UsuarioLombardiTokenDto> post(@RequestBody UsuarioLombardiLoginDto usuarioLombardiLoginDto) {
//        try {
//            UsuarioLombardiTokenDto usuarioToken = this.usuarioLombardiService.autenticar(usuarioLombardiLoginDto);
//            return ResponseEntity.status(200).body(usuarioToken);
//        } catch (ResponseStatusException ex) {
//            return ResponseEntity.status(ex.getStatusCode()).body(null);
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioLombardiTokenDto> post(@RequestBody UsuarioLombardiLoginDto usuarioLombardiLoginDto) {
        UsuarioLombardiTokenDto usuarioToken = this.usuarioLombardiService.autenticar(usuarioLombardiLoginDto);

        return ResponseEntity.status(200).body(usuarioToken);
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
