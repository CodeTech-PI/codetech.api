package tech.code.codetech.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.code.codetech.model.Produto;
import tech.code.codetech.model.Usuario;
import tech.code.codetech.repository.UsuarioRepository;
import tech.code.codetech.strategy.UsuarioInterface;

import java.util.List;

@Service
public class UsuarioService implements UsuarioInterface {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Integer id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario update(Integer id, Usuario usuario){
        if(!usuarioRepository.existsById(id)){
            return null;
        }
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    public boolean delete(Integer id){
        if(!usuarioRepository.existsById(id)){
            return false;
        }
        usuarioRepository.deleteById(id);
        return true;
    }
}