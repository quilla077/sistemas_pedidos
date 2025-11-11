package reservas.demo.models.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idreserva;

    @Column(name = "fechar", nullable = false)
    private LocalDate fechares;

    @Column(name = "horar")
    private LocalTime horares;

    //@Enumerated(EnumType.STRING)
    //private TipoReserva estadores;
    @Column(name = "estador", nullable = false)
    private String estadores;

    @Column(name = "idoferta", nullable = false)
    private Long idoferta;

    @Column(name = "idcomprador", nullable = false)
    private Long idcomprador;
}
