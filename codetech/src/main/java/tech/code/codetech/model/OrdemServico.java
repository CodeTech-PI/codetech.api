package tech.code.codetech.model;


import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Enabled
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double valorAgendamento;

    @OneToOne
    private Agendamento agendamento;

    @ManyToOne
    private Produto produto;

}