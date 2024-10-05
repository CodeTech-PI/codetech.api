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
import tech.code.codetech.dto.ordem.OrdemServicoLucroDto;
import tech.code.codetech.dto.ordem.request.OrdemServicoRequestDto;
import tech.code.codetech.dto.ordem.response.OrdemServicoResponseDto;
import tech.code.codetech.dto.produto.response.ProdutoResponseDto;
import tech.code.codetech.mapper.OrdemServicoMapper;
import tech.code.codetech.model.OrdemServico;
import tech.code.codetech.service.OrdemServicoService;
import tech.code.codetech.strategy.OrdemServicoInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Tag(name = "Ordem de Serviço")
@RestController
@RequestMapping("/ordens-servicos")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoInterface ordemServicoService;

//    @Operation(summary = "", description = """
//            # Listar todas as ordens de serviço
//            ---
//            Lista todas as ordens de serviço no banco de dados
//            """)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK",
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = ProdutoResponseDto.class)
//                    )
//            )
//    })
//    @GetMapping
//    public ResponseEntity<List<OrdemServicoResponseDto>> listar(){
//        List<OrdemServico> listOrdemServico = ordemServicoService.findAll();
//
//        if(listOrdemServico.isEmpty()){
//            return ResponseEntity.status(204).build();
//        }
//        List<OrdemServicoResponseDto> resposta = new ArrayList<>();
//
//        for (OrdemServico ordemServico : listOrdemServico) {
//            resposta.add(OrdemServicoMapper.toResponseDto(ordemServico));
//        }
//        return ResponseEntity.status(200).body(resposta);
//    }
//    @Operation(summary = "", description = """
//            # Buscar uma ordem de serviço por id
//            ---
//            Retorna uma ordem de serviço por id específico
//            """)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK",
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = ProdutoResponseDto.class)
//                    )
//            )
//    })
//    @GetMapping("/{id}")
//    public ResponseEntity<OrdemServicoResponseDto> encontrarPorId(@PathVariable int id){
//        OrdemServico ordemServicoEncontrada = ordemServicoService.findById(id);
//
//        if(Objects.isNull(ordemServicoEncontrada)){
//            return ResponseEntity.status(404).build();
//        }
//
//        return ResponseEntity.status(200).body(OrdemServicoMapper.toResponseDto(ordemServicoEncontrada));
//    }
    @Operation(summary = "", description = """
            # Criar uma ordem de serviço
            ---
            Cria uma nova ordem de serviço
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
    public ResponseEntity<OrdemServicoResponseDto> post(@RequestBody @Valid OrdemServicoRequestDto dto){
        OrdemServicoLucroDto ordemServicoSalva = ordemServicoService.executarOrdemServico(OrdemServicoMapper.toModel(dto));
        return ResponseEntity.status(201).body(OrdemServicoMapper.toResponseDto(ordemServicoSalva));
    }

//    @Operation(summary = "", description = """
//            # Atualizar uma ordem de serviço
//            ---
//            Atualiza uma ordem de serviço por id específico
//            """)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "OK",
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = ProdutoResponseDto.class)
//                    )
//            )
//    })
//    @PutMapping("/{id}")
//    public ResponseEntity<OrdemServicoResponseDto> atualizar(@PathVariable int id, @RequestBody @Valid OrdemServicoRequestDto ordemServicoAtualizada){
//
//        if(Objects.isNull(id) || id <= 0){
//            return ResponseEntity.status(404).build();
//        } else if(Objects.isNull(ordemServicoAtualizada)){
//            return ResponseEntity.status(400).build();
//        }
//
//        OrdemServico ordemServicoExiste = ordemServicoService.update(id, OrdemServicoMapper.toModel(ordemServicoAtualizada));
//
//        if(Objects.isNull(ordemServicoExiste)){
//            return ResponseEntity.status(404).build();
//        }
//
//        return ResponseEntity.status(200).body(OrdemServicoMapper.toResponseDto(ordemServicoExiste));
//    }
    @Operation(summary = "", description = """
            # Deletar uma ordem de serviço
            ---
            Deleta uma ordem de serviço por id específico
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
        boolean isDeleted = ordemServicoService.delete(id);
        if(!isDeleted){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}