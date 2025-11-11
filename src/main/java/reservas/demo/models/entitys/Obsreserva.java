package reservas.demo.models.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reservas.demo.models.Enums.EstadoObs;
import reservas.demo.models.Enums.TipoObsRes;

import java.time.LocalDate;
import java.time.LocalTime;


@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "obsreserva")
public class Obsreserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idobsreserva;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoObsRes tipoobs;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoObs estado = EstadoObs.ENVIADO;

    private LocalTime horaobs;

    private LocalDate fechaobs;

    @Column(nullable = false)
    private Long idreserva;

    @Column(nullable = false)
    private Long idemisor;

    @Column(nullable = false)
    private Long idreceptor;

}
