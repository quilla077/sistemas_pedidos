package reservas.demo.services;


import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import reservas.demo.models.BodyMensaje;
import reservas.demo.models.Ppersona;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DatoServiceClient {
    private final RestTemplate restTemplate;

    public DatoServiceClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<Ppersona> listarCumples1(int id) {
        String url = "http://localhost:8080/api/v1/listarCumples/{id}";
        ResponseEntity<List<Ppersona>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Ppersona>>() {},
                id
        );
        return response.getBody();
    }
    public Ppersona crearSocio(Ppersona socio) {
        String url = "http://localhost:8080/api/v1/socios"; // Cambia la URL según tu API
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Ppersona> request = new HttpEntity<>(socio, headers);
        ResponseEntity<Ppersona> response = restTemplate.postForEntity(url, request, Ppersona.class);
        return response.getBody();
    }
    public Ppersona crearSocio2(Ppersona socio, String apiKey) {
        String url = "http://localhost:8080/api/v1/socios"; // Cambia la URL según tu API
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", apiKey);
        HttpEntity<Ppersona> request = new HttpEntity<>(socio, headers);
        ResponseEntity<Ppersona> response = restTemplate.postForEntity(url, request, Ppersona.class);
        return response.getBody();
    }
    public void enviarMensaje(BodyMensaje bodymensaje) {
        System.out.println(bodymensaje.toString());
        String url = "http://localhost:8080/message/sendText/api_reservas"; // ip_192.168.100.20 - instancia: test_api_wap
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);//headers.set("x-api-key", apiKey);
        headers.set("apikey", "922BD618A445-4490-B907-C85700EC0F7F");
        HttpEntity<BodyMensaje> request = new HttpEntity<>(bodymensaje, headers);
        ResponseEntity<BodyMensaje> response = restTemplate.postForEntity(url, request, BodyMensaje.class);
        //return response.getBody();
    }

}
