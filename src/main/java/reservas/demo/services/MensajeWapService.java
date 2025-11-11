package reservas.demo.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reservas.demo.models.entitys.Comprador;
import reservas.demo.models.entitys.MensajeWap;
import reservas.demo.models.entitys.Socio;
import reservas.demo.repository.MensajeWapRepository;
import reservas.demo.repository.PersonaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MensajeWapService {
    private final MensajeWapRepository mensajeWapRepository;
    private final PersonaRepository personaRepository;

    // Actualizar estado por ID
    public void marcarComoEnviado(Long id) {
        mensajeWapRepository.actualizarEstado(id, "ENVIADO");
    }
    // CREATE
    public MensajeWap crearMensaje(MensajeWap mensajeWap) {
        return mensajeWapRepository.save(mensajeWap);
    }
    public void crearNotifComprador(Long idsocio, Long idcomp) {
        Socio s = personaRepository.findSocioById(idsocio);
        Comprador c = personaRepository.findCompradorById(idcomp);
        MensajeWap mensajeWap = new MensajeWap();
        mensajeWap.setFechamen(LocalDate.now());
        mensajeWap.setHoramen(LocalTime.now());
        mensajeWap.setEstadom("ESPERANDO");
        mensajeWap.setTiponotif("notifComp");
        mensajeWap.setIdemisor(idsocio);
        mensajeWap.setCelular(c.getNumcel());
        mensajeWap.setIdreceptor(idcomp);
        mensajeWap.setNomsocio(s.getNombre()+" "+s.getApellido());
        mensajeWap.setNomcomp(c.getNombre());
        mensajeWapRepository.save(mensajeWap);
    }
    public void crearNotifSocio(Long idsocio, Long idcomp,Long idofer) {
        Socio s = personaRepository.findSocioById(idsocio);
        Comprador c = personaRepository.findCompradorById(idcomp);
        MensajeWap mensajeWap = new MensajeWap();
        mensajeWap.setFechamen(LocalDate.now());
        mensajeWap.setHoramen(LocalTime.now());
        mensajeWap.setEstadom("ESPERANDO");
        mensajeWap.setTiponotif("notifSocio");
        mensajeWap.setIdemisor(idcomp);
        mensajeWap.setCelular(s.getNumcel());
        mensajeWap.setIdreceptor(idsocio);
        mensajeWap.setNomsocio(s.getNombre()+" su ganado con ID: "+idofer);
        mensajeWap.setNomcomp(c.getNombre()+" "+c.getApellido()+" con cel: "+c.getNumcel());
        mensajeWapRepository.save(mensajeWap);
    }

    // Metodo para obtener mensajes con estado ESPERANDO
    public List<MensajeWap> obtenerMensajesEnEspera() {
        return mensajeWapRepository.findByEstadom("ESPERANDO");
    }

    // READ - Obtener por ID
    public MensajeWap obtenerMensajePorId(Long id) {
        return mensajeWapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado con ID: " + id));
    }

    // READ - Obtener todos
    public List<MensajeWap> obtenerTodosMensajes() {
        return mensajeWapRepository.findAll();
    }

}
