package tech.code.codetech.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.model.Produto;
import tech.code.codetech.service.ProdutoService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/produtos")
public class    ProdutoController {

    @Autowired
    private ProdutoService productService;

    @GetMapping
    public ResponseEntity<List<Produto>> listar(){
        List<Produto> listProducts = productService.findAll();

        if(listProducts.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> encontrarPorId(@PathVariable Integer id){
        Produto produtoEncontrado = productService.findById(id);

        if(Objects.isNull(produtoEncontrado)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(produtoEncontrado);
    }

    @PostMapping
    public ResponseEntity<Produto> post(@RequestBody @Valid Produto product){
        if(Objects.isNull(product)){
            return ResponseEntity.status(400).build();
        }
        Produto productSaved = productService.save(product);
        return ResponseEntity.status(201).body(productSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Integer id, @RequestBody @Valid Produto produtoAtualizado){

        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        } else if(Objects.isNull(produtoAtualizado)){
            return ResponseEntity.status(400).build();
        }

        Produto productExists = productService.update(id, produtoAtualizado);

        if(Objects.isNull(productExists)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(productExists);
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

