package tech.code.codetech.model;

import jakarta.persistence.*;
import lombok.*;
import tech.code.codetech.api.controller.generico.StatusUnidade;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "endereco")
public class Unidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;
    private Integer num;

    @Column(name = "status_unidade")
    @Enumerated(EnumType.STRING)  // Transforma em string
    private StatusUnidade status = StatusUnidade.INOPERANTE;

    @Override
    public String toString() {
        return "Unidade { " +
                "id=" + id + ", " +
                "cep='" + cep + "', " +
                "logradouro='" + logradouro + "', " +
                "bairro='" + bairro + "', " +
                "cidade='" + cidade + "', " +
                "estado='" + estado + "', " +
                "complemento='" + complemento + "', " +
                "num=" + num +
                "status=" + status +
                " }";
    }
}
