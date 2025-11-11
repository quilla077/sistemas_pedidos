package reservas.demo.models.dtos;

import lombok.Builder;
import lombok.Data;
import reservas.demo.models.entitys.Comprador;
import reservas.demo.models.entitys.Usuario;

@Builder
@Data
public class Compradordto {
    private Comprador comprador;
    private Usuario usu;
}
