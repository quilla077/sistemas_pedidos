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
@DiscriminatorValue("COMPRADOR")
public class Comprador extends Persona {

    @Column(name = "nivel_fidelidad")
    private Integer fidelidad; // 1 a 5, siendo 5 el máximo
}
