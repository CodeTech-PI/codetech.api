package tech.code.codetech.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.model.Faturamento;
import tech.code.codetech.repository.FaturamentoRepository;
import tech.code.codetech.strategy.FaturamentoInterface;

import java.util.List;

@Service
public class FaturamentoService implements FaturamentoInterface {

        @Autowired
        private FaturamentoRepository faturamentoRepository;

        @Transactional
        public Faturamento save(Faturamento faturamento){
            return faturamentoRepository.save(faturamento);
        }

        public Faturamento findById(Integer id){
            return faturamentoRepository.findById(id).orElse(null);
        }

        public List<Faturamento> findAll(){
            return faturamentoRepository.findAll();
        }

        public Faturamento update(Integer id, Faturamento faturamento){
            if(!faturamentoRepository.existsById(id)){
                return null;
            }
            faturamento.setId(id);
            return faturamentoRepository.save(faturamento);
        }

        public boolean delete(Integer id){
            if(!faturamentoRepository.existsById(id)){
                return false;
            }
            faturamentoRepository.deleteById(id);
            return true;
        }
}
