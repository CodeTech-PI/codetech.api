package tech.code.codetech.dto.lombardi.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LombardiResponseDto {
    private Integer id;
    private String email;
    private String senha;
    private String nome;
}