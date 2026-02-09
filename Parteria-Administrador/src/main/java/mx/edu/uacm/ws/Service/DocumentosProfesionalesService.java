package mx.edu.uacm.ws.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import mx.edu.uacm.ws.Entity.DocumentosProfesionales;
import mx.edu.uacm.ws.Repository.DocumentosProfesionalesRepository;

@Service
@Transactional
public class DocumentosProfesionalesService {
    
    @Autowired
    private DocumentosProfesionalesRepository documentosProfesionalesRepository;
    
    public List<DocumentosProfesionales> obtenerTodosDocumentos(){
        return documentosProfesionalesRepository.findAll();
    }
    
    public List<DocumentosProfesionales> obtenerDocumentosPorRevisar(){
        return documentosProfesionalesRepository.findDocumentosPorRevisar();
    }
    
    public DocumentosProfesionales obtenerDocumentoPorId(Integer id) {
        return documentosProfesionalesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento no encontrado con ID: " + id));
    }
    
    public DocumentosProfesionales actualizarEstadoDocumento(
            Integer id, 
            DocumentosProfesionales.DocumentoEstado estadoTitulo,
            DocumentosProfesionales.DocumentoEstado estadoCedula,
            DocumentosProfesionales.DocumentoEstado estadoIne,
            DocumentosProfesionales.DocumentoEstado estadoComprobante,
            DocumentosProfesionales.DocumentoEstado estadoFotografia,
            DocumentosProfesionales.DocumentoEstado estadoCv,
            DocumentosProfesionales.DocumentoEstado estadoReanimacionNeonatal,
            DocumentosProfesionales.DocumentoEstado estadoReanimacionAdultos,
            DocumentosProfesionales.DocumentoEstado estadoUrgencias,
            DocumentosProfesionales.EstadoGeneral estadoGeneral,
            String observaciones) {
        
        DocumentosProfesionales documento = obtenerDocumentoPorId(id);
        
        // Actualizar los estados individuales usando los setters camelCase
        if (estadoTitulo != null) documento.setEstadoTitulo(estadoTitulo);
        if (estadoCedula != null) documento.setEstadoCedula(estadoCedula);
        if (estadoIne != null) documento.setEstadoIne(estadoIne);
        if (estadoComprobante != null) documento.setEstadoComprobante(estadoComprobante);
        if (estadoFotografia != null) documento.setEstadoFotografia(estadoFotografia);
        if (estadoCv != null) documento.setEstadoCv(estadoCv);
        if (estadoReanimacionNeonatal != null) documento.setEstadoReanimacionNeonatal(estadoReanimacionNeonatal);
        if (estadoReanimacionAdultos != null) documento.setEstadoReanimacionAdultos(estadoReanimacionAdultos);
        if (estadoUrgencias != null) documento.setEstadoUrgencias(estadoUrgencias);
        if (estadoGeneral != null) documento.setEstadoGeneral(estadoGeneral);
        
        return documentosProfesionalesRepository.save(documento);
    }
    
    public Long contarDocumentosPendientes() {
        Long count = documentosProfesionalesRepository.countPendientes();
        return count != null ? count : 0L;
    }
    
    public Long contarDocumentosRechazados() {
        Long count = documentosProfesionalesRepository.countRechazados();
        return count != null ? count : 0L;
    }
    
    public Long contarDocumentosAprobados() {
        Long count = documentosProfesionalesRepository.countAprobados();
        return count != null ? count : 0L;
    }
    
    public List<DocumentosProfesionales> buscarPorEstadoGeneral(DocumentosProfesionales.EstadoGeneral estadoGeneral){
        return documentosProfesionalesRepository.findByEstadoGeneral(estadoGeneral);
    }
    
    public List<DocumentosProfesionales> buscarPorIdPartero(Integer idPartero){
        return documentosProfesionalesRepository.findByIdPartero(idPartero);
    }
    
    // Método adicional para contar documentos en revisión
    public Long contarDocumentosEnRevision() {
        return documentosProfesionalesRepository.countByEstadoGeneral(DocumentosProfesionales.EstadoGeneral.en_revision);
    }
    
    // Método para contar todos los documentos
    public Long contarTodosDocumentos() {
        return documentosProfesionalesRepository.count();
    }
}