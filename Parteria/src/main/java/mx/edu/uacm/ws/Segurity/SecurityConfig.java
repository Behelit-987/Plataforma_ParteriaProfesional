package mx.edu.uacm.ws.Segurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas - acceso sin autenticación
                .requestMatchers(
                    "/",
                    "/inicio", 
                    "/login-parteria", 
                    "/registro", 
                    "/registrar", 
                    "/actualizar-contrasena", 
                    "/actualizar-password",
                    "/css/**", 
                    "/js/**", 
                    "/images/**", 
                    "/webjars/**"
                    
                    //, "/api/files/**"
                ).permitAll()
                // Todas las demás rutas requieren autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login-parteria")
                .loginProcessingUrl("/login-parteria")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/inicio", true) // Esto redirigirá a /inicio después del login
                .failureUrl("/login-parteria?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login-parteria?logout=true") // Redirigir al login después del logout
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}