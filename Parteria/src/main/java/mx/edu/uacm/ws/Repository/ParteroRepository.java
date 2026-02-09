package mx.edu.uacm.ws.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.edu.uacm.ws.Entity.Partero;

@Repository
public interface ParteroRepository extends JpaRepository<Partero, Long>{
	Optional<Partero> findByEmail(String email);
    boolean existsByEmail(String email);
}
