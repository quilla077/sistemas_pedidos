package reservas.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ppersona {
    @JsonProperty("carnet")
    private Long carnet;

    @JsonProperty("nombres")
    private String nombres;

    @JsonProperty("apellidos")
    private String apellidos;

    @JsonProperty("fecha_nac")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String fechaNacimiento;

    @JsonProperty("sexo")
    private String sexo;

    @JsonProperty("id_direccion")
    private Long idDireccion;

    @JsonProperty("ncelular")
    private Integer numeroCelular;
}
