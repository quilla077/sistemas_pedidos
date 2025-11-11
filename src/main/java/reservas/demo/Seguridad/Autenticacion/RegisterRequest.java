package reservas.demo.Seguridad.Autenticacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    Long idper;
    String username;
    String password;
    String email;
    String estado;
    String role;
}
