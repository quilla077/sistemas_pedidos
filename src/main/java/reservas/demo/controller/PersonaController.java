package reservas.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reservas.demo.Seguridad.Autenticacion.AuthResponse;
import reservas.demo.Seguridad.Autenticacion.AuthService;
import reservas.demo.models.Enums.Role;
import reservas.demo.models.dtos.*;
import reservas.demo.models.entitys.*;
import reservas.demo.services.PersonaServicesImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PersonaController {
    private final PersonaServicesImpl personaServices;
    private final AuthService authService;

    @GetMapping("/personas")
    public List<Persona> listarPersonas(){
        return personaServices.listarTodasPersonas();
    }
    @GetMapping("/persona/{id}")
    public ResponseEntity<Persona> obtPersona(@PathVariable Long id){
        Persona persona = personaServices.obtenerPersona(id);
        return ResponseEntity.ok(persona);
    }
    @PostMapping("/persona")
    public ResponseEntity<Persona> registrarPersona(@RequestBody Persona persona) {
        return ResponseEntity.ok(personaServices.guardarPersona(persona));
    }

    @GetMapping("/useradmindtos/{state}")
    public List<UserAdminDto> filtrarUsuariosAdminsDto( @PathVariable String state){
        return personaServices.filtrarUserAdminDtos(state);
    }
    @GetMapping("/usersociodtos/{state}")
    public List<UserSocioDto> filtrarUsuariosSociosDto(@PathVariable String state){
        return personaServices.filtrarUserSocioDtos(state);
    }
    @GetMapping("/usercompdtos/{state}")
    public List<UserCompradorDto> filtrarUsuariosCompsDto(@PathVariable String state){
        return personaServices.filtrarUserCompradorDtos(state);
    }
    @GetMapping("/userporterodtos/{state}")
    public List<UserPorteroDto> filtrarUsuariosporteroDto(@PathVariable String state){
        return personaServices.filtrarUserPorteroDtos(state);
    }

    @GetMapping("/socios")
    public List<Socio> listarSocios(){
        return personaServices.obtenerTodosSocios();
    }
    @GetMapping("/compradores")
    public List<Comprador> listarCompradores(){
        return personaServices.obtenerTodosCompradores();
    }
    @GetMapping("/admins")
    public List<Admin> listarAdmins(){
        return personaServices.obtenerTodosAdmins();
    }

    @PostMapping("/socio")
    public ResponseEntity<Socio> registrarSocio(@RequestBody Sociodto socio) {
        Socio socioGuardado = (Socio) personaServices.guardarPersona(socio.getSocio());
        AuthResponse a=authService.guardarUsuario(socio.getUsu(),socioGuardado.getId_persona());
        System.out.println(a.getTipo()+" registrado: " + a.getIdper());
        return ResponseEntity.ok(socioGuardado);
        //return ResponseEntity.ok((Socio) personaServices.guardarPersona(socio));
    }
    @PostMapping("/comprador")
    public ResponseEntity<Comprador> registrarComprador(@RequestBody Compradordto comprador) {
        Comprador compGuardado = (Comprador) personaServices.guardarPersona(comprador.getComprador());
        AuthResponse a=authService.guardarUsuario(comprador.getUsu(),compGuardado.getId_persona());
        System.out.println(a.getTipo()+" registrado: " + a.getIdper());
        return ResponseEntity.ok(compGuardado);
        //return ResponseEntity.ok((Comprador) personaServices.guardarPersona(comprador));
    }
    @PostMapping("/admin")
    public ResponseEntity<Admin> registrarAdministrador(@RequestBody Admindto admin) {
        Admin adminGuardado = (Admin) personaServices.guardarPersona(admin.getAdmin());
        AuthResponse a=authService.guardarUsuario(admin.getUsu(),adminGuardado.getId_persona());
        System.out.println(a.getTipo()+" registrado: " + a.getIdper());
        return ResponseEntity.ok(adminGuardado);
        //return ResponseEntity.ok((Admin) personaServices.guardarPersona(administrador));
    }
    @PostMapping("/portero")
    public ResponseEntity<Socio> registrarPortero(@RequestBody Sociodto socio) {
        Socio socioGuardado = (Socio) personaServices.guardarPersona(socio.getSocio());
        AuthResponse a=authService.guardarUsuario(socio.getUsu(),socioGuardado.getId_persona());
        System.out.println(a.getTipo()+" registrado: " + a.getIdper());
        return ResponseEntity.ok(socioGuardado);
        //return ResponseEntity.ok((Socio) personaServices.guardarPersona(socio));
    }

    @PostMapping("/adminPrueba")
    public ResponseEntity<?> registrarAdministradorPrueba(@RequestBody Admindto admin) {
        Admin adminGuardado = (Admin) personaServices.guardarPersona(admin.getAdmin());
        AuthResponse a=authService.guardarUsuario(admin.getUsu(),adminGuardado.getId_persona());
        Map<String,Object> response = new HashMap<>();
        List<UserAdminDto> l = personaServices.filtrarUserAdminDtos("activo");
        response.put("sizelista",l.size());
        response.put("auth",a);
        response.put("lista",l);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/socioPrueba")
    public ResponseEntity<?> registrarSocioPrueba(@RequestBody Sociodto socio) {
        Socio socioGuardado = (Socio) personaServices.guardarPersona(socio.getSocio());
        AuthResponse a=authService.guardarUsuario(socio.getUsu(),socioGuardado.getId_persona());
        Map<String,Object> response = new HashMap<>();
        List<UserSocioDto> l = personaServices.filtrarUserSocioDtos("activo");
        response.put("sizelista",l.size());
        response.put("auth",a);
        response.put("lista",l);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/compradorPrueba")
    public ResponseEntity<?> registrarCompradorPrueba(@RequestBody Compradordto comprador) {
        Comprador compGuardado = (Comprador) personaServices.guardarPersona(comprador.getComprador());
        AuthResponse a=authService.guardarUsuario(comprador.getUsu(),compGuardado.getId_persona());
        Map<String,Object> response = new HashMap<>();
        List<UserCompradorDto> l = personaServices.filtrarUserCompradorDtos("activo");
        response.put("sizelista",l.size());
        response.put("auth",a);
        response.put("lista",l);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PostMapping("/porteroPrueba")
    public ResponseEntity<?> registrarPorteroPrueba(@RequestBody Porterodto portero) {
        Portero porteroGuardado = (Portero) personaServices.guardarPersona(portero.getPortero());
        AuthResponse a=authService.guardarUsuario(portero.getUsu(),porteroGuardado.getId_persona());
        Map<String,Object> response = new HashMap<>();
        List<UserPorteroDto> l = personaServices.filtrarUserPorteroDtos("activo");
        response.put("sizelista",l.size());
        response.put("auth",a);
        response.put("lista",l);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/admin/{criterio}/{num}")
    public ResponseEntity<?> getAdminById(@PathVariable String criterio,@PathVariable Long num) {
        Map<String,Object> response = new HashMap<>();
        return personaServices.obtenerAdminPor(criterio,num)
                .map(admin -> {
                    response.put("mensaje", Role.ADMIN+" encontrado");
                    response.put("usuario", admin);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("mensaje", Role.ADMIN+" con ID " + num + " no encontrado");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                });
    }
    @GetMapping("/socio/{criterio}/{num}")
    public ResponseEntity<?> getSocioById(@PathVariable String criterio,@PathVariable Long num) {
        Map<String, Object> response = new HashMap<>();
        return personaServices.obtenerSocioPor(criterio,num)
                .map(socio -> {
                    response.put("mensaje", Role.SOCIO+" encontrado");
                    response.put("usuario", socio);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("mensaje", Role.SOCIO+" con ID " + num + " no encontrado");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                });
    }
    @GetMapping("/comprador/{criterio}/{num}")
    public ResponseEntity<?> getCompradorById(@PathVariable String criterio,@PathVariable Long num) {
        Map<String, Object> response = new HashMap<>();
        return personaServices.obtenerCompradorPor(criterio,num)
                .map(comp -> {
                    response.put("mensaje", Role.COMPRADOR+" encontrado");
                    response.put("usuario", comp);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("mensaje", Role.COMPRADOR+" con ID " + num + " no encontrado");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                });
    }
    @GetMapping("/portero/{criterio}/{num}")
    public ResponseEntity<?> getPorteroById(@PathVariable String criterio,@PathVariable Long num) {
        Map<String, Object> response = new HashMap<>();
        return personaServices.obtenerPorteroPor(criterio,num)
                .map(comp -> {
                    response.put("mensaje", Role.PORTERO+" encontrado");
                    response.put("usuario", comp);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    response.put("mensaje", Role.PORTERO+" con ID " + num + " no encontrado");
                    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
                });
    }

    @PutMapping("/socio")
    public ResponseEntity<Socio> actualizarSocio(@RequestBody Sociodto socio) {
        Socio socioGuardado = (Socio) personaServices.guardarPersona(socio.getSocio());
        AuthResponse a=authService.actualizarUsuario(socio.getUsu());
        System.out.println(a.getTipo()+" actualizado: " + a.getIdper());
        return ResponseEntity.ok(socioGuardado);
    }
    @PutMapping("/comprador")
    public ResponseEntity<Comprador> actualizarComprador(@RequestBody Compradordto comprador) {
        Comprador compGuardado = (Comprador) personaServices.guardarPersona(comprador.getComprador());
        AuthResponse a=authService.actualizarUsuario(comprador.getUsu());
        System.out.println(a.getTipo()+" actualizado: " + a.getIdper());
        return ResponseEntity.ok(compGuardado);
    }
    @PutMapping("/admin")
    public ResponseEntity<Admin> actualizarAdministrador(@RequestBody Admindto admin) {
        Admin adminGuardado = (Admin) personaServices.guardarPersona(admin.getAdmin());
        AuthResponse a=authService.actualizarUsuario(admin.getUsu());
        System.out.println(a.getTipo()+" actualizado: " + a.getIdper());
        return ResponseEntity.ok(adminGuardado);
    }
    @PutMapping("/portero")
    public ResponseEntity<Portero> actualizarPortero(@RequestBody Porterodto portero) {
        Portero porteroGuardado = (Portero) personaServices.guardarPersona(portero.getPortero());
        AuthResponse a=authService.actualizarUsuario(portero.getUsu());
        System.out.println(a.getTipo()+" actualizado: " + a.getIdper());
        return ResponseEntity.ok(porteroGuardado);
    }


}
