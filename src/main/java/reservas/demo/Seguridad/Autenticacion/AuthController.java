package reservas.demo.Seguridad.Autenticacion;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservas.demo.models.entitys.Usuario;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    //@CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    //@CrossOrigin(origins = "http://localhost:4200")
    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register( @RequestBody  RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
    //@CrossOrigin(origins = "http://localhost:4200")
    @PutMapping(value = "/update")
    public ResponseEntity<AuthResponse> update( @RequestBody Usuario usu) {
        return ResponseEntity.ok(authService.actualizarUsuario(usu));
    }


}
