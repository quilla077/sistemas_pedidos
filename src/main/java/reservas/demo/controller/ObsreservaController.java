package reservas.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservas.demo.models.Enums.EstadoObs;
import reservas.demo.models.Enums.TipoObsRes;
import reservas.demo.models.entitys.Obsreserva;
import reservas.demo.services.ObsreservaService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/obsreserva")
@RequiredArgsConstructor
public class ObsreservaController {
    private final ObsreservaService obsreservaService;

    // CRUD Endpoints
    @GetMapping
    public List<Obsreserva> getAllObsreservas() {
        return obsreservaService.getAllObsreservas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Obsreserva> getObsreservaById(@PathVariable Long id) {
        Optional<Obsreserva> obsreserva = obsreservaService.getObsreservaById(id);
        return obsreserva.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Obsreserva createObsreserva(@RequestBody Obsreserva obsreserva) {
        return obsreservaService.createObsreserva(obsreserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Obsreserva> updateObsreserva(@PathVariable Long id,
                                                       @RequestBody Obsreserva obsreservaDetails) {
        Obsreserva updatedObsreserva = obsreservaService.updateObsreserva(id, obsreservaDetails);
        return updatedObsreserva != null ?
                ResponseEntity.ok(updatedObsreserva) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteObsreserva(@PathVariable Long id) {
        boolean deleted = obsreservaService.deleteObsreserva(id);
        return deleted ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    // Custom search endpoints

    @GetMapping("/tipo/{tipoobs}")
    public List<Obsreserva> getByTipoobs(@PathVariable TipoObsRes tipoobs) {
        return obsreservaService.getByTipoobs(tipoobs);
    }
    @GetMapping("/tipo/{tipoobs}/{id}")
    public List<Obsreserva> getByTipoobsId(@PathVariable TipoObsRes tipoobs,@PathVariable Long id) {
        return obsreservaService.getByTipoobsId(tipoobs,id);
    }

    @GetMapping("/fecha/{fechaobs}")
    public List<Obsreserva> getByFechaobs(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaobs) {
        return obsreservaService.getByFechaobs(fechaobs);
    }

    @GetMapping("/fecha/rango")
    public List<Obsreserva> getByFechaobsBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return obsreservaService.getByFechaobsBetween(inicio, fin);
    }

    @GetMapping("/emisor/{idemisor}")
    public List<Obsreserva> getByIdemisor(@PathVariable Long idemisor) {
        return obsreservaService.getByIdemisor(idemisor);
    }

    @GetMapping("/receptor/{idreceptor}")
    public List<Obsreserva> getByIdreceptor(@PathVariable Long idreceptor) {
        return obsreservaService.getByIdreceptor(idreceptor);
    }

    @GetMapping("/reserva/{idreserva}")
    public List<Obsreserva> getByIdreserva(@PathVariable Long idreserva) {
        return obsreservaService.getByIdreserva(idreserva);
    }

    @GetMapping("/estado/{estado}")
    public List<Obsreserva> getByEstado(@PathVariable EstadoObs estado) {
        return obsreservaService.getByEstado(estado);
    }

    @GetMapping("/busqueda")
    public List<Obsreserva> getByTipoAndEstado(
            @RequestParam TipoObsRes tipo,
            @RequestParam EstadoObs estado) {
        return obsreservaService.getByTipoAndEstado(tipo, estado);
    }

    @GetMapping("/count")
    public long countObsreservas() {
        return obsreservaService.countObsreservas();
    }
}
