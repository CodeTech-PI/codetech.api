package tech.code.codetech.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ListaProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantidadeProdutos;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Agendamento agendamento;
}
