package tech.code.codetech.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tech.code.codetech.dto.lombardi.request.LombardiRequestDto;
import tech.code.codetech.mapper.UsuarioLombardiMapper;
import tech.code.codetech.model.UsuarioLombardi;
import tech.code.codetech.repository.UsuarioLombardiRepository;
import tech.code.codetech.service.autenticacao.configuration.security.jwt.GerenciadorTokenJwt;
import tech.code.codetech.service.autenticacao.dto.UsuarioLombardiLoginDto;
import tech.code.codetech.service.autenticacao.dto.UsuarioLombardiTokenDto;
import tech.code.codetech.strategy.UsuarioLombardiInterface;

@Service
public class UsuarioLombardiService implements UsuarioLombardiInterface {

    @Autowired
    private UsuarioLombardiRepository usuarioLombardiRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UsuarioLombardiTokenDto autenticar(UsuarioLombardiLoginDto usuarioLombardiLoginDto) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuarioLombardiLoginDto.getEmail(),
                usuarioLombardiLoginDto.getSenha()
        );

        try {
            final Authentication authentication = this.authenticationManager.authenticate(credentials);

            UsuarioLombardi usuarioLombardiAutenticado =
                    usuarioLombardiRepository.findByEmail(usuarioLombardiLoginDto.getEmail())
                            .orElseThrow(
                                    () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            final String token = gerenciadorTokenJwt.generateToken(authentication);
            return UsuarioLombardiMapper.of(usuarioLombardiAutenticado, token);
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(401, "Usuário ou senha inválidos", e);
        }
    }


    @Transactional
    public UsuarioLombardi save(UsuarioLombardi usuarioLombardi) {
        return usuarioLombardiRepository.save(usuarioLombardi);
    }

    public UsuarioLombardi update(Integer id, UsuarioLombardi usuarioLombardi) {
        if (!usuarioLombardiRepository.existsById(id)) {
            return null;
        }
        usuarioLombardi.setId(id);

        return usuarioLombardiRepository.save(usuarioLombardi);
    }

    @Transactional
    public boolean delete(Integer id) {
        if (!usuarioLombardiRepository.existsById(id)) {
            return false;
        }
        usuarioLombardiRepository.deleteById(id);
        return true;
    }

    public UsuarioLombardi findById(Integer id) {
        return usuarioLombardiRepository.findById(id).orElse(null);
    }

    public UsuarioLombardi findByEmailAndSenha(String email, String password) {
        return usuarioLombardiRepository.findByEmailAndSenha(email, password);
    }

    public void criar(LombardiRequestDto usuarioLombardiDto) {
        final UsuarioLombardi novoUsuarioLombardi = UsuarioLombardiMapper.of(usuarioLombardiDto);

        String senhaCriptografada = passwordEncoder.encode(usuarioLombardiDto.getSenha());
        usuarioLombardiDto.setSenha(senhaCriptografada);

        this.usuarioLombardiRepository.save(novoUsuarioLombardi);
    }
}