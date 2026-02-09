package mx.edu.uacm.ws.Segurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.edu.uacm.ws.Service.ParteroService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ParteroService parteroService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return parteroService.findByEmail(email)
                .map(partero -> User.builder()
                        .username(partero.getEmail())
                        .password(partero.getPasswordHash())
                        .roles("PARTERO")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
    }
}