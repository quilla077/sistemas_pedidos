package reservas.demo.models.dtos;

import lombok.Builder;
import lombok.Data;
import reservas.demo.models.entitys.Portero;
import reservas.demo.models.entitys.Usuario;

@Builder
@Data
public class Porterodto {
    private Portero portero;
    private Usuario usu;
}
