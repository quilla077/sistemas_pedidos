package reservas.demo.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompradorFielDto {
    private Long idcompfiel;
    private Long numveces;
    private Long numcel;
}
