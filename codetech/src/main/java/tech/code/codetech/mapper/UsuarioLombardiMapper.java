package tech.code.codetech.mapper;

import tech.code.codetech.dto.lombardi.request.LombardiRequestDto;
import tech.code.codetech.dto.lombardi.response.LombardiResponseDto;
import tech.code.codetech.model.UsuarioLombardi;

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
}