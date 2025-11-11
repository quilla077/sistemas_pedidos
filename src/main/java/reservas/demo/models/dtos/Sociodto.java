package reservas.demo.models.dtos;

import lombok.Builder;
import lombok.Data;
import reservas.demo.models.entitys.Socio;
import reservas.demo.models.entitys.Usuario;

@Builder
@Data
public class Sociodto {
    private Socio socio;
    private Usuario usu;
}
