package reservas.demo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reservas.demo.models.entitys.Oferta;
import reservas.demo.models.Enums.TipoOferta;
import reservas.demo.models.dtos.CompradorFielDto;
import reservas.demo.models.utils.AppProperties;
import reservas.demo.repository.FidelidadRepository;
import reservas.demo.repository.OfertaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfertaService {
    //@Autowired
    private final OfertaRepository ofertaRepository;
    private final FidelidadRepository fidelidadRepository;
    private final MensajeWapService mensajeWapService;
    private final AppProperties appProperties;

    // CREATE
    public Oferta createOferta(Oferta oferta) throws InterruptedException {
        registrarMensajes(oferta.getIdsocio());
        return ofertaRepository.save(oferta);
    }
    public List<Oferta> obtenerOfertasConMasDeDosDiasActivas() {
        return ofertaRepository.findOfertasConMasDeDosDiasActivas();
    }
    public void actualizarOfertasConMasDeDosDiasActivas() {
        List<Oferta> ofer=ofertaRepository.findOfertasConMasDeDosDiasActivas();
        if (!ofer.isEmpty()){
            ofer.forEach(oferta ->{
                System.out.println("\nOfertaID: " + oferta.getIdoferta() +
                        " - Hora: " + oferta.getFechao()+
                        " SocioID:"+oferta.getIdsocio());
                ofertaRepository.actualizarEstado(TipoOferta.CADUCADO,oferta.getIdoferta());
            });
        }else{
            System.out.println("🕐 No existen ofertas caducadas...");
        }
    }

    //@Async
    public void registrarMensajes(Long id) throws InterruptedException {
        List<CompradorFielDto> c = fidelidadRepository.findTopCompradoresFieles(
                        id, appProperties.getNummesesfidelidad(),appProperties.getLimitecompfidelidad());
        //c.forEach(System.out::println);
        for (CompradorFielDto compradorFielDto : c) {
            System.out.println(compradorFielDto.toString());
            mensajeWapService.crearNotifComprador(id, compradorFielDto.getIdcompfiel());
        }
    }
    // READ ALL
    public List<Oferta> getAllOfertas() {
        return ofertaRepository.findAll();
    }

    // READ BY ID
    public Optional<Oferta> getOfertaById(Long id) {
        return ofertaRepository.findById(id);
    }

    // UPDATE
    public Oferta updateOferta(Long id, Oferta ofertaDetails) {
        Optional<Oferta> optionalOferta = ofertaRepository.findById(id);
        if (optionalOferta.isPresent()) {
            Oferta oferta = optionalOferta.get();
            oferta.setFechao(ofertaDetails.getFechao());
            oferta.setPrecio(ofertaDetails.getPrecio());
            oferta.setCalidad(ofertaDetails.getCalidad());
            oferta.setPeso(ofertaDetails.getPeso());
            oferta.setProcedencia(ofertaDetails.getProcedencia());
            oferta.setEstado(ofertaDetails.getEstado());
            oferta.setMarca(ofertaDetails.getMarca());
            oferta.setCodigoficha(ofertaDetails.getCodigoficha());
            oferta.setIdsocio(ofertaDetails.getIdsocio());
            return ofertaRepository.save(oferta);
        }
        return null;
    }


    public String updateOfertaQuery(Long id,int num){
        switch (num){
            case 0: ofertaRepository.actualizarEstado(TipoOferta.RESERVADO,id);
                break;
            case 1: ofertaRepository.actualizarEstado(TipoOferta.PAGADO,id);
                break;
            case 2: ofertaRepository.actualizarEstado(TipoOferta.ACTIVO,id);
                break;
            case 3: ofertaRepository.actualizarEstado(TipoOferta.DECOMISADO,id);
                break;
        }
        return "funciona";
    }
    // DELETE
    public boolean deleteOferta(Long id) {
        if (ofertaRepository.existsById(id)) {
            ofertaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Búsquedas específicas
    public List<Oferta> getOfertasByIdSocio(Long idSocio) {
        return ofertaRepository.findByIdsocio(idSocio);
    }

    public List<Oferta> getOfertasByCalidad(String calidad) {
        return ofertaRepository.findByCalidad(calidad);
    }

    public List<Oferta> getOfertasByFecha(LocalDate fecha) {
        LocalDateTime startOfDay = fecha.atStartOfDay();
        LocalDateTime endOfDay = fecha.atTime(LocalTime.MAX);
        return ofertaRepository.findByFechaoBetween(startOfDay, endOfDay);
    }

    public List<Oferta> getOfertasByRangoFechas(LocalDateTime startDate, LocalDateTime endDate) {
        return ofertaRepository.findByFechaoBetween(startDate, endDate);
    }

    public List<Oferta> getOfertasByIdSocioAndCalidad(Long idSocio, String calidad) {
        return ofertaRepository.findByIdsocioAndCalidad(idSocio, calidad);
    }

    public List<Oferta> getOfertasByPrecioGreaterThan(Double precioMinimo) {
        return ofertaRepository.findByPrecioGreaterThan(precioMinimo);
    }
    public List<Oferta> getOfertasActivasNative() {
        return ofertaRepository.findByEstadoNative("ACTIVO");
    }
    // Metodo usando JPQL (alternativa)
    public List<Oferta> getOfertasActivas() {
        return ofertaRepository.findByEstado(TipoOferta.ACTIVO);
    }
}
