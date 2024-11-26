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
import tech.code.codetech.dto.unidade.request.UnidadeRequestDto;
import tech.code.codetech.dto.unidade.response.UnidadeResponseDto;
import tech.code.codetech.mapper.UnidadeMapper;
import tech.code.codetech.model.Unidade;
import tech.code.codetech.service.UnidadeService;
import tech.code.codetech.api.controller.generico.fila.FilaObj;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag(name = "Unidade")
@RestController
@RequestMapping("/unidades")
public class UnidadeController {

    @Autowired
    private UnidadeService unidadeService;
    private FilaObj<Unidade> filaDeUnidades = new FilaObj<>(10);

    //CONFIGURAÇÃO SWAGGGER listar()
    @Operation(summary = "Listar todas as unidades", description = """
            Esse endpoint permite listar todas as unidades cadastradas no banco de dados.
            
            - Retorna uma lista de objetos representando cada unidade.
            
            Respostas:
            
            - 200: Requisição sucedida. Retorna a lista de unidades em JSON.
            - 204: Nenhuma unidade encontrada.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UnidadeResponseDto.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhuma unidade encontrada",
                    content = @Content()
            )
    })
    @GetMapping
    public ResponseEntity<List<UnidadeResponseDto>> listar() {
        List<Unidade> listUnidades = unidadeService.findAll();

        if (listUnidades.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<UnidadeResponseDto> resposta = new ArrayList<>();

        for (Unidade unidade : listUnidades) {
            resposta.add(UnidadeMapper.toResponseDto(unidade));
        }
        return ResponseEntity.status(200).body(resposta);
    }

    //CONFIGURAÇÃO SWAGGGER listarUnidadesOperacionais()
    @Operation(summary = "Listar unidades operantes", description = """
            Esse endpoint permite listar todas as unidades que estão operantes (não estão na fila)
            
            - Retorna uma lista de objetos representando cada unidade.
            
            Respostas:
            
            - 200: Requisição sucedida. Retorna a lista de unidades em JSON.
            - 204: Nenhuma unidade em operacional encontrada.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UnidadeResponseDto.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhuma unidade operacional encontrada.",
                    content = @Content()
            )
    })
    @GetMapping("/operacional")
    public ResponseEntity<List<UnidadeResponseDto>> listarUnidadesOperacionais() {
        List<Unidade> todasUnidades = unidadeService.findAll();
        List<Unidade> unidadesEmConstrucao = filaDeUnidades.getAll();
        List<Unidade> auxiliar = new ArrayList<>();

        for (Unidade unidade : todasUnidades) {
            if (!unidadesEmConstrucao.contains(unidade)) {
                auxiliar.add(unidade);
            }
        }

        if (auxiliar.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        //transformando em dtos
        List<UnidadeResponseDto> retorno = new ArrayList<>();

        for (Unidade unidade : auxiliar) {
            retorno.add(UnidadeMapper.toResponseDto(unidade));
        }

        return ResponseEntity.status(200).body(retorno);
    }

    //CONFIGURAÇÃO SWAGGGER listarUnidadesInoperantes()
    @Operation(summary = "Listar unidades inoperantes", description = """
            Esse endpoint permite listar todas as unidades que NÃO estão operantes (estão na fila)
            
            - Retorna uma lista de objetos representando cada unidade.
            
            Respostas:
            
            - 200: Requisição sucedida. Retorna a lista de unidades em JSON.
            - 204: Nenhuma unidade em construção encontrada.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UnidadeResponseDto.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhuma unidade em construção encontrada.",
                    content = @Content()
            )
    })
    @GetMapping("/inoperacional")
    public ResponseEntity<List<UnidadeResponseDto>> listarUnidadesInoperantes() {

        List<Unidade> unidadesEmConstrucao = filaDeUnidades.getAll();

        if (unidadesEmConstrucao.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<UnidadeResponseDto> retorno = new ArrayList<>();

        for (Unidade unidade : unidadesEmConstrucao) {
            retorno.add(UnidadeMapper.toResponseDto(unidade));
        }

        return ResponseEntity.status(200).body(retorno);
    }

    //CONFIGURAÇÃO SWAGGER tornarUnidadeOperacional()
    @Operation(summary = "Torna a unidade operacional", description = """
            Esse endpoint permite ativar uma unidade.
            
            - Retorna a unidade ativada.
            
            Respostas:
            
            - 201: Unidade criada com sucesso. Retorna a unidade em JSON.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnidadeResponseDto.class)
                    )
            )
    })
    @PostMapping
    @GetMapping("/tornar-operacional/{qtdOperacoes}")
    public ResponseEntity<List<UnidadeResponseDto>> tornarUnidadeOperacional(@PathVariable int qtdOperacoes) {

        List<Unidade> unidadesOperacionais = new ArrayList<>();

        if (filaDeUnidades.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        if (qtdOperacoes <= 0 || qtdOperacoes > filaDeUnidades.getTamanho()) {
            return ResponseEntity.status(400).build();
        }

        for (int i = 0; i < qtdOperacoes; i++) {
            Unidade unidade = filaDeUnidades.poll();
            unidadesOperacionais.add(unidade);
        }

        if (unidadesOperacionais.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        List<UnidadeResponseDto> unidadesResponseDto = new ArrayList<>();
        for (Unidade unidade : unidadesOperacionais) {
            unidadesResponseDto.add(UnidadeMapper.toResponseDto(unidade));
        }

        return ResponseEntity.status(200).body(unidadesResponseDto);
    }





    //CONFIGURAÇÃO SWAGGER buscarPorId()
    @Operation(summary = "Buscar uma unidade", description = """
            Esse endpoint permite buscar uma unidade específica pelo ID.
            
            - Retorna um objeto representando a unidade encontrada.
            
            Respostas:
            
            - 200: Requisição sucedida. Retorna a unidade em JSON.
            - 404: Unidade não encontrada, ID inválido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnidadeResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UnidadeResponseDto> buscarPorId(@PathVariable Integer id) {
        Unidade unidade = unidadeService.findById(id);

        if (Objects.isNull(unidade)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(UnidadeMapper.toResponseDto(unidade));
    }

    //CONFIGURAÇÃO SWAGGER post()
    @Operation(summary = "Criar uma unidade", description = """
            Esse endpoint permite criar uma nova unidade no banco de dados.
            
            - Retorna a unidade criada.
            
            Respostas:
            
            - 201: Unidade criada com sucesso. Retorna a unidade em JSON.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnidadeResponseDto.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<UnidadeResponseDto> post(@RequestBody @Valid UnidadeRequestDto dto) {
        Unidade unidade = unidadeService.save(UnidadeMapper.toModel(dto));
        filaDeUnidades.insert(unidade);
        return ResponseEntity.status(201).body(UnidadeMapper.toResponseDto(unidade));
    }

//    //CONFIGURAÇÃO SWAGGER postFila()
//    @Operation(summary = "Criar uma unidade", description = """
//            Esse endpoint permite criar uma nova unidade no banco de dados.
//
//            - Retorna a unidade criada.
//
//            Respostas:
//
//            - 201: Unidade criada com sucesso. Retorna a unidade em JSON.
//            """)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Created",
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = UnidadeResponseDto.class)
//                    )
//            )
//    })
//    @PostMapping
//    public ResponseEntity<UnidadeResponseDto> postFila(@RequestBody @Valid UnidadeRequestDto dto) {
//        Unidade unidade = unidadeService.save(UnidadeMapper.toModel(dto));
//        return ResponseEntity.status(201).body(UnidadeMapper.toResponseDto(unidade));
//    }

    //CONFIGURAÇÃO SWAGGER atualizar()
    @Operation(summary = "Atualizar uma unidade", description = """
            Esse endpoint permite atualizar uma unidade existente pelo ID.
            
            - Retorna a unidade atualizada.
            
            Respostas:
            
            - 200: Unidade atualizada com sucesso. Retorna a unidade em JSON.
            - 404: Unidade não encontrada, dados inválidos.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UnidadeResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada",
                    content = @Content()
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<UnidadeResponseDto> atualizar(@PathVariable Integer id, @RequestBody @Valid UnidadeRequestDto unidadeAtualizada) {

        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        } else if (Objects.isNull(unidadeAtualizada)) {
            return ResponseEntity.status(400).build();
        }

        Unidade unidadeExists = unidadeService.update(id, UnidadeMapper.toModel(unidadeAtualizada));

        if (Objects.isNull(unidadeExists)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(UnidadeMapper.toResponseDto(unidadeExists));
    }


    //CONFIGURAÇÃO SWAGGER deletar()
    @Operation(summary = "Deletar uma unidade", description = """
            Esse endpoint permite deletar uma unidade pelo ID.
            
            Respostas:
            
            - 204: Unidade deletada com sucesso. Não retorna conteúdo.
            - 404: Unidade não encontrada, dados inválidos.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content",
                    content = @Content()
            ),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada",
                    content = @Content()
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        }

        boolean isDeleted = unidadeService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}