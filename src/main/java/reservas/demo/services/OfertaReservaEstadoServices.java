package reservas.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reservas.demo.models.Enums.TipoOferta;
import reservas.demo.models.Enums.TipoReserva;
import reservas.demo.repository.*;

@Service
@RequiredArgsConstructor
public class OfertaReservaEstadoServices {
    private final OfertaRepository ofertaRepository;
    private final ReservaRepository reservaRepository;
    private final OfertaReservaRepository ofertaReservaRepository;

    public void reservaExpirada(Long idofer, Long idRes){
        ofertaRepository.actualizarEstado(TipoOferta.ACTIVO,idofer);
        reservaRepository.actualizaEstado(String.valueOf(TipoReserva.EXPIRADA),idRes);
    }
    public void ofertaPagada(Long idofer){
        ofertaRepository.actualizarEstado(TipoOferta.PAGADO,idofer);
        reservaRepository.actualizaEstado(
                String.valueOf(TipoReserva.PAGADA),
                ofertaReservaRepository.findOfertaReservaActiva(idofer));
    }

}
