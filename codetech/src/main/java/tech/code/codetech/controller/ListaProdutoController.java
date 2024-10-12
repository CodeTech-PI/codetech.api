package tech.code.codetech.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.dto.lista.request.ListaProdutoAtualizacaoRequestDto;
import tech.code.codetech.dto.lista.request.ListaProdutoRequestDto;
import tech.code.codetech.dto.lista.response.ListaProdutoResponseDto;
import tech.code.codetech.mapper.ListaProdutoMapper;
import tech.code.codetech.model.ListaProduto;
import tech.code.codetech.service.ListaProdutoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/lista-produtos")
public class ListaProdutoController {

    @Autowired
    private ListaProdutoService listaProdutoService;

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

    @GetMapping("/{id}")
    public ResponseEntity<ListaProdutoResponseDto> encontrarPorId(@PathVariable int id){
        ListaProduto listaProdutoEncontrada = listaProdutoService.findById(id);

        if(Objects.isNull(listaProdutoEncontrada)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(ListaProdutoMapper.toResponseDto(listaProdutoEncontrada));
    }

    @PostMapping
    public ResponseEntity<List<ListaProdutoResponseDto>> post(@RequestBody @Valid ListaProdutoRequestDto dto){
        List<ListaProduto> listaSalva = listaProdutoService.saveAll(ListaProdutoMapper.toModel(dto));
        return ResponseEntity.status(201).body(ListaProdutoMapper.toResponseDto(listaSalva));
    }

    @PutMapping
    public ResponseEntity<List<ListaProdutoResponseDto>> atualizar(@RequestBody @Valid ListaProdutoAtualizacaoRequestDto listaAtualizada){

        List<ListaProduto> listaExiste = listaProdutoService.update(ListaProdutoMapper.toModel(listaAtualizada));

        if(listaExiste.isEmpty()){
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(200).body(ListaProdutoMapper.toResponseDto(listaExiste));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        }
        boolean isDeleted = listaProdutoService.delete(id);
        if(!isDeleted){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(204).build();
    }
}