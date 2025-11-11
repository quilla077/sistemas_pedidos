package reservas.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reservas.demo.models.Enums.EstadoGanado;
import reservas.demo.models.entitys.Ganado;
import reservas.demo.repository.GanadoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GanadoService {
    private final GanadoRepository ganadoRepository;

    public List<Ganado> findAll() {
        return ganadoRepository.findAll();
    }

    public Optional<Ganado> findById(Long id) {
        return ganadoRepository.findById(id);
    }

    public Ganado save(Ganado ganado) {
        return ganadoRepository.save(ganado);
    }

    public void deleteById(Long id) {
        ganadoRepository.deleteById(id);
    }

    public void actualizarEstado(Long id, EstadoGanado estado) {
        ganadoRepository.actualizarEstado(id, estado);
    }
    public List<Ganado> getGanadoBySocioId(Long idsocio) {
        return ganadoRepository.findByIdsocio(idsocio);
    }
    public List<Ganado> listarPorEstado(EstadoGanado estado) {
        return ganadoRepository.findByEstadog(estado);
    }
    public List<Ganado> listarPorSocioYEstado(Long idsocio, EstadoGanado estado) {
        return ganadoRepository.findByIdsocioAndEstadog(idsocio, estado);
    }
}
