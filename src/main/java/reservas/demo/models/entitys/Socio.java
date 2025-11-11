package reservas.demo.models.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("SOCIO")
public class Socio extends Persona {

    @Column(name = "antiguedad_anios")
    private Integer antiguedad;

    @Column(length = 50)
    private String marca;
}
