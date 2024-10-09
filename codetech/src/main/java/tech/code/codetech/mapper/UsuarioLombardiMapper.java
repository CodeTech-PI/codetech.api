package tech.code.codetech.mapper;

import tech.code.codetech.dto.lombardi.request.LombardiRequestDto;
import tech.code.codetech.dto.lombardi.response.LombardiResponseDto;
import tech.code.codetech.model.UsuarioLombardi;
import tech.code.codetech.service.autenticacao.dto.UsuarioTokenDto;

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

    public static UsuarioLombardi of(LombardiRequestDto lombardiRequestDto){
        UsuarioLombardi usuarioLombardi = new UsuarioLombardi();

        usuarioLombardi.setNome(lombardiRequestDto.getNome());
        usuarioLombardi.setEmail(lombardiRequestDto.getEmail());
        usuarioLombardi.setSenha(lombardiRequestDto.getSenha());

        return usuarioLombardi;
    }

    public static UsuarioTokenDto of(UsuarioLombardi usuarioLombardi, String token){
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setUserId(usuarioLombardi.getId());
        usuarioTokenDto.setEmail(usuarioLombardi.getEmail());
        usuarioTokenDto.setNome(usuarioLombardi.getNome());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }
}