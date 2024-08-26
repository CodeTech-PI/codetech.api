package tech.code.codetech.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.model.Product;
import tech.code.codetech.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional // Garante integridade dos dados (rollback)
    public Product save(Product produto){
        return productRepository.save(produto);
    }

    public List<Product> findAll(){ // Encontra tudo
        return productRepository.findAll();
    }

    public Product findById(Integer id){
        return productRepository.findById(id).orElse(null);
    }

    public Product update(Integer id, Product product){
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
