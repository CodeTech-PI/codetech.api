package tech.code.codetech.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.model.Agendamento;
import tech.code.codetech.repository.AgendamentoRepository;

import java.util.List;

@Service
public class AgendamentoService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Transactional
    public Agendamento save(Agendamento agendamento){
        return agendamentoRepository.save(agendamento);
    }

    public List<Agendamento> findAll(){
        return agendamentoRepository.findAll();
    }

    public Agendamento findById(Integer id){
        return agendamentoRepository.findById(id).orElse(null);
    }

    public Agendamento update(Integer id, Agendamento agendamento){
        if(!agendamentoRepository.existsById(id)){
            return null;
        }
        agendamento.setId(id);
        return agendamentoRepository.save(agendamento);
    }

    public boolean delete(Integer id){
        if(!agendamentoRepository.existsById(id)){
            return false;
        }
        agendamentoRepository.deleteById(id);
        return true;
    }
}
