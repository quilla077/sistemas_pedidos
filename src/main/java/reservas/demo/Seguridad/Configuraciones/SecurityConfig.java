package reservas.demo.Seguridad.Configuraciones;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import reservas.demo.Seguridad.JsonWebToken.JwtAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilita CORS
            .csrf(AbstractHttpConfigurer::disable)
            //.csrf().disable()
//            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authRequest ->
              authRequest
                .requestMatchers("/auth/**").permitAll() //,"/api/v1/**"
                .anyRequest().authenticated()
                )
            .sessionManagement(sessionManager->
                sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();

    }
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        // Orígenes permitidos para pruebas
        List<String> allowedOrigins = Arrays.asList(
                "http://localhost:4200",      // Tu frontend local
                "http://192.168.0.12:4200",    // Tu frontend en red local
                "http://0.0.0.0",             // Para cualquier IP local
                "https://www.postman.com",    // Alternativa para Postman
                "https://sistema-reservas-l5cqppfk3-roods-projects.vercel.app/",
                "https://sistema-reservas-weld.vercel.app/",
                "https://web.postman.co"      // Para Postman web
        );
        //config.setAllowedOrigins(List.of("*")); // Permite solo tu frontend
        config.setAllowedOrigins(allowedOrigins); // Permite solo tu frontend
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE","OPTIONS")); //  Métodos permitidos
        config.setAllowedHeaders(List.of("*")); // Headers permitidos
        config.setAllowCredentials(true); // Necesario si usas cookies/tokens

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica a todas las rutas
        return source;
    }

}
