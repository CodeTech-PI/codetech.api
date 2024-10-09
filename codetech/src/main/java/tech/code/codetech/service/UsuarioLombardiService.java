package tech.code.codetech.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.model.UsuarioLombardi;
import tech.code.codetech.repository.UsuarioLombardiRepository;
import tech.code.codetech.strategy.UsuarioLombardiInterface;

@Service
public class UsuarioLombardiService implements UsuarioLombardiInterface {

    @Autowired
    private UsuarioLombardiRepository usuarioLombardiRepository;

    @Transactional
    public UsuarioLombardi save(UsuarioLombardi usuarioLombardi){
       return usuarioLombardiRepository.save(usuarioLombardi);
    }

    public UsuarioLombardi update(Integer id, UsuarioLombardi usuarioLombardi){
        if(!usuarioLombardiRepository.existsById(id)){
            return null;
        }
         usuarioLombardi.setId(id);
        return usuarioLombardiRepository.save(usuarioLombardi);
    }

    @Transactional
    public boolean delete(Integer id){
        if(!usuarioLombardiRepository.existsById(id)){
            return false;
        }
        usuarioLombardiRepository.deleteById(id);
        return true;
    }

    public UsuarioLombardi findById(Integer id){
        return usuarioLombardiRepository.findById(id).orElse(null);
    }

    public UsuarioLombardi findByEmailAndSenha(String email, String password){
        return usuarioLombardiRepository.findByEmailAndSenha(email, password);
    }


}
