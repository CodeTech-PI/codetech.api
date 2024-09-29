package tech.code.codetech.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.model.ListaProduto;
import tech.code.codetech.repository.ListaProdutoRepository;
import tech.code.codetech.strategy.ListaProdutoInterface;
import java.util.List;

@Service
public class ListaProdutoService implements ListaProdutoInterface {

    @Autowired
    private ListaProdutoRepository listaProdutoRepository;

    @Transactional
    public ListaProduto save(ListaProduto listaProduto){
        return listaProdutoRepository.save(listaProduto);
    }

    public ListaProduto findById(Integer id){
        return listaProdutoRepository.findById(id).orElse(null);
    }

    public List<ListaProduto> findAll(){
        return listaProdutoRepository.findAll();
    }

    public ListaProduto update(Integer id, ListaProduto listaProduto){
        if(!listaProdutoRepository.existsById(id)){
            return null;
        }
        listaProduto.setId(id);
        return listaProdutoRepository.save(listaProduto);
    }

    public boolean delete(Integer id){
        if(!listaProdutoRepository.existsById(id)){
            return false;
        }
        listaProdutoRepository.deleteById(id);
        return true;
    }

}