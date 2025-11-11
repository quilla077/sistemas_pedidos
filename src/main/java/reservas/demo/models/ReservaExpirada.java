package reservas.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaExpirada {
    public LocalTime horar;
    public Long idreserva;
    public Long idoferta;
    public Long idcomprador;
    public Long idsocio;
}
