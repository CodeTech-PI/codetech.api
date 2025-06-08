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
import tech.code.codetech.dto.faturamento.request.FaturamentoRequestDto;
import tech.code.codetech.dto.faturamento.response.FaturamentoResponseDto;
import tech.code.codetech.dto.produto.response.ProdutoResponseDto;
import tech.code.codetech.mapper.FaturamentoMapper;
import tech.code.codetech.model.Faturamento;
import tech.code.codetech.service.FaturamentoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag(name = "Faturamento")
@RestController
@RequestMapping("/faturamentos")
public class FaturamentoController {

    @Autowired
    private FaturamentoService faturamentoService;

    //CONFIGURAÇÃO SWAGGER listar()
    @Operation(summary = "Listar todos os faturamentos", description = """
            Esse endpoint permite listar todos os faturamentos cadastrados no sistema.
            
            - Retorna uma lista de objetos representando cada faturamento.
            
            Respostas:
            - 200: Requisição sucedida. Retorna a lista de faturamentos em JSON.
            - 204: Nenhum faturamento encontrado.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FaturamentoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhum faturamento encontrado")
    })
    @GetMapping
    public ResponseEntity<List<FaturamentoResponseDto>> listar() {
        List<Faturamento> listFaturamento = faturamentoService.findAll();

        if (listFaturamento.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<FaturamentoResponseDto> resposta = new ArrayList<>();

        for (Faturamento faturamento : listFaturamento) {
            resposta.add(FaturamentoMapper.toResponseDto(faturamento));
        }
        return ResponseEntity.status(200).body(resposta);
    }

    //CONFIGURAÇÃO SWAGGER encontrarPorId()
    @Operation(summary = "Buscar faturamento por ID", description = """
            Esse endpoint permite buscar um faturamento específico pelo seu ID.
            
            - Retorna o objeto `Faturamento` correspondente ao ID fornecido como parâmetro.
            
            Respostas:
            - 200: Requisição sucedida. Retorna o faturamento solicitado em JSON.
            - 404: Faturamento não encontrado para o ID informado.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FaturamentoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Faturamento não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FaturamentoResponseDto> encontrarPorId(@PathVariable int id) {
        Faturamento faturamentoEncontrado = faturamentoService.findById(id);

        if (Objects.isNull(faturamentoEncontrado)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(FaturamentoMapper.toResponseDto(faturamentoEncontrado));
    }


//    // CONFIGURAÇÃO SWAGGER post()
//    @Operation(summary = "Criar um faturamento", description = """
//            Esse endpoint permite criar um novo faturamento no banco de dados.
//
//            Respostas:
//            - 201: Faturamento criado com sucesso.
//            """)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Created",
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = FaturamentoResponseDto.class)
//                    )
//            )
//    })
//    @PostMapping
//    public ResponseEntity<FaturamentoResponseDto> post(@RequestBody @Valid FaturamentoRequestDto dto) {
//        Faturamento faturamentoSalvo = faturamentoService.save(FaturamentoMapper.toModel(dto));
//        return ResponseEntity.status(201).body(FaturamentoMapper.toResponseDto(faturamentoSalvo));
//    }
//
//    // CONFIGURAÇÃO SWAGGER atualizar()
//    @Operation(summary = "Atualizar um faturamento", description = """
//            Esse endpoint permite atualizar um faturamento específico pelo seu ID.
//
//            Respostas:
//            - 200: Faturamento atualizado com sucesso.
//            - 400: Requisição inválida, dados do faturamento não fornecidos.
//            - 404: Faturamento não encontrado para o ID informado.
//            """)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK",
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = FaturamentoResponseDto.class)
//                    )
//            ),
//            @ApiResponse(responseCode = "400", description = "Dados não fornecidos ou inválidos"),
//            @ApiResponse(responseCode = "404", description = "Faturamento não encontrado para o ID fornecido")
//    })
//    @PutMapping("/{id}")
//    public ResponseEntity<FaturamentoResponseDto> atualizar(@PathVariable int id, @RequestBody @Valid FaturamentoRequestDto faturamentoAtualizado) {
//
//        if (Objects.isNull(id) || id <= 0) {
//            return ResponseEntity.status(404).build();
//        } else if (Objects.isNull(faturamentoAtualizado)) {
//            return ResponseEntity.status(400).build();
//        }
//
//        Faturamento faturamentoExiste = faturamentoService.update(id, FaturamentoMapper.toModel(faturamentoAtualizado));
//
//        if (Objects.isNull(faturamentoExiste)) {
//            return ResponseEntity.status(404).build();
//        }
//
//        return ResponseEntity.status(200).body(FaturamentoMapper.toResponseDto(faturamentoExiste));
//    }


    // CONFIGURAÇÃO SWAGGER deletar()
    @Operation(summary = "Deletar um faturamento", description = """
            Esse endpoint permite deletar um faturamento específico pelo seu ID.
            
            Respostas:
            - 204: Faturamento deletado com sucesso.
            - 404: Faturamento não encontrado para o ID informado.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "404", description = "Faturamento não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id) {
        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        }
        faturamentoService.delete(id);
        return ResponseEntity.status(204).build();
    }

}