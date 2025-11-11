package reservas.demo.models.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reservas.demo.models.Enums.EstadoGanado;


import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ganado")
public class Ganado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idganado;

    private LocalDate fechag;

    private Double peso;

    private String procedencia;

    @Enumerated(EnumType.STRING)
    private EstadoGanado estadog;

    private String marca;

    private Long idsocio;
}
