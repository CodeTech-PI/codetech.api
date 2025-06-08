package tech.code.codetech.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate dt;
    private LocalTime horario;
    private boolean cancelado = false;

    @JoinColumn(name = "cliente_id")
    @ManyToOne
    private Usuario usuario;
}
