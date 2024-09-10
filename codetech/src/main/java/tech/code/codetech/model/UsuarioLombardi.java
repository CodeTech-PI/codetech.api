package tech.code.codetech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UsuarioLombardi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 75)
    @Email
    private String email;

    @NotBlank
    @Size(max = 25)
    private String senha;

    @NotBlank
    @Size(max = 100)
    private String nome;
}
