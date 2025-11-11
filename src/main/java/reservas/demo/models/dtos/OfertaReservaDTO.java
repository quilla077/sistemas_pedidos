package reservas.demo.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reservas.demo.models.Enums.TipoOferta;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfertaReservaDTO {
    private Long idreserva;
    private LocalDate fechares;
    private LocalTime horares;
    private String estadores;
    private Long idcomprador;
    private Double precio;
    private String calidad;
    private Double peso;
    private String procedencia;
    private String marca;
    private Long idsocio;
    private TipoOferta estadoOferta;
}
