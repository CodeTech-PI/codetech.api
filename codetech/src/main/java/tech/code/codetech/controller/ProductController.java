package tech.code.codetech.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.code.codetech.model.Product;
import tech.code.codetech.service.ProductService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listar(){
        List<Product> listProducts = productService.findAll();

        if(listProducts.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(listProducts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> encontrarPorId(@PathVariable Integer id){
        Product produtoEncontrado = productService.findById(id);

        if(Objects.isNull(produtoEncontrado)){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(produtoEncontrado);
    }

    @PostMapping
    public ResponseEntity<Product> post(@RequestBody @Valid Product product){
        if(Objects.isNull(product)){
            return ResponseEntity.status(400).build();
        }
        Product productSaved = productService.save(product);
        return ResponseEntity.status(201).body(productSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> atualizar(@PathVariable Integer id, @RequestBody @Valid Product produtoAtualizado){

        if(Objects.isNull(id) || id <= 0){
            return ResponseEntity.status(404).build();
        } else if(Objects.isNull(produtoAtualizado)){
            return ResponseEntity.status(400).build();
        }

        Product productExists = productService.update(id, produtoAtualizado);

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
