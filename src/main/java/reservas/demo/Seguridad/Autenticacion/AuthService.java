package reservas.demo.Seguridad.Autenticacion;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reservas.demo.Seguridad.JsonWebToken.JwtService;
import reservas.demo.models.Enums.CodeAuthResponse;
import reservas.demo.models.Enums.Role;
import reservas.demo.models.entitys.Persona;
import reservas.demo.models.entitys.Socio;
import reservas.demo.models.entitys.Usuario;
import reservas.demo.repository.PersonaRepository;
import reservas.demo.repository.UsuarioRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        AuthResponse authResponse=AuthResponse.builder().token("").build();
        try{
            UserDetails user = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            Usuario u = usuarioRepository.obtenerByUsername(user.getUsername());
            String token=jwtService.getToken(user);
            authResponse.setToken(token);authResponse.setIdper(u.getIdper());
            authResponse.setCode(CodeAuthResponse.OK);authResponse.setTipo(u.getRole());
            authResponse.setMensaje("Credenciales correctos");
            if (u.getRole().equals(Role.SOCIO)){
                Socio per = personaRepository.findSocioById(u.getIdper());
                authResponse.setMarca(per.getMarca());
            }else{
                authResponse.setMarca("ninguno");
            }
            return authResponse;
        }catch (AuthenticationException a){
            System.out.println(a.getMessage()+" La contraseña es erronea ");
            authResponse.setCode(CodeAuthResponse.PWD);
            authResponse.setMensaje("Contraseña incorrecta");
        }catch (NoSuchElementException n){
            System.out.println("El usuario no existe: " + n.getMessage());
            authResponse.setCode(CodeAuthResponse.USU);
            authResponse.setMensaje("Nombre de usuario no registrado");
        } catch (Exception ex){
            System.out.println("Algo paso en la obtencion de datos."+ex.getMessage());
        }
        return authResponse;
    }

    public AuthResponse register(RegisterRequest request) {
        System.out.println(request.getUsername()+" - "+request.getRole());
        System.out.println(request.getIdper());
        Usuario usuario = Usuario.builder()
                .idper(request.getIdper())
                //.idper(usuarioRepository.findMaxIdPersona()+1)
                .username(request.getUsername())
                .password(passwordEncoder.encode( request.getPassword()))
                .email(request.getEmail())
                .estado(request.getEstado())
                .role(Role.valueOf(request.getRole() ))
            .build();
        usuarioRepository.save(usuario);
        return AuthResponse.builder()
            .token(jwtService.getToken(usuario)) //peticion de token
            .idper(usuario.getIdper())
            .tipo(usuario.getRole())
            .build();
    }
    public AuthResponse guardarUsuario(Usuario usu, Long id) {
        System.out.println(usu.getUsername()+" - "+usu.getRole());
        Usuario usuario = Usuario.builder()
                .idper(id)
                .username(usu.getUsername())
                .password(passwordEncoder.encode( usu.getPassword()))
                .email(usu.getEmail())
                .estado(usu.getEstado())
                .role(usu.getRole())
                .build();
        usuarioRepository.save(usuario);
        return AuthResponse.builder()
                .idper(usuario.getIdper())
                .tipo(usuario.getRole())
                .build();
    }
    public AuthResponse actualizarUsuario(Usuario usu) {
        System.out.println(usu.getUsername()+" - "+usu.getRole());
        Usuario usuario = Usuario.builder()
                .idper(usu.getIdper())
                .username(usu.getUsername())
                .password(usu.getPassword())
                .email(usu.getEmail())
                .estado(usu.getEstado())
                .role(usu.getRole())
                .build();
        usuarioRepository.save(usuario);
        return AuthResponse.builder()
                .idper(usuario.getIdper())
                .tipo(usuario.getRole())
                .build();
    }

}