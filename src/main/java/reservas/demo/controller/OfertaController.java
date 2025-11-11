package reservas.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservas.demo.models.entitys.Oferta;
import reservas.demo.services.OfertaReservaEstadoServices;
import reservas.demo.services.OfertaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oferta")
public class OfertaController {
    //@Autowired
    private final OfertaService ofertaService;
    private final OfertaReservaEstadoServices ofertaReservaEstadoServices;

    // CREATE
    @PostMapping
    public ResponseEntity<Oferta> createOferta(@RequestBody Oferta oferta) {
        Oferta nuevaOferta = null;
        try {
            nuevaOferta = ofertaService.createOferta(oferta);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(nuevaOferta);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Oferta>> getAllOfertas() {
        List<Oferta> ofertas = ofertaService.getAllOfertas();
        return ResponseEntity.ok(ofertas);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Oferta> getOfertaById(@PathVariable Long id) {
        Optional<Oferta> oferta = ofertaService.getOfertaById(id);
        System.out.println("oferta.toString() = " + oferta.toString());
        return oferta.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Oferta> updateOferta(@PathVariable Long id, @RequestBody Oferta ofertaDetails) {
        Oferta updatedOferta = ofertaService.updateOferta(id, ofertaDetails);
        if (updatedOferta != null) {
            return ResponseEntity.ok(updatedOferta);
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("pagado/{id}")
    public void updateOfertaPagado(@PathVariable Long id) {
        ofertaReservaEstadoServices.ofertaPagada(id);
    }
    @PutMapping("/{id}/{num}")
    public String updateOfertaN(@PathVariable Long id, @PathVariable Integer num) {
        return ofertaService.updateOfertaQuery(id, num);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOferta(@PathVariable Long id) {
        boolean deleted = ofertaService.deleteOferta(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Búsquedas específicas
    @GetMapping("/socio/{idSocio}")
    public ResponseEntity<List<Oferta>> getOfertasByIdSocio(@PathVariable Long idSocio) {
        List<Oferta> ofertas = ofertaService.getOfertasByIdSocio(idSocio);
        return ResponseEntity.ok(ofertas);
    }
    @GetMapping("/caducadas")
    public ResponseEntity<List<Oferta>> listarOfertasConMasDeDosDiasActivas() {
        List<Oferta> ofertas = ofertaService.obtenerOfertasConMasDeDosDiasActivas();
        return ResponseEntity.ok(ofertas);
    }
    @GetMapping("/calidad/{calidad}")
    public ResponseEntity<List<Oferta>> getOfertasByCalidad(@PathVariable String calidad) {
        List<Oferta> ofertas = ofertaService.getOfertasByCalidad(calidad);
        return ResponseEntity.ok(ofertas);
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Oferta>> getOfertasByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Oferta> ofertas = ofertaService.getOfertasByFecha(fecha);
        return ResponseEntity.ok(ofertas);
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<List<Oferta>> getOfertasByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Oferta> ofertas = ofertaService.getOfertasByRangoFechas(startDate, endDate);
        return ResponseEntity.ok(ofertas);
    }

    @GetMapping("/socio-calidad")
    public ResponseEntity<List<Oferta>> getOfertasByIdSocioAndCalidad(
            @RequestParam Long idSocio,
            @RequestParam String calidad) {
        List<Oferta> ofertas = ofertaService.getOfertasByIdSocioAndCalidad(idSocio, calidad);
        return ResponseEntity.ok(ofertas);
    }

    @GetMapping("/precio-mayor")
    public ResponseEntity<List<Oferta>> getOfertasByPrecioGreaterThan(
            @RequestParam Double precioMinimo) {
        List<Oferta> ofertas = ofertaService.getOfertasByPrecioGreaterThan(precioMinimo);
        return ResponseEntity.ok(ofertas);
    }
    // Endpoint para ofertas activas (JPQL)
    @GetMapping("/activo")
    public ResponseEntity<List<Oferta>> getOfertasActivas() {
        List<Oferta> ofertas = ofertaService.getOfertasActivas();
        return ResponseEntity.ok(ofertas);
    }
}
