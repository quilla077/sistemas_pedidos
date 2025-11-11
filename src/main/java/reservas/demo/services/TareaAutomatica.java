package reservas.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reservas.demo.models.BodyMensaje;
import reservas.demo.models.Enums.TipoReserva;
import reservas.demo.models.entitys.MensajeWap;
import reservas.demo.models.ReservaExpirada;
import reservas.demo.models.entitys.Oferta;
import reservas.demo.models.utils.AppProperties;
import reservas.demo.models.utils.FechaUtils;
import reservas.demo.repository.OfertaReservaRepository;
import reservas.demo.repository.ReservaRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TareaAutomatica {
    private final OfertaReservaRepository ofertaReservaRepository;
    private final ReservaRepository reservaRepository;
    private final OfertaReservaEstadoServices ofertaReservaEstSer;
    private final OfertaService ofertaServices;
    private final ObsreservaService obsreservaService;
    private final MensajeWapService mensajeWapService;
    private final DatoServiceClient clientMensaje;
    private final AppProperties appProperties;
    // EJECUTA cada 5 segundos.
/*    @Scheduled(fixedRate = 30000)
//    public void tareaCada5Segundos() {
//        System.out.println("✅ Ejecutando tarea: " + new Date());
//    }
/
*   "0 0 * * * *"	        Cada hora
    "0 0 9 * * *"	        Cada día a las 9 AM
    "0 0 12 * * MON-FRI"	Dias laborables al mediodía
* */
    public void envioMensajesWap(){
        List<MensajeWap> men= mensajeWapService.obtenerMensajesEnEspera();
        if (!men.isEmpty()){
            men.forEach(m->{
                if(m.getTiponotif().equals("notifComp")){
                    System.out.println(m.toString());
                    BodyMensaje mensaje1 = BodyMensaje.builder()
                            .number("+591"+m.getCelular())
                            .text("Cómo está case "+m.getNomcomp()+"?, " +
                                    "le escribe "+m.getNomsocio()+" de FUTECRA sector Altiplano" +
                                    ", acabamos de publicar nuevas ofertas recien faeneado." +
                                    "Le invito a pasar por la plataforma para verlos.")
                            .delay(9450)
                            .build();
                    clientMensaje.enviarMensaje(mensaje1);  // Enviar a otro metodo
                }
                if(m.getTiponotif().equals("notifSocio")){
                    System.out.println(m.toString());
                    BodyMensaje mensaje1 = BodyMensaje.builder()
                            .number("+591"+m.getCelular())
                            .text("Estimado socio "+m.getNomsocio()+" fue reservado. " +
                                    "El S.Reservas le envía los siguientes datos: cliente " +
                                    m.getNomcomp()+
                                    ". Puede escribirle para coordinar su pago.")
                            .delay(6450)
                            .build();
                    clientMensaje.enviarMensaje(mensaje1);  // Enviar a otro metodo
                }
                mensajeWapService.marcarComoEnviado(m.getIdmensaje());
                try {
                        Thread.sleep(6345);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
            });
        }else{
            System.out.println("🕐 No existen mensajes en espera...");
        }
    }
    // EJECUTA cada minuto (60,000 milisegundos)
    //@Scheduled(cron = "0 */10 * * * *")
    @Scheduled(fixedRate = 300000)
    public void tareaCadaMinuto() {
        revisarOfertaPagada();
        revisarReservasExpiradas();
        envioMensajesWap();
        revisarProductosCaducados();
    }
    public void revisarReservasExpiradas(){
        List<ReservaExpirada> reservas = ofertaReservaRepository
                .findReservasExpiradas(FechaUtils.anioActual(), FechaUtils.mesActual());
        if (!reservas.isEmpty()){
            reservas.forEach(reserva ->{
                System.out.println("\nReservaID: " + reserva.idreserva +" - Hora: " + reserva.horar+
                        " OfertaID: "+reserva.idoferta+" SocioID:"+reserva.idsocio);
                if (pasoUnaHora(reserva.horar)){
                    System.out.println(reserva.idreserva+ " -> Este si borraremos");
                    ofertaReservaEstSer.reservaExpirada(reserva.idoferta,reserva.idreserva);
                    obsreservaService.createExpirReserva(reserva.idsocio,reserva.idcomprador,reserva.idreserva);
                }
            });
        }else{
            System.out.println("🕐 No existen reservas activas...");
        }
    }
    public boolean pasoUnaHora(LocalTime horaReserva) {
        LocalTime horaActual = LocalTime.now();
        long minutosDiferencia = Duration.between(horaReserva, horaActual).toMinutes();
        System.out.println(horaReserva+" - "+horaActual+" = "+minutosDiferencia);
        return minutosDiferencia > appProperties.getTimeexpiracionoferta();  // True si pasaron 60 minutos o más
    }
    public void revisarOfertaPagada(){
        List<ReservaExpirada> reservas = ofertaReservaRepository
                .findReservasPagadas(FechaUtils.anioActual(), FechaUtils.mesActual());
        if (!reservas.isEmpty()){
            reservas.forEach(reserva ->{
                System.out.println("\nReservaID: " + reserva.idreserva +" - Hora: " + reserva.horar+
                        " OfertaID: "+reserva.idoferta+" SocioID:"+reserva.idsocio);
                reservaRepository.actualizaEstado(
                        String.valueOf(TipoReserva.PAGADA),reserva.idreserva);
            });
        }else{
            System.out.println("🕐 No existen ofertas pagadas...");
        }
    }
    // Se ejecuta cada día a las 00:01 AM
    @Scheduled(cron = "0 1 0 * * ?")
    public void revisarProductosCaducados() {
        try {
            ofertaServices.actualizarOfertasConMasDeDosDiasActivas();
        } catch (Exception e) {
            System.out.println("Error en la revisión de productos caducados: {} "+ e.getMessage());
        }
        System.out.println("Finalizada revisión de productos caducados");
    }
}
