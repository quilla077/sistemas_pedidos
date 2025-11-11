package reservas.demo.models.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mensajewap")
public class MensajeWap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idmensaje;

    @Column(nullable = false)
    private String estadom;
    private String tiponotif;

    private LocalDate fechamen;
    private LocalTime horamen;

    private String nomsocio;
    private String nomcomp;
    private Long celular;

    @Column(nullable = false)
    private Long idemisor;
    @Column(nullable = false)
    private Long idreceptor;

}
