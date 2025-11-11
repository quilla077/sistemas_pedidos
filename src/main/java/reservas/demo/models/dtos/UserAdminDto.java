package reservas.demo.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reservas.demo.models.Enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAdminDto {
    private Long idper;
    private String username;
    private String estado;
    private String email;
    private String password;
    private Role role;

    private String nombre;
    private String apellido;
    private String carnet;
    private Long numcel;
    private Integer nivel;

}
