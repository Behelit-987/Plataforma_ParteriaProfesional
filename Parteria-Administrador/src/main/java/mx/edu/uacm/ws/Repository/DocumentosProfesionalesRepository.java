package mx.edu.uacm.ws.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.edu.uacm.ws.Entity.DocumentosProfesionales;

@Repository
public interface DocumentosProfesionalesRepository extends JpaRepository<DocumentosProfesionales, Integer> {
    
    // IMPORTANTE: Usar estadoGeneral (camelCase) en lugar de estado_general (snake_case)
    @Query("SELECT d FROM DocumentosProfesionales d WHERE d.estadoGeneral = :estadoGeneral")
    List<DocumentosProfesionales> findByEstadoGeneral(@Param("estadoGeneral") DocumentosProfesionales.EstadoGeneral estadoGeneral);
    
    @Query("SELECT d FROM DocumentosProfesionales d WHERE d.idPartero = :idPartero")
    List<DocumentosProfesionales> findByIdPartero(@Param("idPartero") Integer idPartero);
    
 // En DocumentosProfesionalesRepository.java
    @Query("SELECT d FROM DocumentosProfesionales d WHERE " +
           "d.estadoGeneral = 'pendiente' OR " +  
           "d.estadoGeneral = 'en_revision' " +   
           "ORDER BY d.idPProfesional DESC")
    List<DocumentosProfesionales> findDocumentosPorRevisar();

    // Consultas de conteo también actualizadas
    @Query("SELECT COUNT(d) FROM DocumentosProfesionales d WHERE d.estadoGeneral = 'pendiente'")
    Long countPendientes();

    @Query("SELECT COUNT(d) FROM DocumentosProfesionales d WHERE d.estadoGeneral = 'aprobado'")
    Long countAprobados();

    @Query("SELECT COUNT(d) FROM DocumentosProfesionales d WHERE d.estadoGeneral = 'rechazado'")
    Long countRechazados();
    
    @Query("SELECT COUNT(d) FROM DocumentosProfesionales d WHERE d.estadoGeneral = :estadoGeneral")
    Long countByEstadoGeneral(@Param("estadoGeneral") DocumentosProfesionales.EstadoGeneral estadoGeneral);
}