package mx.edu.uacm.ws.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.uacm.ws.Entity.Partero;
import mx.edu.uacm.ws.Repository.ParteroRepository;

@Service
@Transactional
public class ParteroService {
    
    @Autowired
    private ParteroRepository parteroRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Partero registrarPartero(Partero partero) {
        if (parteroRepository.existsByEmail(partero.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
        
        // Validar que la contraseña no sea nula
        if (partero.getPasswordHash() == null || partero.getPasswordHash().trim().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }
        
        String passwordHash = passwordEncoder.encode(partero.getPasswordHash());
        partero.setPasswordHash(passwordHash);
        partero.setFechaRegistro(LocalDateTime.now());
        
        return parteroRepository.save(partero);
    }
    
    @Transactional
    public boolean actualizarPassword(String email, String passwordNow, String passwordNew) {
        Optional<Partero> parteroUp = parteroRepository.findByEmail(email);
        
        if (parteroUp.isPresent()) {
            Partero partero = parteroUp.get();
            
            if (passwordEncoder.matches(passwordNow, partero.getPasswordHash())) {
                String newPasswordHash = passwordEncoder.encode(passwordNew);
                partero.setPasswordHash(newPasswordHash);
                parteroRepository.save(partero);
                return true;
            }
        }
        return false;
    }
    
    public Optional<Partero> findByEmail(String email) {
        return parteroRepository.findByEmail(email);
    }
    
    public boolean existsEmail(String email) {
        return parteroRepository.existsByEmail(email);
    }
}