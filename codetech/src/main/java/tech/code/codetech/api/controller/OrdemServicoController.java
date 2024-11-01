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
import tech.code.codetech.dto.ordem.OrdemServicoLucroDto;
import tech.code.codetech.dto.ordem.request.OrdemServicoRequestDto;
import tech.code.codetech.dto.ordem.response.OrdemServicoResponseDto;
import tech.code.codetech.dto.produto.response.ProdutoResponseDto;
import tech.code.codetech.mapper.OrdemServicoMapper;
import tech.code.codetech.model.OrdemServico;
import tech.code.codetech.strategy.OrdemServicoInterface;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Tag(name = "Ordem de Serviço")
@RestController
@RequestMapping("/ordens-servicos")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoInterface ordemServicoService;

    //CONFIGURAÇÃO SWAGGER listar()
    @Operation(summary = "Listar todas as ordens de serviço", description = """
            Esse endpoint permite listar todas as ordens de serviço no banco de dados.
                        
            Respostas:
            - 200: OK. Retorna a lista de ordens de serviço em JSON.
            - 204: Nenhuma ordem de serviço encontrada.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdemServicoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "204", description = "Nenhuma ordem de serviço encontrada.")
    })
    @GetMapping
    public ResponseEntity<List<OrdemServicoResponseDto>> listar() {
        List<OrdemServicoLucroDto> listOrdemServico = ordemServicoService.buscarTodos();

        if (listOrdemServico.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<OrdemServicoResponseDto> resposta = new ArrayList<>();

        for (OrdemServicoLucroDto ordemServico : listOrdemServico) {
            resposta.add(OrdemServicoMapper.toResponseDto(ordemServico));
        }
        return ResponseEntity.status(200).body(resposta);
    }

    //CONFIGURAÇÃO SWAGGER encontrarPorId()
    @Operation(summary = "Buscar uma ordem de serviço por ID", description = """
            Esse endpoint permite buscar uma ordem de serviço específica pelo ID.
                        
            Respostas:
            - 200: OK. Retorna a ordem de serviço encontrada em JSON.
            - 404: Ordem de serviço não encontrada para o ID fornecido.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdemServicoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Ordem de serviço não encontrada.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDto> encontrarPorId(@PathVariable int id) {
        OrdemServicoLucroDto ordemServicoEncontrada = ordemServicoService.buscarPorId(id);
        return ResponseEntity.status(200).body(OrdemServicoMapper.toResponseDto(ordemServicoEncontrada));
    }

    //CONFIGURAÇÃO SWAGGER post()
    @Operation(summary = "Criar uma ordem de serviço", description = """
            Esse endpoint permite criar uma nova ordem de serviço.
                        
            Respostas:
            - 201: Ok. Retorna a ordem de serviço criada em JSON.
            - 400: Erro ao criar a ordem de serviço, dados inválidos.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdemServicoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro ao criar a ordem de serviço.")
    })
    @PostMapping
    public ResponseEntity<OrdemServicoResponseDto> post(@RequestBody @Valid OrdemServicoRequestDto dto) {
        OrdemServicoLucroDto ordemServicoSalva = ordemServicoService.executarOrdemServico(OrdemServicoMapper.toModel(dto));
        return ResponseEntity.status(201).body(OrdemServicoMapper.toResponseDto(ordemServicoSalva));
    }

    //CONFIGURAÇÃO SWAGGER atualizar()
    @Operation(summary = "Atualizar uma ordem de serviço", description = """
            Esse endpoint permite atualizar uma ordem de serviço específica pelo ID.
                        
            Respostas:
            - 200: OK. Retorna a ordem de serviço atualizada em JSON.
            - 400: Erro ao atualizar a ordem de serviço, dados inválidos.
            - 404: Ordem de serviço não encontrada.
            """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = OrdemServicoResponseDto.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar a ordem de serviço."),
            @ApiResponse(responseCode = "404", description = "Ordem de serviço não encontrada.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrdemServicoResponseDto> atualizar(@PathVariable int id, @RequestBody @Valid OrdemServicoRequestDto ordemServico) {
        OrdemServicoLucroDto ordemServicoAtualizada = ordemServicoService.atualizarOrdemServico(OrdemServicoMapper.toModel(ordemServico), id);

        if (Objects.isNull(ordemServicoAtualizada)) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.status(200).body(OrdemServicoMapper.toResponseDto(ordemServicoAtualizada));
    }


    //CONFIGURAÇÃO SWAGGER deletar()
//    @Operation(summary = "Deletar uma ordem de serviço", description = """
//            Esse endpoint permite deletar uma ordem de serviço específica pelo ID.
//
//            Respostas:
//            - 204: No Content. Ordem de serviço deletada com sucesso.
//            - 404: Ordem de serviço não encontrada.
//            """)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "No Content. Ordem de serviço deletada com sucesso."),
//            @ApiResponse(responseCode = "404", description = "Ordem de serviço não encontrada.")
//    })
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletar(@PathVariable int id){
//        if(Objects.isNull(id) || id <= 0){
//            return ResponseEntity.status(404).build();
//        }
//        boolean isDeleted = ordemServicoService.delete(id);
//        if(!isDeleted){
//            return ResponseEntity.status(404).build();
//        }
//        return ResponseEntity.status(204).build();
//    }

    public static void ordenarPorValor(List<OrdemServico> vetor) {
        for (int i = 0; i < vetor.size(); i++) {
            int indMaior = i;
            for (int j = i + 1; j < vetor.size(); j++) {

                if (vetor.get(j).getValorTatuagem().compareTo(vetor.get(indMaior).getValorTatuagem()) > 0) {
                    indMaior = j;
                }
            }
            if (indMaior != i) {

                OrdemServico aux = vetor.get(i);
                vetor.set(i, vetor.get(indMaior));
                vetor.set(indMaior, aux);
            }
        }
        for (OrdemServico os : vetor) {
            System.out.println(os.getValorTatuagem());
        }
    }

    @GetMapping("/buscar-preco/{valorProcurado}")
    public ResponseEntity<List<OrdemServico>> buscarPorPreco(@PathVariable double valorProcurado) {
        List<OrdemServico> ordemServico = ordemServicoService.findAll();
        ordenarPorValor(ordemServico);
        List<OrdemServico> encontrados = new ArrayList<>();

        int indinf = 0;
        int indsup = ordemServico.size() - 1;
        BigDecimal valorBuscado = BigDecimal.valueOf(valorProcurado);

        while (indinf <= indsup) {
            int meio = (indinf + indsup) / 2;
            BigDecimal valorMeio = ordemServico.get(meio).getValorTatuagem();

            if (valorMeio.compareTo(valorBuscado) == 0) {
                encontrados.add(ordemServico.get(meio));
                int indexEsquerda = meio - 1;
                while (indexEsquerda >= 0 && ordemServico.get(indexEsquerda).getValorTatuagem().compareTo(valorBuscado) == 0) {
                    encontrados.add(ordemServico.get(indexEsquerda));
                    indexEsquerda--;
                }
                int indexDireita = meio + 1;
                while (indexDireita < ordemServico.size() && ordemServico.get(indexDireita).getValorTatuagem().compareTo(valorBuscado) == 0) {
                    encontrados.add(ordemServico.get(indexDireita));
                    indexDireita++;
                }
                break;
            } else if (valorBuscado.compareTo(valorMeio) < 0) {
                indsup = meio - 1;
            } else {
                indinf = meio + 1;
            }
        }
        if (!encontrados.isEmpty()) {
            return ResponseEntity.ok(encontrados);
        } else {
            return ResponseEntity.status(204).build();
        }
    }
}