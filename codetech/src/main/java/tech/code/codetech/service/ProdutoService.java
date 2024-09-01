package tech.code.codetech.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.model.Produto;
import tech.code.codetech.repository.ProdutoRepository;
import tech.code.codetech.strategy.ProdutoInterface;

import java.util.List;

@Service
public class ProdutoService implements ProdutoInterface {

    @Autowired
    private ProdutoRepository productRepository;

    @Transactional // Garante integridade dos dados (rollback)
    public Produto save(Produto produto){
        return productRepository.save(produto);
    }

    public List<Produto> findAll(){ // Encontra tudo
        return productRepository.findAll();
    }

    public Produto findById(Integer id){
        return productRepository.findById(id).orElse(null);
    }

    public Produto update(Integer id, Produto product){
        if(!productRepository.existsById(id)){
           return null;
        }
        product.setId(id);
        return productRepository.save(product);
    }

    public boolean delete(Integer id){
        if(!productRepository.existsById(id)){
           return false;
        }
            productRepository.deleteById(id);
            return true;
    }
}
