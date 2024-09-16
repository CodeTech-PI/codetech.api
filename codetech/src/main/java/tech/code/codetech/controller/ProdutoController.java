package tech.code.codetech.controller;

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
import tech.code.codetech.dto.produto.request.ProdutoRequestDto;
import tech.code.codetech.dto.produto.response.ProdutoResponseDto;
import tech.code.codetech.mapper.ProdutoMapper;
import tech.code.codetech.model.Produto;
import tech.code.codetech.service.ProdutoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag(name = "Produto")
@RestController
@RequestMapping("/produtos")
public class    ProdutoController {

    @Autowired
    private ProdutoService productService;

    @Operation(summary = "Listar todos os produtos", description = """
            # Listar todos os produtos
            ---
            O endpoint lista todos os produtos cadastrados no estoque
            """)
    @ApiResponses(value = {
            @ApiResponse (responseCode = "200", description = "Listar todos os produtos",
            content = @Content (
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProdutoResponseDto.class))
            )
            ),
            @ApiResponse(responseCode = "204", description = "Quando não tem produtos cadastrados",
            content = @Content ()
            )
    })
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> listar(){
        List<Produto> listProducts = productService.findAll();

        if(listProducts.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        List<ProdutoResponseDto> resposta = new ArrayList<>();

        for (Produto produto : listProducts) {
            resposta.add(ProdutoMapper.toResponseDto(produto));
        }
        return ResponseEntity.status(200).body(resposta);
    }

    @Operation(summary = "Buscar Produto por ID", description = """
            # Buscar produto por id
            ---
            O endpoint encontra um produto específico por id
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mostra um produto específico",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProdutoResponseDto.class)
            )
            ),
            @ApiResponse(responseCode = "404", description = "Quando não encontra o produto",
                    content = @Content()
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> encontrarPorId(@PathVariable Integer id){
        Produto produtoEncontrado = productService.findById(id);

        if(Objects.isNull(produtoEncontrado)){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(ProdutoMapper.toResponseDto(produtoEncontrado));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDto> post(@RequestBody @Valid ProdutoRequestDto dto){
        Produto productSaved = productService.save(ProdutoMapper.toModel(dto));
        return ResponseEntity.status(201).body(ProdutoMapper.toResponseDto(productSaved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> atualizar(@PathVariable Integer id, @RequestBody @Valid ProdutoRequestDto produtoAtualizado){

        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        } else if(Objects.isNull(produtoAtualizado)){
            return ResponseEntity.status(400).build();
        }

        Produto productExists = productService.update(id, ProdutoMapper.toModel(produtoAtualizado));

        if(Objects.isNull(productExists)){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(ProdutoMapper.toResponseDto(productExists));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){

        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        }
        boolean isDeleted =  productService.delete(id);
        if(!isDeleted){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}