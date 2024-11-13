package tech.code.codetech.model;


import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal valorTatuagem;

    @OneToOne
    private Agendamento agendamento;

}