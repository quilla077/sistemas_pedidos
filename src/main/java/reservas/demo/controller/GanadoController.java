package reservas.demo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservas.demo.models.Enums.EstadoGanado;
import reservas.demo.models.entitys.Ganado;
import reservas.demo.services.GanadoService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ganado")
public class GanadoController {
    private final GanadoService ganadoService;

    @GetMapping
    public List<Ganado> getAllGanado() {
        return ganadoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ganado> getGanadoById(@PathVariable Long id) {
        Optional<Ganado> ganado = ganadoService.findById(id);
        return ganado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/socio/{idsocio}")
    public ResponseEntity<List<Ganado>> getGanadoBySocioId(@PathVariable Long idsocio) {
        try {
            List<Ganado> ganados = ganadoService.getGanadoBySocioId(idsocio);
            return ResponseEntity.ok(ganados);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/estado/{estado}")
    public ResponseEntity<?> listarPorEstado(@PathVariable EstadoGanado estado) {
        try {
            List<Ganado> ganados = ganadoService.listarPorEstado(estado);
            return ResponseEntity.ok(ganados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar el ganado por estado: " + e.getMessage());
        }
    }
    @GetMapping("/idsocio-estado/{idsocio}/{estado}")
    public ResponseEntity<?> listarPorSocioYEstado(@PathVariable Long idsocio,
                                                   @PathVariable EstadoGanado estado) {
        try {
            List<Ganado> ganados = ganadoService.listarPorSocioYEstado(idsocio, estado);
            return ResponseEntity.ok(ganados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al listar el ganado por socio y estado: " + e.getMessage());
        }
    }

    @PostMapping
    public Ganado createGanado(@RequestBody Ganado ganado) {
        return ganadoService.save(ganado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ganado> updateGanado(@PathVariable Long id, @RequestBody Ganado ganadoDetails) {
        Optional<Ganado> ganado = ganadoService.findById(id);
        if (ganado.isPresent()) {
            Ganado existingGanado = ganado.get();
            // Actualizar campos
            existingGanado.setFechag(ganadoDetails.getFechag());
            existingGanado.setPeso(ganadoDetails.getPeso());
            existingGanado.setProcedencia(ganadoDetails.getProcedencia());
            existingGanado.setEstadog(ganadoDetails.getEstadog());
            existingGanado.setMarca(ganadoDetails.getMarca());
            existingGanado.setIdsocio(ganadoDetails.getIdsocio());
            return ResponseEntity.ok(ganadoService.save(existingGanado));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/{estado}")
    public ResponseEntity<Void> updateEstado(@PathVariable Long id, @PathVariable String estado) {
        try {
            System.out.println("id = " + id + ", estado = " + estado);
            switch (estado){
                case "VERIFICADO":ganadoService.actualizarEstado(id, EstadoGanado.VERIFICADO);
                break;
                case "SACRIFICADO":ganadoService.actualizarEstado(id, EstadoGanado.SACRIFICADO);
                    break;
                case "REGISTRADO":ganadoService.actualizarEstado(id, EstadoGanado.REGISTRADO);
                    break;
            }
            //EstadoGanado nuevoEstado = EstadoGanado.valueOf(estado);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGanado(@PathVariable Long id) {
        if (ganadoService.findById(id).isPresent()) {
            ganadoService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
