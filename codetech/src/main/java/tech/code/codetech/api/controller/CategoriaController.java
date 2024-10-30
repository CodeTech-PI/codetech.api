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
import tech.code.codetech.dto.agendamento.response.AgendamentoResponseDto;
import tech.code.codetech.dto.categoria.request.CategoriaRequestDto;
import tech.code.codetech.dto.categoria.response.CategoriaResponseDto;
import tech.code.codetech.dto.produto.request.ProdutoRequestDto;
import tech.code.codetech.mapper.CategoriaMapper;
import tech.code.codetech.model.Categoria;
import tech.code.codetech.service.CategoriaService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag(name = "Categoria")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    public CategoriaService categoriaService;

    //CONFIGURAÇÂO SWAGGER listar()
    @Operation(summary = "Listar categorias", description = """
            Esse endpoint permite a listagem de todas as categorias cadastradas no sistema:
            
            - Retorna uma lista de objetos representando cada categoria.
            
            Respostas:
            
            - 200: Requisição sucedida. Retorna a lista de categorias em JSON.
            - 204: Nenhuma categoria encontrada.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhuma categoria encontrada")
    })
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

    //CONFIGURAÇÂO SWAGGER buscarPorId()
    @Operation(summary = "Buscar categoria por ID", description = """
            Esse endpoint permite buscar uma categoria específica pelo seu ID.
            
            - Retorna o objeto `Categoria` correspondente ao ID fornecido como parâmetro.
            
            Respostas:
            - 200: Requisição sucedida. Retorna a categoria solicitada em JSON.
            - 404: Categoria não encontrada para o ID informado.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> buscarPorId(@PathVariable Integer id) {
        Categoria categoria = categoriaService.findById(id);

        if (Objects.isNull(categoria)) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(CategoriaMapper.toResponseDto(categoria));
    }

    //CONFIGURAÇÃO SWAGGER salvar()
    @Operation(summary = "Salvar nova categoria", description = """
            Esse endpoint permite a criação de uma nova categoria.
            
            - Recebe os dados da categoria no corpo da requisição e retorna o objeto `Categoria` criado.
            
            Respostas:
            - 201: Categoria criada com sucesso. Retorna a categoria criada em JSON.
            - 400: Dados inválidos para a criação da categoria.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para a criação da categoria")
    })
    @PostMapping
    public ResponseEntity<CategoriaResponseDto> salvar(@RequestBody @Valid CategoriaRequestDto dto) {
        Categoria categoria = categoriaService.save(CategoriaMapper.toModel(dto));
        return ResponseEntity.status(201).body(CategoriaMapper.toResponseDto(categoria));
    }

    //CONFIGURAÇÃO SWAGGER atualizar()
    @Operation(summary = "Atualizar categoria", description = """
            Esse endpoint permite atualizar uma categoria existente pelo seu ID.
            
            - Recebe os dados atualizados da categoria no corpo da requisição e retorna o objeto `Categoria` atualizado.
            
            Respostas:
            - 200: Atualização realizada com sucesso. Retorna a categoria atualizada em JSON.
            - 404: Categoria não encontrada para o ID informado.
            - 400: Dados inválidos para a atualização da categoria.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para a atualização da categoria")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> atualizar(@PathVariable Integer id, @RequestBody @Valid CategoriaRequestDto categoriaAtualizada) {
        Categoria categoria = categoriaService.update(id, CategoriaMapper.toModel(categoriaAtualizada));
        return ResponseEntity.status(200).body(CategoriaMapper.toResponseDto(categoria));
    }

    //CONFIGURAÇÃO SWAGGER deletar()
    @Operation(summary = "Deletar categoria", description = """
            Esse endpoint permite deletar uma categoria específica pelo seu ID.
            
            - Exclui a categoria correspondente ao ID fornecido como parâmetro.
            
            Respostas:
            - 204: Exclusão realizada com sucesso. Não retorna conteúdo.
            - 404: Categoria não encontrada para o ID informado.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exclusão realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
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
