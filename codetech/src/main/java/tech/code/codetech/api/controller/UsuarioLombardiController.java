package tech.code.codetech.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.lombardi.request.LombardiRequestDto;
import tech.code.codetech.dto.lombardi.response.LombardiResponseDto;
import tech.code.codetech.mapper.UsuarioLombardiMapper;
import tech.code.codetech.model.UsuarioLombardi;
import tech.code.codetech.service.UsuarioLombardiService;
import tech.code.codetech.service.autenticacao.dto.UsuarioLombardiTokenDto;
import tech.code.codetech.service.autenticacao.dto.UsuarioLombardiLoginDto;

import java.util.Objects;

@Tag(name = "Usuário Lombardi")
@RestController
@RequestMapping("/lombardi")
public class UsuarioLombardiController {

    @Autowired
    private UsuarioLombardiService usuarioLombardiService;

    @Operation(summary = "Buscar usuário Lombardi por id", description = "Retorna um usuário Lombardi por id específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Usuário Lombardi encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LombardiResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Not Found - Usuário Lombardi não encontrado", content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<LombardiResponseDto> encontrarPorId(@PathVariable Integer id) {
        UsuarioLombardi usuarioLombardi = usuarioLombardiService.findById(id);

        if (Objects.isNull(usuarioLombardi)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(UsuarioLombardiMapper.toResponseDto(usuarioLombardi));
    }

    @Operation(summary = "Criar um usuário Lombardi", description = "Cria um novo usuário Lombardi no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created - Usuário Lombardi criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LombardiResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos fornecidos", content = @Content())
    })
    @PostMapping
    public ResponseEntity<Void> criar(@RequestBody @Valid LombardiRequestDto lombardiCriacaoDto) {
        this.usuarioLombardiService.criar(lombardiCriacaoDto);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "Login do usuário Lombardi", description = "Realiza o login de um usuário Lombardi")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Login realizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioLombardiTokenDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Not Found - Usuário Lombardi não encontrado", content = @Content())
    })
    @PostMapping("/login")
    public ResponseEntity<UsuarioLombardiTokenDto> login(@RequestBody UsuarioLombardiLoginDto dto) {
        UsuarioLombardiTokenDto usuarioLombardiTokenDto = this.usuarioLombardiService.autenticar(dto);

        if (usuarioLombardiTokenDto == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(usuarioLombardiTokenDto);
    }

    @Operation(summary = "Atualizar um usuário Lombardi", description = "Atualiza um usuário Lombardi por id específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - Usuário Lombardi atualizado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LombardiResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad Request - Dados inválidos fornecidos", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not Found - Usuário Lombardi não encontrado", content = @Content())
    })
    @PutMapping("/{id}")
    public ResponseEntity<LombardiResponseDto> atualizar(@PathVariable Integer id, @RequestBody @Valid LombardiRequestDto lombardiAtualizado) {
        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        } else if (Objects.isNull(lombardiAtualizado)) {
            return ResponseEntity.status(400).build();
        }

        UsuarioLombardi userLombardiExists = usuarioLombardiService.update(id, UsuarioLombardiMapper.toModel(lombardiAtualizado));

        if (Objects.isNull(userLombardiExists)) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(UsuarioLombardiMapper.toResponseDto(userLombardiExists));
    }

    @Operation(summary = "Deletar um usuário Lombardi", description = "Deleta um usuário Lombardi por id específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content - Usuário Lombardi deletado com sucesso", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Not Found - Usuário Lombardi não encontrado", content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        }
        boolean isDeleted = usuarioLombardiService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}
