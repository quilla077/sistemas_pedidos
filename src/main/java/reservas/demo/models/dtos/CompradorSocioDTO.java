package reservas.demo.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompradorSocioDTO {
    private Long idcomprador;
    private String nombre;
    private String carnet;
}
