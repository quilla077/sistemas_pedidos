package reservas.demo.models.utils;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppProperties {
    private int limitecompfidelidad;
    private int nummesesfidelidad;
    private int timeexpiracionoferta;
    private int timerevision;
}
