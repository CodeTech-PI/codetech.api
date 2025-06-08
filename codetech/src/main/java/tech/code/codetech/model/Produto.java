package tech.code.codetech.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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
    private String unidadeMedida;
    private BigDecimal preco;

    @ManyToOne
    private Categoria categoria;

    @Override
    public int compareTo(Produto o) {
        return this.quantidade.compareTo(o.getQuantidade());
    }

    public Produto(Integer id, String nome, String descricao, Integer quantidade, Double preco, Integer categoriaId) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = BigDecimal.valueOf(preco);
        this.categoria = new Categoria();
        this.categoria.setId(categoriaId);
    }

}

