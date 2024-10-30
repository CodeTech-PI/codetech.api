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
public class ProdutoController {

    @Autowired
    private ProdutoService productService;

    // CONFIGURAÇÃO SWAGGER listar()
    @Operation(summary = "Listar todos os produtos", description = """
        Esse endpoint permite a listagem de todos os produtos cadastrados no estoque:
        
        - Retorna uma lista de objetos representando cada produto.
        
        Respostas:
        
        - 200: Requisição sucedida. Retorna a lista de produtos em JSON.
        - 204: Nenhum produto encontrado.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar todos os produtos",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProdutoResponseDto.class))
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado")
    })
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDto>> listar() {
        List<Produto> listProducts = productService.findAll();

        if (listProducts.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<ProdutoResponseDto> resposta = new ArrayList<>();

        for (Produto produto : listProducts) {
            resposta.add(ProdutoMapper.toResponseDto(produto));
        }
        return ResponseEntity.status(200).body(resposta);
    }


    // CONFIGURAÇÃO SWAGGER encontrarPorId()
    @Operation(summary = "Buscar produto por id", description = """
        Esse endpoint permite retornar um produto específico pelo seu ID.
        
        - Retorna um objeto representando o produto se encontrado.
        
        Respostas:
        
        - 200: Requisição sucedida. Retorna o produto em JSON.
        - 404: Produto não encontrado, ID fornecido inválido.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> encontrarPorId(@PathVariable Integer id) {
        Produto produtoEncontrado = productService.findById(id);

        if (Objects.isNull(produtoEncontrado)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(ProdutoMapper.toResponseDto(produtoEncontrado));
    }

    // CONFIGURAÇÃO SWAGGER post()
    @Operation(summary = "Criar um produto", description = """
        Esse endpoint permite criar um novo produto no sistema.
        
        - Requer um objeto ProdutoRequestDto no corpo da requisição.
        
        Respostas:
        
        - 201: Produto criado com sucesso. Retorna o objeto do produto criado em JSON.
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
    public ResponseEntity<ProdutoResponseDto> post(@RequestBody @Valid ProdutoRequestDto dto) {
        Produto productSaved = productService.save(ProdutoMapper.toModel(dto)); //, dto.getCategoriaId()
        return ResponseEntity.status(201).body(ProdutoMapper.toResponseDto(productSaved));
    }

    // CONFIGURAÇÃO SWAGGER atualizar()
    @Operation(summary = "Atualizar um produto", description = """
        Esse endpoint permite atualizar um produto específico pelo seu ID.
        
        - Requer um objeto ProdutoRequestDto no corpo da requisição para os novos dados do produto.
        
        Respostas:
        
        - 200: Produto atualizado com sucesso. Retorna o objeto do produto atualizado em JSON.
        - 400: Solicitação inválida.
        - 404: Produto não encontrado.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProdutoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> atualizar(@PathVariable Integer id, @RequestBody @Valid ProdutoRequestDto produtoAtualizado) {

        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        } else if (Objects.isNull(produtoAtualizado)) {
            return ResponseEntity.status(400).build();
        }

        Produto productExists = productService.update(id, ProdutoMapper.toModel(produtoAtualizado));

        if (Objects.isNull(productExists)) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(ProdutoMapper.toResponseDto(productExists));
    }

    // CONFIGURAÇÃO SWAGGER deletar()
    @Operation(summary = "Deletar um produto", description = """
        Esse endpoint permite deletar um produto específico pelo seu ID.
        
        - Requer o ID do produto a ser deletado como parâmetro na URL.
        
        Respostas:
        
        - 204: Produto deletado com sucesso. Não retorna conteúdo.
        - 404: Produto não encontrado.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content",
                    content = @Content()
            ),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {

        if (Objects.isNull(id) || id <= 0) {
            return ResponseEntity.status(404).build();
        }
        boolean isDeleted = productService.delete(id);
        if (!isDeleted) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }

    // CONFIGURAÇÃO SWAGGER exportarProdutosParaCsv()
    @Operation(summary = "Exportar arquivos dos produtos", description = """
        Esse endpoint permite exportar todos os produtos cadastrados no estoque para um arquivo CSV.
        
        - Retorna uma mensagem de sucesso ao concluir a exportação.
        
        Respostas:
        
        - 200: Exportação realizada com sucesso. Retorna uma mensagem indicando que os produtos foram exportados.
        - 204: Nenhum produto encontrado para exportação.
        """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok. Exportação realizada com sucesso.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado para exportação.",
                    content = @Content()
            )
    })
    @GetMapping("/exportar")
    public ResponseEntity<String> exportarProdutosParaCsv() {
        List<Produto> produtos = productService.findAll();
        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        productService.exportarProdutosParaCsv();
        return ResponseEntity.ok("Produtos exportados com sucesso para o arquivo produtos.csv");
    }
}