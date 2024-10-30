package tech.code.codetech.api.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.produto.response.ProdutoResponseDto;
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

@Tag(name = "Usuário")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //CONFIGURAÇÃO SWAGGER listar()
    @Operation(summary = "Listar todos os usuários", description = """
        Esse endpoint permite listar todos os usuários cadastrados no banco de dados.
        
        - Retorna uma lista de objetos representando cada usuário.
        
        Respostas:
        
        - 200: Requisição sucedida. Retorna a lista de usuários em JSON.
        - 204: Nenhum usuário encontrado.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsuariosResponseDto.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado")
    })
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

    //CONFIGURAÇÂO SWAGGER encontrarPorId()
    @Operation(summary = "Buscar usuário por id", description = """
        Esse endpoint permite buscar um usuário específico pelo ID.
        
        - Retorna um objeto representando o usuário encontrado.
        
        Respostas:
        
        - 200: Requisição sucedida. Retorna o usuário em JSON.
        - 404: Usuário não encontrado, ID inválido.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuariosResponseDto> encontrarPorId(@PathVariable Integer id) {
        Usuario usuarioEncontrado = usuarioService.findById(id);

        if (Objects.isNull(usuarioEncontrado)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(UsuarioMapper.toResponseDto(usuarioEncontrado));
    }

    //CONFIGURAÇÂO SWAGGER post()
    @Operation(summary = "Criar um usuário", description = """
        Esse endpoint permite criar um novo usuário no banco de dados.
        
        - Retorna o usuário criado.
        
        Respostas:
        
        - 201: Usuário criado com sucesso. Retorna o usuário em JSON.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosResponseDto.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<UsuariosResponseDto> post(@RequestBody @Valid UsuariosRequestDto dto) {
        Usuario usuarioSaved = usuarioService.save(UsuarioMapper.toModel(dto));
        return ResponseEntity.status(201).body(UsuarioMapper.toResponseDto(usuarioSaved));
    }

    //CONFIGURAÇÂO SWAGGER atualizar()
    @Operation(summary = "Atualizar um usuário", description = """
        Esse endpoint permite atualizar um usuário existente pelo ID.
        
        - Retorna o usuário atualizado.
        
        Respostas:
        
        - 200: Usuário atualizado com sucesso. Retorna o usuário em JSON.
        - 404: Usuário não encontrado, dados inválidos.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuariosResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
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

    //CONFIGURAÇÂO SWAGGER deletar()
    @Operation(summary = "Deletar um usuário", description = """
        Esse endpoint permite deletar um usuário pelo ID.
        
        Respostas:
        
        - 204: Usuário deletado com sucesso. Não retorna conteúdo.
        - 404: Usuário não encontrado, dados inválidos.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content",
                    content = @Content()
            ),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
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