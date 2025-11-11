package reservas.demo.models.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reservas.demo.models.Enums.TipoOferta;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "oferta")
public class Oferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idoferta;
    //@Column(name = "fechao", nullable = false)
    private LocalDateTime fechao;
    //@Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private Double precio;
    //@Column(name = "calidad", length = 50)
    private String calidad;
    //@Column(name = "peso", precision = 10, scale = 2)
    private Double peso;
    //@Column(name = "procedencia", length = 100)
    private String procedencia;
    //@Column(name = "estado", length = 20)
    @Enumerated(EnumType.STRING)
    private TipoOferta estado;
    //@Column(name = "marca", length = 50)
    private String marca;
    //@Column(name = "codigoficha", nullable = false)
    private Long codigoficha;
    //@Column(name = "idsocio", nullable = false)
    private Long idsocio;
}
