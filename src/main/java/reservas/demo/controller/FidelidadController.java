package reservas.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reservas.demo.models.entitys.Fidelidad;
import reservas.demo.services.FidelidadService;

@RestController
@RequestMapping("/api/v1/oferta/fide")
@AllArgsConstructor
public class FidelidadController {
    private final FidelidadService fidelidadService;
    @PostMapping
    public ResponseEntity<Fidelidad> createFidelidad(@RequestBody Fidelidad fidelidad) {
        try {
            Fidelidad nuevaFidelidad = fidelidadService.createFidelidad(fidelidad);
            return new ResponseEntity<>(nuevaFidelidad, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
