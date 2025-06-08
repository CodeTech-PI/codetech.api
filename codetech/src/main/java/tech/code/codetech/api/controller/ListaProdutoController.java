package tech.code.codetech.api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.lista.request.ListaProdutoAtualizacaoRequestDto;
import tech.code.codetech.dto.lista.request.ListaProdutoDeleteRequestDto;
import tech.code.codetech.dto.lista.request.ListaProdutoRequestDto;
import tech.code.codetech.dto.lista.response.ListaProdutoResponseDto;
import tech.code.codetech.mapper.ListaProdutoMapper;
import tech.code.codetech.model.ListaProduto;
import tech.code.codetech.service.ListaProdutoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Lista de Produto")
@RestController
@RequestMapping("/lista-produtos")
public class ListaProdutoController {

    @Autowired
    private ListaProdutoService listaProdutoService;

    // CONFIGURAÇÃO SWAGGER listar()
    @Operation(summary = "Listar todos os produtos", description = """
            Esse endpoint permite listar todos os produtos cadastrados:
            
            - Retorna uma lista de objetos representando cada produto.
            
            Respostas:
            
            - 200: Requisição sucedida. Retorna a lista de produtos em JSON.
            - 204: Nenhum produto encontrado.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ListaProdutoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado.")
    })
    @GetMapping
    public ResponseEntity<List<ListaProdutoResponseDto>> listar(){
        List<ListaProduto> listProdutos = listaProdutoService.findAll();

        if(listProdutos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        List<ListaProdutoResponseDto> resposta = new ArrayList<>();

        for (ListaProduto listaProduto : listProdutos) {
            resposta.add(ListaProdutoMapper.toResponseDto(listaProduto));
        }
        return ResponseEntity.status(200).body(resposta);
    }

    // CONFIGURAÇÃO SWAGGER encontrarPorId()
    @Operation(summary = "Buscar produto por ID", description = """
            Esse endpoint permite buscar um produto específico pelo seu ID.
            
            - Retorna o objeto `ListaProduto` correspondente ao ID fornecido.
            
            Respostas:
            
            - 200: Requisição sucedida. Retorna o produto solicitado em JSON.
            - 404: Produto não encontrado para o ID informado.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ListaProdutoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ListaProdutoResponseDto> encontrarPorId(@PathVariable int id){
        ListaProduto listaProdutoEncontrada = listaProdutoService.findById(id);

        if(Objects.isNull(listaProdutoEncontrada)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(ListaProdutoMapper.toResponseDto(listaProdutoEncontrada));
    }

    // CONFIGURAÇÃO SWAGGER post()
    @Operation(summary = "Criar produtos", description = """
            Esse endpoint permite criar novos produtos na lista.
            
            - Retorna a lista de produtos criados.
            
            Respostas:
            
            - 201: Requisição sucedida. Retorna a lista de produtos criados em JSON.
            - 400: Erro ao criar produtos. Verifique os dados fornecidos.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ListaProdutoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro ao criar produtos.")
    })
    @PostMapping
    public ResponseEntity<List<ListaProdutoResponseDto>> post(@RequestBody @Valid ListaProdutoRequestDto dto){
        List<ListaProduto> listaSalva = listaProdutoService.saveAll(ListaProdutoMapper.toModel(dto));
        return ResponseEntity.status(201).body(ListaProdutoMapper.toResponseDto(listaSalva));
    }

    // CONFIGURAÇÃO SWAGGER atualizar()
    @Operation(summary = "Atualizar produtos", description = """
            Esse endpoint permite atualizar produtos existentes na lista.
            
            - Retorna a lista de produtos atualizados.
            
            Respostas:
            
            - 200: Requisição sucedida. Retorna a lista de produtos atualizados em JSON.
            - 400: Erro ao atualizar produtos. Verifique os dados fornecidos.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ListaProdutoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar produtos.")
    })
    @PutMapping
    public ResponseEntity<List<ListaProdutoResponseDto>> atualizar(@RequestBody @Valid ListaProdutoAtualizacaoRequestDto listaAtualizada){

        List<ListaProduto> listaExiste = listaProdutoService.update(ListaProdutoMapper.toModel(listaAtualizada));

        if(listaExiste.isEmpty()){
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(200).body(ListaProdutoMapper.toResponseDto(listaExiste));
    }

    // CONFIGURAÇÃO SWAGGER deletar()
    @Operation(summary = "Deletar produtos", description = """
            Esse endpoint permite deletar produtos da lista.
            
            - Retorna uma resposta sem conteúdo se a deleção for bem-sucedida.
            
            Respostas:
            
            - 204: Requisição sucedida. Os produtos foram deletados com sucesso.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produtos deletados com sucesso.")
    })
    @DeleteMapping
    public ResponseEntity<Void> deletar(@RequestBody ListaProdutoDeleteRequestDto listaProdutoDeleteRequestDto){
        listaProdutoService.deleteAll(listaProdutoDeleteRequestDto.getProdutosIds());
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Buscar lista de produtos pelo ID do agendamento", description = """
            Esse endpoint permite buscar todos os produtos relacionados a um agendamento específico.
            
            - Retorna uma lista de produtos do agendamento especificado.
            
            Respostas:
            
            - 200: Lista de produtos retornada com sucesso.
            - 204: Nenhum produto encontrado para o agendamento.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso."),
            @ApiResponse(responseCode = "204", description = "Nenhum produto encontrado para o agendamento."),
            @ApiResponse(responseCode = "400", description = "Erro na requisição. Possivelmente parâmetros inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping("/buscar-lista/{idAgendamento}")
    public ResponseEntity<List<ListaProdutoResponseDto>> buscarListaDoAgendamento(@PathVariable int idAgendamento){
        List<ListaProduto> listaDeProdutosDoAgendamento = listaProdutoService.buscarListaProdutosPeloAgendamento(idAgendamento);

        if(listaDeProdutosDoAgendamento.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(ListaProdutoMapper.toResponseDto(listaDeProdutosDoAgendamento));

    }
}