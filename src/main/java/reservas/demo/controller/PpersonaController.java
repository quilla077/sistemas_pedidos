package reservas.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import reservas.demo.models.BodyMensaje;
import reservas.demo.models.Ppersona;
import reservas.demo.services.DatoServiceClient;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cumples")
public class PpersonaController {
    private final DatoServiceClient personaService;

    public PpersonaController(DatoServiceClient personaService) {
        this.personaService = personaService;
    }

    @GetMapping("/{id}")//Mono
    public ResponseEntity<?> consumirCumples(@PathVariable int id) {
        System.out.println("idController = " + id);
//        return personaService.listarCumples(id);
        try {
            List<Ppersona> a = personaService.listarCumples1(id);
            //String response = personaService.listarCumples(id);
            return ResponseEntity.ok(a);
        } catch (HttpClientErrorException e) {
            // Errores 4xx
            return ResponseEntity.status(e.getStatusCode())
                    .body("Error en la solicitud: " + e.getStatusText());
        } catch (HttpServerErrorException e) {
            // Errores 5xx
            return ResponseEntity.status(e.getStatusCode())
                    .body("Error en el servidor remoto: " + e.getStatusText());
        } catch (ResourceAccessException e) {
            // Problemas de conexión, timeout, etc.
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("No se pudo conectar con el servicio remoto: " + e.getMessage());
        } catch (Exception e) {
            // Otros errores inesperados
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado: " + e.getMessage());
        }
    }
    @PostMapping("/crear-socio")
    public ResponseEntity<?> crearSocio(@RequestBody Ppersona socio) {
        try {
            Ppersona socioCreado = personaService.crearSocio(socio);
            return ResponseEntity.ok(socioCreado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear socio: " + e.getMessage());
        }
    }
    @PostMapping("")
    public ResponseEntity<?> envioMenssage(@RequestBody BodyMensaje bodymessage) {
        try {
            personaService.enviarMensaje(bodymessage);
            return ResponseEntity.ok("gracias a Dios");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al crear socio: " + e.getMessage());
        }
    }
}
