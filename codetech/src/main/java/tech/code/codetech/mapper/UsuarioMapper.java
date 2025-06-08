package tech.code.codetech.mapper;

import tech.code.codetech.dto.usuarios.request.UsuariosRequestDto;
import tech.code.codetech.dto.usuarios.response.UsuariosResponseDto;
import tech.code.codetech.model.Usuario;

public class UsuarioMapper {

    public static Usuario toModel(UsuariosRequestDto dto){
        return Usuario.builder()
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .telefone(dto.getTelefone())
                .email(dto.getEmail())
                .dataNascimento(dto.getDataNascimento())
                .build();
    }

    public static UsuariosResponseDto toResponseDto(Usuario model){
        return UsuariosResponseDto.builder()
                .id(model.getId())
                .nome(model.getNome())
                .cpf(model.getCpf())
                .telefone(model.getTelefone())
                .email(model.getEmail())
                .dataNascimento(model.getDataNascimento())
                .build();
    }

}
