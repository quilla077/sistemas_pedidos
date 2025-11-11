package reservas.demo.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reservas.demo.models.entitys.Fidelidad;
import reservas.demo.repository.FidelidadRepository;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class FidelidadService {
    private final FidelidadRepository fidelidadRepository;
    // Crear nueva fidelidad
    public Fidelidad createFidelidad(Fidelidad fidelidad) {
        if (fidelidad.getFechaRes() == null) {
            fidelidad.setFechaRes(LocalDate.now());
        }
        return fidelidadRepository.save(fidelidad);
    }


}
