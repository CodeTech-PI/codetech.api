package tech.code.codetech.mapper;

import tech.code.codetech.dto.lombardi.request.LombardiRequestDto;
import tech.code.codetech.dto.lombardi.response.LombardiResponseDto;
import tech.code.codetech.model.UsuarioLombardi;
import tech.code.codetech.service.autenticacao.dto.UsuarioLombardiTokenDto;


public class UsuarioLombardiMapper {

    public static UsuarioLombardi toModel(LombardiRequestDto dto){
        return UsuarioLombardi.builder()
                .email(dto.getEmail())
                .senha(dto.getSenha())
                .nome(dto.getNome())
                .build();
    }

    public static LombardiResponseDto toResponseDto (UsuarioLombardi model){
        return LombardiResponseDto.builder()
                .id(model.getId())
                .email(model.getEmail())
                .senha(model.getSenha())
                .nome(model.getNome())
                .build();
    }

    public static UsuarioLombardi of(LombardiRequestDto lombardiCriacaoDto){
        UsuarioLombardi usuarioLombardi = new UsuarioLombardi();

        usuarioLombardi.setNome(lombardiCriacaoDto.getNome());
        usuarioLombardi.setEmail(lombardiCriacaoDto.getEmail());
        usuarioLombardi.setSenha(lombardiCriacaoDto.getSenha());

        return usuarioLombardi;
    }

    public static UsuarioLombardiTokenDto of(UsuarioLombardi usuarioLombardi, String token) {

        UsuarioLombardiTokenDto usuarioLombardiTokenDto = new UsuarioLombardiTokenDto();

        usuarioLombardiTokenDto.setUserId(usuarioLombardi.getId());
        usuarioLombardiTokenDto.setEmail(usuarioLombardi.getEmail());
        usuarioLombardiTokenDto.setNome(usuarioLombardi.getNome());
        usuarioLombardiTokenDto.setToken(token);

        return usuarioLombardiTokenDto;
    }
}