package reservas.demo.models.dtos;

import lombok.Builder;
import lombok.Data;
import reservas.demo.models.entitys.Admin;
import reservas.demo.models.entitys.Usuario;

@Builder
@Data
public class Admindto {
    private Admin admin;
    private Usuario usu;
}
