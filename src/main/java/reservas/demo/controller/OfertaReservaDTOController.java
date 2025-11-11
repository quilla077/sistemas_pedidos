package reservas.demo.controller;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservas.demo.models.dtos.CompradorSocioDTO;
import reservas.demo.models.dtos.OfertaReservaDTO;
import reservas.demo.models.informaciones.InfoCompReservas;
import reservas.demo.models.informaciones.InfoSocioOfertas;
import reservas.demo.models.informaciones.InfoSocioReservas;
import reservas.demo.services.OfertaReservaDTOService;

import java.util.*;

@RestController
@RequestMapping("/api/v1/oferta-reserva")
@RequiredArgsConstructor
public class OfertaReservaDTOController {
    private final OfertaReservaDTOService ofertaReservaService;
    
    @GetMapping
    public List<OfertaReservaDTO> getAll() {
        return ofertaReservaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfertaReservaDTO> getById(@PathVariable Long id) {
        Optional<OfertaReservaDTO> dto = ofertaReservaService.findById(id);
        return dto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Búsquedas personalizadas
    @GetMapping("/socio/{idsocio}")
    public List<OfertaReservaDTO> getByIdsocio(@PathVariable Long idsocio) {
        return ofertaReservaService.findByIdsocio(idsocio);
    }

    @GetMapping("/comprador/{idcomprador}")
    public List<OfertaReservaDTO> getByIdcomprador(@PathVariable Long idcomprador) {
        return ofertaReservaService.findByIdcomprador(idcomprador);
    }

    @GetMapping("/marca/{marca}")
    public List<OfertaReservaDTO> getByMarca(@PathVariable String marca) {
        return ofertaReservaService.findByMarca(marca);
    }

    // Versión alternativa que acepta fechas como String
    @GetMapping("/{fechaInicio}/{fechaFin}")
    public ResponseEntity<?> getOfertasReservasByDateRangeString(
            @PathVariable String fechaInicio,
            @PathVariable String fechaFin) {
        System.out.println("fechaInicio = " + fechaInicio + ", fechaFin = " + fechaFin);
        try {
            List<OfertaReservaDTO> resultados = ofertaReservaService
                    .getOferReserByDateRange(fechaInicio, fechaFin);
            return ResponseEntity.ok(resultados);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor");
        }
    }

    @GetMapping("/infosocio/{idsocio}/{anio}/{mes}")
    public ResponseEntity<?> getResumenSocio(
            @PathVariable Long idsocio,
            @PathVariable int anio,
            @PathVariable int mes) {
        Map<String, Object> response = new HashMap<>();
        try {
            InfoSocioOfertas infoofertapag = ofertaReservaService.getResumenSocioOferPagado(idsocio, anio, mes);
            InfoSocioReservas inforeservas = ofertaReservaService.getResumenSocioRes(idsocio, anio, mes);
            InfoSocioOfertas infooferta = ofertaReservaService.getResumenSocioOfer(idsocio, anio, mes);
            response.put("success", true);
            response.put("message", "Informacion de Socio obtenido exitosamente");
            response.put("data", Map.of(
                    "pagados", infoofertapag,
                    "reservas", inforeservas,
                    "ofertas", infooferta
            ));
            return ResponseEntity.ok(response);
        } catch (NoResultException e) {
            // Si no hay resultados, retornar objetos vacíos o con valores por defecto
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        }  catch (Exception e) {//throw new RuntimeException(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno del servidor: " + e.getMessage());
        }
    }

    @GetMapping("/infocomp/{idcomprador}/{anio}/{mes}")
    public ResponseEntity<?> getResumenPagos(
            @PathVariable Long idcomprador,
            @PathVariable int anio,
            @PathVariable int mes) {
        Map<String, Object> response = new HashMap<>();
        try {
            InfoCompReservas resumen1 = ofertaReservaService.getResumenPagosComprador(idcomprador, anio, mes);
            InfoCompReservas resumen2 = ofertaReservaService.getResumenPagosComprador(idcomprador, mes-1==0?anio-1:anio, mes-1==0?12:mes-1);
            response.put("success", true);
            response.put("message", "Informacion del comprador obtenido exitosamente");
            response.put("data", Map.of(
                    "mesactual", resumen1,
                    "mesanterior", resumen2
            ));
            return ResponseEntity.ok(response);
        } catch (NoResultException e) {
            // No hay resultados (idsocio no existe o no hay reservas)
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/filtros")
    public ResponseEntity<?> getReservasPorQuincena(
            @RequestParam Integer anio,
            @RequestParam Integer mes,
            @RequestParam Integer quincena,
            @RequestParam Integer tipoBusqueda,
            @RequestParam Long idBusqueda) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<OfertaReservaDTO> reservas = ofertaReservaService.obtenerReservasPorQuincena(
                    tipoBusqueda,idBusqueda, anio, mes, quincena);
            if (reservas.isEmpty()) {
                response.put("success", false);
                response.put("message", "No se encontraron reservas para los criterios especificados");
                response.put("data", Collections.emptyList());
            }else{
                response.put("success", true);
                response.put("message", "Reservas obtenidas exitosamente");
                response.put("data", reservas);
            }
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", "Parámetros inválidos");
            response.put("message", e.getMessage());
            response.put("status", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error interno del servidor");
            response.put("message", "Ocurrió un error inesperado al procesar la solicitud");
            response.put("status", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/filtroComp")
    public ResponseEntity<?> getReservasFiltradaComp(
            @RequestParam Integer anio,
            @RequestParam Integer mes,
            @RequestParam Integer quincena,
            @RequestParam Long idcomp,
            @RequestParam Long idsocio) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<OfertaReservaDTO> reservas = ofertaReservaService.obtenerReservasFiltradaComprador(
                    idcomp,idsocio, anio, mes, quincena);
            if (reservas.isEmpty()) {
                response.put("success", false);
                response.put("message", "No se encontraron reservas para los criterios especificados");
                response.put("data", Collections.emptyList());
            }else{
                response.put("success", true);
                response.put("message", "Reservas obtenidas exitosamente");
                response.put("data", reservas);
            }
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", "Parámetros inválidos");
            response.put("message", e.getMessage());
            response.put("status", 400);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Error interno del servidor");
            response.put("message", "Ocurrió un error inesperado al procesar la solicitud");
            response.put("status", 500);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/comp-socio/{idSocio}")
    public ResponseEntity<List<CompradorSocioDTO>> getCompradoresBySocio(@PathVariable Long idSocio) {
        List<CompradorSocioDTO> compradores = ofertaReservaService.findCompradoresBySocio(idSocio);
        return ResponseEntity.ok(compradores);
    }

}
