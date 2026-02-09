package mx.edu.uacm.ws.Repository;

import mx.edu.uacm.ws.Entity.ParteroProfesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParteroProfesionalRepository extends JpaRepository<ParteroProfesional, Integer> {
    
    @Query("SELECT p FROM ParteroProfesional p WHERE p.id_partero = :idPartero ORDER BY p.id_PProfesional DESC LIMIT 1")
    Optional<ParteroProfesional> findFirstByParteroId(@Param("idPartero") Integer idPartero);
    
    @Query("SELECT p FROM ParteroProfesional p WHERE p.id_partero = :idPartero")
    List<ParteroProfesional> findAllByParteroId(@Param("idPartero") Integer idPartero);
    
    @Query("SELECT COUNT(p) > 0 FROM ParteroProfesional p WHERE p.id_partero = :idPartero")
    boolean existsByParteroId(@Param("idPartero") Integer idPartero);
}