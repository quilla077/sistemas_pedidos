package reservas.demo.models.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fidelidad")
public class Fidelidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fidelidad", nullable = false)
    private Long idFidelidad;
    @Column(name = "id_socio", nullable = false)
    private Long idSocio;
    @Column(name = "id_comp", nullable = false)
    private Long idComp;
    @Column(name = "cel_comp")
    private Long celComp;
    @Column(name = "fecha_res")
    private LocalDate fechaRes;
}
