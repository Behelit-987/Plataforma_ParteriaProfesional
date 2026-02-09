package mx.edu.uacm.ws.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.edu.uacm.ws.Entity.Administrador;
import mx.edu.uacm.ws.Repository.AdministradoRepository;

@Service
@Transactional
public class AdministradorService {
	
	@Autowired
	private AdministradoRepository administradorRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public Administrador registrarAdministrador(Administrador administrador) {
		if(administradorRepository.existsByEmail(administrador.getEmail())) {
			throw new RuntimeException ("Este correo ya esta registrado");
		}
		if(administrador.getPassword_hash() == null || administrador.getPassword_hash().trim().isEmpty()) {
			throw new RuntimeException("Contraseña Obligatoria");
		}
		
		String passwordHash = passwordEncoder.encode(administrador.getPassword_hash());
		administrador.setPassword_hash(passwordHash);
		administrador.setFechaRegistro(LocalDateTime.now());
		
		return administradorRepository.save(administrador);
	}
	
	public Optional<Administrador> findByEmail(String email){
		return administradorRepository.findByEmail(email);
	}
	
	public boolean existsEmail(String email) {
		return administradorRepository.existsByEmail(email);
	}
	
}
