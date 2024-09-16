package tech.code.codetech.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.model.Unidade;
import tech.code.codetech.repository.UnidadeRepository;
import tech.code.codetech.strategy.UnidadeInterface;
import java.util.List;

@Service
public class UnidadeService implements UnidadeInterface {

   @Autowired
    private UnidadeRepository unidadeRepository;

   @Transactional
   public Unidade save(Unidade unidade){
       return unidadeRepository.save(unidade);
   }

    public List<Unidade> findAll() {
        return unidadeRepository.findAll();
    }

    public Unidade findById(Integer id){
         return unidadeRepository.findById(id).orElse(null);
    }

    public Unidade update(Integer id, Unidade unidade){
        if(!unidadeRepository.existsById(id)){
           return null;
        }
        unidade.setId(id);
        return unidadeRepository.save(unidade);
    }

    public boolean delete(Integer id){
        if(!unidadeRepository.existsById(id)){
           return false;
        }
        unidadeRepository.deleteById(id);
        return true;
    }
}