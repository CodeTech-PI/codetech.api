package tech.code.codetech.service;

import jakarta.transaction.Transactional;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import tech.code.codetech.exception.NaoEncontradoException;
import tech.code.codetech.model.ListaProduto;
import tech.code.codetech.repository.ListaProdutoRepository;
import tech.code.codetech.strategy.ListaProdutoInterface;
import java.util.List;

@Service
public class ListaProdutoService implements ListaProdutoInterface {

    @Autowired
    private ListaProdutoRepository listaProdutoRepository;

    @Transactional
    public List<ListaProduto> saveAll(List<ListaProduto> listaProduto){
        return listaProdutoRepository.saveAll(listaProduto);
    }

    public ListaProduto findById(Integer id){
        return listaProdutoRepository.findById(id).orElse(null);
    }

    public List<ListaProduto> findAll(){
        return listaProdutoRepository.findAll();
    }

    public List<ListaProduto> update(List<ListaProduto> listaProduto){
        for (ListaProduto produto : listaProduto) {
            if(!existePorId(produto.getId())){
                throw new NaoEncontradoException("Lista Produto");
            }
        }
        return saveAll(listaProduto);
    }

    private boolean existePorId(Integer id){
        return listaProdutoRepository.existsById(id);
    }

    public boolean delete(Integer id){
        if(!listaProdutoRepository.existsById(id)){
            return false;
        }
        listaProdutoRepository.deleteById(id);
        return true;
    }

    @Override
    public List<ListaProduto> buscarListaProdutosPeloAgendamento(Integer idAgendamento) {
        return listaProdutoRepository.findByAgendamentoId(idAgendamento);
    }
}