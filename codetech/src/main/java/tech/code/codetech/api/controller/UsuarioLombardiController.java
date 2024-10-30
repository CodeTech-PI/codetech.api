package tech.code.codetech.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.lombardi.request.LombardiRequestDto;
import tech.code.codetech.dto.lombardi.response.LombardiResponseDto;
import tech.code.codetech.mapper.UsuarioLombardiMapper;
import tech.code.codetech.model.UsuarioLombardi;
import tech.code.codetech.service.UsuarioLombardiService;
import java.util.Objects;
import tech.code.codetech.service.autenticacao.dto.UsuarioLombardiTokenDto;
import tech.code.codetech.service.autenticacao.dto.UsuarioLombardiLoginDto;

@Tag(name = "Lombardi")
@RestController
@RequestMapping("/lombardi")
public class UsuarioLombardiController {

    @Autowired
    private UsuarioLombardiService usuarioLombardiService;

//    @GetMapping("/{id}")
//    public ResponseEntity<LombardiResponseDto> encontrarPorId(@PathVariable Integer id){
//        UsuarioLombardi usuarioLombardi = usuarioLombardiService.findById(id);
//
//        if(Objects.isNull(usuarioLombardi)){
//            return ResponseEntity.status(404).build();
//        }
//        return ResponseEntity.status(200).body(UsuarioLombardiMapper.toResponseDto(usuarioLombardi));
//    }

//    @PostMapping
//    @SecurityRequirement(name = "Bearer")
//    public ResponseEntity<Void> criar(@RequestBody @Valid LombardiRequestDto lombardiCriacaoDto){
//        this.usuarioLombardiService.criar(lombardiCriacaoDto);
//        return ResponseEntity.status(201).build();
//    }


    @PostMapping("/login")
    public ResponseEntity<UsuarioLombardiTokenDto> login(@RequestBody UsuarioLombardiLoginDto dto) {
        UsuarioLombardiTokenDto usuarioLombardiTokenDto = this.usuarioLombardiService.autenticar(dto);

        if (usuarioLombardiTokenDto == null) {
            return ResponseEntity.status(404).build();
        }
            return ResponseEntity.status(200).body(usuarioLombardiTokenDto);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<LombardiResponseDto> atualizar(@PathVariable Integer id, @RequestBody @Valid LombardiRequestDto lombardiAtualizado){
//
//        if(Objects.isNull(id) || id <= 0){
//            return ResponseEntity.status(404).build();
//        } else if(Objects.isNull(lombardiAtualizado)){
//            return ResponseEntity.status(400).build();
//        }
//
//        UsuarioLombardi userLombardiExists = usuarioLombardiService.update(id, UsuarioLombardiMapper.toModel(lombardiAtualizado));
//
//        if(Objects.isNull(userLombardiExists)){
//            return ResponseEntity.status(404).build();
//        }
//
//        return ResponseEntity.status(200).body(UsuarioLombardiMapper.toResponseDto(userLombardiExists));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletar(@PathVariable Integer id){
//
//        if(Objects.isNull(id) || id <= 0){
//            return ResponseEntity.status(404).build();
//        }
//        boolean isDeleted =  usuarioLombardiService.delete(id);
//        if(!isDeleted){
//            return ResponseEntity.status(404).build();
//        }
//        return ResponseEntity.status(204).build();
//    }
}
