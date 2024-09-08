package tech.code.codetech.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Produto implements Comparable<Produto> {
    //Entity do Banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantidade;
    private String nome;
    private String descricao;
    private String categoria;
    private String unidadeDeMedida;
    private LocalDate dataDeValidade;
    private Double preco;

    @Override
    public int compareTo(Produto o) {
        return this.quantidade.compareTo(o.getQuantidade());
    }
}

