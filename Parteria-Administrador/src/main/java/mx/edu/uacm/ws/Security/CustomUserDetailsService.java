package mx.edu.uacm.ws.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.edu.uacm.ws.Service.AdministradorService;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private AdministradorService administradorService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		return administradorService.findByEmail(email).map(administrador -> User.builder()
				.username(administrador.getEmail())
				.password(administrador.getPassword_hash())
				.roles("ADMINISTRADOR")
				.build())
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no esncontrado: " + email));
	}
}
