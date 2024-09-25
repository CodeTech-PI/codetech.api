package tech.code.codetech.controller;

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
    @Operation(summary = "", description = """
            # Listar todos os faturamentos
            ---
            Lista todos os faturamentos no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )
            )
    })
    @GetMapping
   public ResponseEntity<List<FaturamentoResponseDto>> listar(){
        List<Faturamento> listFaturamento = faturamentoService.findAll();

        if(listFaturamento.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        List<FaturamentoResponseDto> resposta = new ArrayList<>();

        for (Faturamento faturamento : listFaturamento) {
            resposta.add(FaturamentoMapper.toResponseDto(faturamento));
        }
        return ResponseEntity.status(200).body(resposta);
    }
    @Operation(summary = "", description = """
            # Buscar faturamento por id
            ---
            Retorna um faturamento por id específico
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<FaturamentoResponseDto> encontrarPorId(@PathVariable int id){
        Faturamento faturamentoEncontrado = faturamentoService.findById(id);

        if(Objects.isNull(faturamentoEncontrado)){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(FaturamentoMapper.toResponseDto(faturamentoEncontrado));
    }
    @Operation(summary = "", description = """
            # Criar um faturamento
            ---
            Cria um novo faturamento no banco de dados
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<FaturamentoResponseDto> post(@RequestBody @Valid FaturamentoRequestDto dto){
        Faturamento faturamentoSalvo = faturamentoService.save(FaturamentoMapper.toModel(dto));
        return ResponseEntity.status(201).body(FaturamentoMapper.toResponseDto(faturamentoSalvo));
    }
    @Operation(summary = "", description = """
            # Atualizar um faturamento
            ---
            Atualiza um faturamento por id específico
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<FaturamentoResponseDto> atualizar(@PathVariable int id, @RequestBody @Valid FaturamentoRequestDto faturamentoAtualizado){

        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        } else if(Objects.isNull(faturamentoAtualizado)){
            return ResponseEntity.status(400).build();
        }

        Faturamento faturamentoExiste = faturamentoService.update(id, FaturamentoMapper.toModel(faturamentoAtualizado));

        if(Objects.isNull(faturamentoExiste)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(FaturamentoMapper.toResponseDto(faturamentoExiste));
    }
    @Operation(summary = "", description = """
            # Deletar um faturamento
            ---
            Deleta um faturamento por id específico
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        }
        faturamentoService.delete(id);
        return ResponseEntity.status(204).build();
    }

}