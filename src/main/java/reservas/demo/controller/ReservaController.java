package reservas.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservas.demo.models.entitys.Reserva;
import reservas.demo.services.ReservaService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reserva")
public class ReservaController {

    private final ReservaService reservaService;

    // CRUD endpoints
    @GetMapping
    public List<Reserva> getAllReservas() {
        return reservaService.getAllReservas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> getReservaById(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.getReservaById(id);
        return reserva.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reserva createReserva(@RequestBody Reserva reserva) {
        return reservaService.createReserva(reserva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> updateReserva(@PathVariable Long id, @RequestBody Reserva reservaDetails) {
        Reserva updatedReserva = reservaService.updateReserva(id, reservaDetails);
        if (updatedReserva != null) {
            return ResponseEntity.ok(updatedReserva);
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}/{num}")
    public String updateOfertaN(@PathVariable Long id, @PathVariable Integer num) {
        return reservaService.updateReservaQuery(id, num);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReserva(@PathVariable Long id) {
        if (reservaService.deleteReserva(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Endpoints de consulta personalizada
    @GetMapping("/comprador/{idComprador}")
    public List<Reserva> getReservasByComprador(@PathVariable Long idComprador) {
        return reservaService.getReservasByComprador(idComprador);
    }

    @GetMapping("/fecha/{fecha}")
    public List<Reserva> getReservasByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return reservaService.getReservasByFecha(fecha);
    }

    @GetMapping("/estado/{estado}")
    public List<Reserva> getReservasByEstado(@PathVariable String estado) {
        return reservaService.getReservasByEstado(estado);
    }

    @GetMapping("/marca/{marca}")
    public List<Reserva> getReservasByMarca(@PathVariable String marca) {
        return reservaService.getReservasByMarca(marca);
    }

    @GetMapping("/comprador/{idComprador}/estado/{estado}")
    public List<Reserva> getReservasByCompradorAndEstado(
            @PathVariable Long idComprador, @PathVariable String estado) {
        return reservaService.getReservasByCompradorAndEstado(idComprador, estado);
    }

    @GetMapping("/rango-fechas")
    public List<Reserva> getReservasByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return reservaService.getReservasByRangoFechas(inicio, fin);
    }
}
