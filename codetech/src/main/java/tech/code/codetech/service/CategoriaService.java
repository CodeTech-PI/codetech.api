package tech.code.codetech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.model.Categoria;
import tech.code.codetech.repository.CategoriaRepository;
import tech.code.codetech.strategy.CategoriaInterface;

import java.util.List;

@Service
public class CategoriaService implements CategoriaInterface {

    @Autowired
    private CategoriaRepository categoriaRepository;


    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria findById(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public Categoria update(Integer id, Categoria categoria) {
        if(!categoriaRepository.existsById(id)){
            return null;
        }
        categoria.setId(id);
        return categoriaRepository.save(categoria);
    }

    @Override
    public boolean delete(Integer id) {
        if(!categoriaRepository.existsById(id)){
            return false;
        }
        categoriaRepository.deleteById(id);
        return true;
    }
}