package reservas.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reservas.demo.models.entitys.MensajeWap;
import reservas.demo.services.MensajeWapService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mensajewap")
@AllArgsConstructor
public class MensajeWapController {
    private final MensajeWapService mensajeWapService;

    @GetMapping
    public List<MensajeWap> getAllMensajeWap() {
        return mensajeWapService.obtenerMensajesEnEspera();
    }

}
