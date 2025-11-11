package reservas.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reservas.demo.models.Enums.EstadoObs;
import reservas.demo.models.Enums.TipoObsRes;
import reservas.demo.models.entitys.Obsreserva;
import reservas.demo.repository.ObsreservaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ObsreservaService {
    private final ObsreservaRepository obsreservaRepository;

    // CRUD methods
    public List<Obsreserva> getAllObsreservas() {
        return obsreservaRepository.findAll();
    }

    public Optional<Obsreserva> getObsreservaById(Long id) {
        return obsreservaRepository.findById(id);
    }

    public Obsreserva createObsreserva(Obsreserva obsreserva) {
        return obsreservaRepository.save(obsreserva);
    }
    public void createExpirReserva(Long idsocio, Long idcomp, Long idres) {
        Obsreserva reserva=new Obsreserva();
        reserva.setDescripcion("Como no pago la reserva generará una mala puntuacion, tenga cuidado la proxima vez");
        reserva.setEstado(EstadoObs.ENVIADO);
        reserva.setTipoobs(TipoObsRes.QUEJA);
        reserva.setFechaobs(LocalDate.now());
        reserva.setHoraobs(LocalTime.now());
        reserva.setIdemisor(idsocio);
        reserva.setIdreceptor(idcomp);
        reserva.setIdreserva(idres);
        obsreservaRepository.save(reserva);
    }

    public Obsreserva updateObsreserva(Long id, Obsreserva obsreservaDetails) {
        Optional<Obsreserva> optionalObsreserva = obsreservaRepository.findById(id);

        if (optionalObsreserva.isPresent()) {
            Obsreserva obsreserva = optionalObsreserva.get();
            obsreserva.setDescripcion(obsreservaDetails.getDescripcion());
            obsreserva.setTipoobs(obsreservaDetails.getTipoobs());
            obsreserva.setEstado(obsreservaDetails.getEstado());
            obsreserva.setHoraobs(obsreservaDetails.getHoraobs());
            obsreserva.setFechaobs(obsreservaDetails.getFechaobs());
            obsreserva.setIdreserva(obsreservaDetails.getIdreserva());
            obsreserva.setIdemisor(obsreservaDetails.getIdemisor());
            obsreserva.setIdreceptor(obsreservaDetails.getIdreceptor());

            return obsreservaRepository.save(obsreserva);
        }
        return null;
    }

    public boolean deleteObsreserva(Long id) {
        if (obsreservaRepository.existsById(id)) {
            obsreservaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Custom search methods
    public List<Obsreserva> getByTipoobs(TipoObsRes tipoobs) {
        return obsreservaRepository.findByTipoobs(tipoobs);
    }
    public List<Obsreserva> getByTipoobsId(TipoObsRes tipoobs,Long id) {
        return obsreservaRepository.findByTipoobsId(tipoobs,id);
    }

    public List<Obsreserva> getByFechaobs(LocalDate fechaobs) {
        return obsreservaRepository.findByFechaobs(fechaobs);
    }

    public List<Obsreserva> getByFechaobsBetween(LocalDate fechaInicio, LocalDate fechaFin) {
        return obsreservaRepository.findByFechaobsBetween(fechaInicio, fechaFin);
    }

    public List<Obsreserva> getByIdemisor(Long idemisor) {
        return obsreservaRepository.findByIdemisor(idemisor);
    }

    public List<Obsreserva> getByIdreceptor(Long idreceptor) {
        return obsreservaRepository.findByIdreceptor(idreceptor);
    }

    public List<Obsreserva> getByIdreserva(Long idreserva) {
        return obsreservaRepository.findByIdreserva(idreserva);
    }

    public List<Obsreserva> getByEstado(EstadoObs estado) {
        return obsreservaRepository.findByEstado(estado);
    }

    public List<Obsreserva> getByTipoAndEstado(TipoObsRes tipo, EstadoObs estado) {
        return obsreservaRepository.findByTipoAndEstado(tipo, estado);
    }

    public long countObsreservas() {
        return obsreservaRepository.count();
    }

    public boolean existsById(Long id) {
        return obsreservaRepository.existsById(id);
    }
}
