package tech.code.codetech.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.model.OrdemServico;
import tech.code.codetech.repository.OrdemServicoRepository;
import tech.code.codetech.strategy.OrdemServicoInterface;

@Service
public class OrdemServicoService implements OrdemServicoInterface {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Transactional
    public OrdemServico save(OrdemServico ordemServico){
        return ordemServicoRepository.save(ordemServico);
    }

    public OrdemServico findById(Integer id){
        return ordemServicoRepository.findById(id).orElse(null);
    }

    public OrdemServico update(Integer id, OrdemServico ordemServico){
        if(!ordemServicoRepository.existsById(id)){
            return null;
        }
        ordemServico.setId(id);
        return ordemServicoRepository.save(ordemServico);
    }

    public boolean delete(Integer id){
        if(!ordemServicoRepository.existsById(id)){
            return false;
        }
        ordemServicoRepository.deleteById(id);
        return true;
    }

}