package reservas.demo.Seguridad.Autenticacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reservas.demo.models.Enums.CodeAuthResponse;
import reservas.demo.models.Enums.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    String token;
    CodeAuthResponse code;
    String mensaje;
    private Long idper;
    private String marca;
    private Role tipo;  
}
