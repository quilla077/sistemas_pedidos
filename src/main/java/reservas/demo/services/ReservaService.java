package reservas.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reservas.demo.models.Enums.TipoReserva;
import reservas.demo.models.entitys.Oferta;
import reservas.demo.models.entitys.Reserva;
import reservas.demo.repository.MensajeWapRepository;
import reservas.demo.repository.OfertaRepository;
import reservas.demo.repository.ReservaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final OfertaRepository ofertaRepository;
    private final MensajeWapService mensajeWapService;

    // CRUD básico
    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> getReservaById(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva createReserva(Reserva reserva) {
        Oferta ofer = ofertaRepository.getReferenceById(reserva.getIdoferta());
        mensajeWapService.crearNotifSocio(ofer.getIdsocio(),reserva.getIdcomprador(),ofer.getIdoferta());
        return reservaRepository.save(reserva);
    }

    public Reserva updateReserva(Long id, Reserva reservaDetails) {
        Optional<Reserva> optionalReserva = reservaRepository.findById(id);
        if (optionalReserva.isPresent()) {
            Reserva reserva = optionalReserva.get();
            reserva.setFechares(reservaDetails.getFechares());
            reserva.setHorares(reservaDetails.getHorares());
            reserva.setEstadores(reservaDetails.getEstadores());
            reserva.setIdoferta(reservaDetails.getIdoferta());
            reserva.setIdcomprador(reservaDetails.getIdcomprador());
            return reservaRepository.save(reserva);
        }
        return null;
    }
    public String updateReservaQuery(Long id,int num){
        String k="Esta bien funciona";
        if (reservaRepository.existsById(id)){
            System.out.println("id = " + id + ", num = " + num);
        }else{
            k="no existe el id de reserva";
            System.out.println(k);
        }
        switch (num){
            case 0: reservaRepository.actualizaEstado(
                    String.valueOf(TipoReserva.PAGADA),id);break;
            case 1: reservaRepository.actualizaEstado(
                    String.valueOf(TipoReserva.EXPIRADA),id);break;
            case 2: reservaRepository.actualizaEstado(
                    String.valueOf(TipoReserva.ACTIVA),id);break;
            case 3: reservaRepository.actualizaEstado(
                    String.valueOf(TipoReserva.DECLINADA),id);break;
            default: k="Existen errores";
        }
        return k;
    }

    public boolean deleteReserva(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Consultas personalizadas
    public List<Reserva> getReservasByComprador(Long idComprador) {
        return reservaRepository.findByCompradorNative(idComprador);
    }

    public List<Reserva> getReservasByFecha(LocalDate fecha) {
        return reservaRepository.findByFechaNative(fecha);
    }

    public List<Reserva> getReservasByEstado(String estado) {
        return reservaRepository.findByEstadoNative(estado);
    }

    public List<Reserva> getReservasByMarca(String marca) {
        return reservaRepository.findByMarcaNative(marca);
    }

    public List<Reserva> getReservasByCompradorAndEstado(Long idComprador, String estado) {
        return reservaRepository.findByCompradorAndEstadoNative(idComprador, estado);
    }

    public List<Reserva> getReservasByRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return reservaRepository.findByRangoFechasNative(fechaInicio, fechaFin);
    }
}
