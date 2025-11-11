package reservas.demo.models.informaciones;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoSocioReservas {
    private Double ventames;
    private Long numreservas;
}
