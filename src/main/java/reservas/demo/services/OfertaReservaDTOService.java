package reservas.demo.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reservas.demo.models.Enums.TipoOferta;
import reservas.demo.models.dtos.CompradorSocioDTO;
import reservas.demo.models.dtos.OfertaReservaDTO;
import reservas.demo.models.informaciones.InfoCompReservas;
import reservas.demo.models.informaciones.InfoSocioOfertas;
import reservas.demo.models.informaciones.InfoSocioReservas;
import reservas.demo.repository.OfertaReservaRepository;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfertaReservaDTOService {
    private final OfertaReservaRepository ofertaReservaRepository;

    // CRUD básico
    public List<OfertaReservaDTO> findAll() {
        return ofertaReservaRepository.findAllDTOs();
    }

    public Optional<OfertaReservaDTO> findById(Long id) {
        return ofertaReservaRepository.findOfertReservById(id);
    }

    // Búsquedas personalizadas
    public List<OfertaReservaDTO> findByIdsocio(Long idsocio) {
        return ofertaReservaRepository.findByIdsocio(idsocio);
    }

    public List<OfertaReservaDTO> findByIdcomprador(Long idcomprador) {
        return ofertaReservaRepository.findByIdcomp(idcomprador);
    }
    public InfoSocioOfertas getResumenSocioOferPagado(Long idsocio, int anio, int mes) {
        InfoSocioOfertas resumen = ofertaReservaRepository.findResumenSocioOfertas(
                TipoOferta.PAGADO,idsocio, anio, mes);
        return resumen.getOfertames() != null ?resumen : new InfoSocioOfertas(0.0, 0L);
    }
    public InfoSocioReservas getResumenSocioRes(Long idsocio, int anio, int mes) {
        //return ofertaReservaRepository.findResumenSocioReservas(idsocio, anio, mes);
        InfoSocioReservas resumen = ofertaReservaRepository.findResumenSocioReservas(
                idsocio, anio, mes);
        return resumen.getVentames() != null ?resumen : new InfoSocioReservas(0.0, 0L);
    }
    public InfoSocioOfertas getResumenSocioOfer(Long idsocio, int anio, int mes) {
        InfoSocioOfertas resumen = ofertaReservaRepository.findResumenSocioOfertas(
                TipoOferta.ACTIVO,idsocio, anio, mes);
        return resumen.getOfertames() != null ?resumen : new InfoSocioOfertas(0.0, 0L);
    }
    public InfoCompReservas getResumenPagosComprador(Long idcomprador, int anio, int mes) {
        InfoCompReservas resumen = ofertaReservaRepository.findResumenPagosComprador(idcomprador, anio, mes);
        return resumen.getComprames() != null ?resumen : new InfoCompReservas(0.0, 0L);
    }

    public List<OfertaReservaDTO> findByMarca(String marca) {
        return ofertaReservaRepository.findByMarca(marca);
    }

    public List<OfertaReservaDTO> getOferReserByDateRange(String fechaInicio, String fechaFin) {
        try {
//            LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
//            LocalDate fechaFin = LocalDate.parse(fechaFinStr);
            System.out.println("SERVICIO: fechaInicio = " + fechaInicio + ", fechaFin = " + fechaFin);
            return ofertaReservaRepository.findAllDTOsByDateRange(fechaInicio, fechaFin);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use el formato yyyy-MM-dd");
        }
    }
    public List<OfertaReservaDTO> obtenerReservasPorQuincena(Integer tipobusqueda,Long idbusqueda,
                                                             Integer anio, Integer mes, Integer quincena) {
        if (idbusqueda == null || anio == null || mes == null || quincena == null) {
            throw new IllegalArgumentException("Todos los parámetros son obligatorios");
        }
        if (mes < 0 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        }
        if (quincena < 1 || quincena > 3) {
            throw new IllegalArgumentException("La quincena debe ser 1 o 2");
        }
        if (tipobusqueda < 1 || tipobusqueda > 2) {
            throw new IllegalArgumentException("El tipo de búsqueda debe ser 1 (comprador) o 2 (socio)");
        }
        return ofertaReservaRepository.findOfertaReservaSocioFecha(tipobusqueda,idbusqueda, anio, mes, quincena);
    }
    public List<OfertaReservaDTO> obtenerReservasFiltradaComprador(Long idcomp,Long idsocio,
                                                             Integer anio, Integer mes, Integer quincena) {
        if (idsocio == null || anio == null || mes == null || quincena == null) {
            throw new IllegalArgumentException("Todos los parámetros son obligatorios");
        }
        if (mes < 0 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12");
        }
        if (quincena < 1 || quincena > 3) {
            throw new IllegalArgumentException("La quincena debe ser 1 o 2");
        }
        return ofertaReservaRepository.findOfertaReservaSocioComp(idcomp,idsocio, anio, mes, quincena);
    }
    public List<CompradorSocioDTO> findCompradoresBySocio(Long idSocio) {
        return ofertaReservaRepository.findCompradoresSocio(idSocio);
    }
}
