package mx.edu.uacm.ws.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.uacm.ws.Entity.Administrador;

@Repository
public interface AdministradoRepository extends JpaRepository<Administrador, Long>{
	Optional<Administrador> findByEmail(String email);
	boolean existsByEmail(String email);
}
