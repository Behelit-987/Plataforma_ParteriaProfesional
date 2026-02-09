package mx.edu.uacm.ws.Service;

import mx.edu.uacm.ws.Entity.ParteroProfesional;
import mx.edu.uacm.ws.Repository.ParteroProfesionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ParteroProfesionalService {

    @Autowired
    private ParteroProfesionalRepository parteroProfesionalRepository;

    @Autowired
    private DocumentoProfesionalService documentoService;

    // Método principal para obtener un único registro por id_partero
    public Optional<ParteroProfesional> obtenerPorIdPartero(Integer id_partero) {
        return parteroProfesionalRepository.findFirstByParteroId(id_partero);
    }

    // Método para diagnóstico: obtener todos los registros de un partero
    public List<ParteroProfesional> obtenerTodosPorIdPartero(Integer id_partero) {
        return parteroProfesionalRepository.findAllByParteroId(id_partero);
    }

    // Método para eliminar duplicados manteniendo solo el registro más reciente
    public void eliminarDuplicados(Integer id_partero) {
        List<ParteroProfesional> registros = parteroProfesionalRepository.findAllByParteroId(id_partero);
        if (registros.size() > 1) {
            // Mantener el registro más reciente (mayor id_PProfesional)
            ParteroProfesional masReciente = registros.stream()
                .max((r1, r2) -> Integer.compare(r1.getId_PProfesional(), r2.getId_PProfesional()))
                .orElse(null);
            
            if (masReciente != null) {
                // Eliminar los demás
                for (ParteroProfesional registro : registros) {
                    if (!registro.getId_PProfesional().equals(masReciente.getId_PProfesional())) {
                        parteroProfesionalRepository.delete(registro);
                    }
                }
            }
        }
    }

    // Método principal para guardar o actualizar - asegura que no haya duplicados
    public ParteroProfesional guardarOActualizar(ParteroProfesional parteroProfesional) {
        // Primero eliminar duplicados si existen
        eliminarDuplicados(parteroProfesional.getId_partero());
        
        // Verificar si existe un registro
        Optional<ParteroProfesional> existente = obtenerPorIdPartero(parteroProfesional.getId_partero());
        
        if (existente.isPresent()) {
            // Actualizar registro existente
            ParteroProfesional actual = existente.get();
            actualizarCampos(actual, parteroProfesional);
            verificarYActualizarEstadoGeneral(actual);
            return parteroProfesionalRepository.save(actual);
        } else {
            // Crear nuevo registro
            verificarYActualizarEstadoGeneral(parteroProfesional);
            return parteroProfesionalRepository.save(parteroProfesional);
        }
    }

    // Método auxiliar para actualizar campos de manera segura
    private void actualizarCampos(ParteroProfesional destino, ParteroProfesional fuente) {
        // Actualizar solo si el valor no es nulo en la fuente
        if (fuente.getTitulo_profesional() != null) {
            destino.setTitulo_profesional(fuente.getTitulo_profesional());
        }
        if (fuente.getCedula_profesional() != null) {
            destino.setCedula_profesional(fuente.getCedula_profesional());
        }
        if (fuente.getIne() != null) {
            destino.setIne(fuente.getIne());
        }
        if (fuente.getComprobante_domicilio() != null) {
            destino.setComprobante_domicilio(fuente.getComprobante_domicilio());
        }
        if (fuente.getFotografia() != null) {
            destino.setFotografia(fuente.getFotografia());
        }
        if (fuente.getCv() != null) {
            destino.setCv(fuente.getCv());
        }
        if (fuente.getReanimacion_neonatal() != null) {
            destino.setReanimacion_neonatal(fuente.getReanimacion_neonatal());
        }
        if (fuente.getReanimacion_adultos() != null) {
            destino.setReanimacion_adultos(fuente.getReanimacion_adultos());
        }
        if (fuente.getUrgencias_obstetricas() != null) {
            destino.setUrgencias_obstetricas(fuente.getUrgencias_obstetricas());
        }
        
        // Actualizar campos de enumeraciones solo si no son nulos
        if (fuente.getAreas_consulta_prenatal() != null) {
            destino.setAreas_consulta_prenatal(fuente.getAreas_consulta_prenatal());
        }
        if (fuente.getTriage_obstetrica() != null) {
            destino.setTriage_obstetrica(fuente.getTriage_obstetrica());
        }
        if (fuente.getSala_labor() != null) {
            destino.setSala_labor(fuente.getSala_labor());
        }
        if (fuente.getRecuperacion() != null) {
            destino.setRecuperacion(fuente.getRecuperacion());
        }
        if (fuente.getAtencion_parto() != null) {
            destino.setAtencion_parto(fuente.getAtencion_parto());
        }
        if (fuente.getHospitalizacion() != null) {
            destino.setHospitalizacion(fuente.getHospitalizacion());
        }
        if (fuente.getPlanificacion_familiar() != null) {
            destino.setPlanificacion_familiar(fuente.getPlanificacion_familiar());
        }
        if (fuente.getEducacion_perinatal() != null) {
            destino.setEducacion_perinatal(fuente.getEducacion_perinatal());
        }
        if (fuente.getEstimulacion_temprana() != null) {
            destino.setEstimulacion_temprana(fuente.getEstimulacion_temprana());
        }
        if (fuente.getAsociacion_perteneciente() != null) {
            destino.setAsociacion_perteneciente(fuente.getAsociacion_perteneciente());
        }
        
        // Campos de texto
        if (fuente.getAsociacion() != null) {
            destino.setAsociacion(fuente.getAsociacion());
        }
        if (fuente.getCargo() != null) {
            destino.setCargo(fuente.getCargo());
        }
        
        // Estados de documentos
        if (fuente.getEstado_titulo() != null) {
            destino.setEstado_titulo(fuente.getEstado_titulo());
        }
        if (fuente.getEstado_cedula() != null) {
            destino.setEstado_cedula(fuente.getEstado_cedula());
        }
        if (fuente.getEstado_ine() != null) {
            destino.setEstado_ine(fuente.getEstado_ine());
        }
        if (fuente.getEstado_comprobante() != null) {
            destino.setEstado_comprobante(fuente.getEstado_comprobante());
        }
        if (fuente.getEstado_fotografia() != null) {
            destino.setEstado_fotografia(fuente.getEstado_fotografia());
        }
        if (fuente.getEstado_cv() != null) {
            destino.setEstado_cv(fuente.getEstado_cv());
        }
        if (fuente.getEstado_reanimacion_neonatal() != null) {
            destino.setEstado_reanimacion_neonatal(fuente.getEstado_reanimacion_neonatal());
        }
        if (fuente.getEstado_reanimacion_adultos() != null) {
            destino.setEstado_reanimacion_adultos(fuente.getEstado_reanimacion_adultos());
        }
        if (fuente.getEstado_urgencias() != null) {
            destino.setEstado_urgencias(fuente.getEstado_urgencias());
        }
        
        // Estado general
        if (fuente.getEstado_general() != null) {
            destino.setEstado_general(fuente.getEstado_general());
        }
    }

    /**
     * Guarda un documento y persiste la información en la base de datos
     */
    public String guardarDocumentoYPersistir(MultipartFile archivo, Integer id_partero, String tipoDocumento) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            return null;
        }

        // Guardar el documento en el sistema de archivos
        String nombreArchivo = documentoService.guardarDocumento(archivo, id_partero, tipoDocumento);
        
        // Primero limpiar duplicados
        eliminarDuplicados(id_partero);
        
        // Obtener o crear el registro
        Optional<ParteroProfesional> optional = obtenerPorIdPartero(id_partero);
        ParteroProfesional parteroProfesional;
        
        if (optional.isPresent()) {
            parteroProfesional = optional.get();
        } else {
            parteroProfesional = new ParteroProfesional();
            parteroProfesional.setId_partero(id_partero);
        }
        
        // Actualizar el campo correspondiente según el tipo de documento
        actualizarCampoDocumento(parteroProfesional, nombreArchivo, tipoDocumento);
        
        // Verificar y actualizar estado general
        verificarYActualizarEstadoGeneral(parteroProfesional);
        
        // Guardar usando el método que previene duplicados
        guardarOActualizar(parteroProfesional);
        return nombreArchivo;
    }

    /**
     * Actualiza el campo de documento específico y su estado
     */
    private void actualizarCampoDocumento(ParteroProfesional parteroProfesional, String nombreArchivo, String tipoDocumento) {
        switch (tipoDocumento) {
            case "titulo":
                parteroProfesional.setTitulo_profesional(nombreArchivo);
                parteroProfesional.setEstado_titulo(ParteroProfesional.DocumentoEstado.pendiente);
                break;
            case "cedula":
                parteroProfesional.setCedula_profesional(nombreArchivo);
                parteroProfesional.setEstado_cedula(ParteroProfesional.DocumentoEstado.pendiente);
                break;
            case "ine":
                parteroProfesional.setIne(nombreArchivo);
                parteroProfesional.setEstado_ine(ParteroProfesional.DocumentoEstado.pendiente);
                break;
            case "comprobante":
                parteroProfesional.setComprobante_domicilio(nombreArchivo);
                parteroProfesional.setEstado_comprobante(ParteroProfesional.DocumentoEstado.pendiente);
                break;
            case "fotografia":
                parteroProfesional.setFotografia(nombreArchivo);
                parteroProfesional.setEstado_fotografia(ParteroProfesional.DocumentoEstado.pendiente);
                break;
            case "cv":
                parteroProfesional.setCv(nombreArchivo);
                parteroProfesional.setEstado_cv(ParteroProfesional.DocumentoEstado.pendiente);
                break;
            case "reanimacion_neonatal":
                parteroProfesional.setReanimacion_neonatal(nombreArchivo);
                parteroProfesional.setEstado_reanimacion_neonatal(ParteroProfesional.DocumentoEstado.pendiente);
                break;
            case "reanimacion_adultos":
                parteroProfesional.setReanimacion_adultos(nombreArchivo);
                parteroProfesional.setEstado_reanimacion_adultos(ParteroProfesional.DocumentoEstado.pendiente);
                break;
            case "urgencias_obstetricas":
                parteroProfesional.setUrgencias_obstetricas(nombreArchivo);
                parteroProfesional.setEstado_urgencias(ParteroProfesional.DocumentoEstado.pendiente);
                break;
        }
    }

    /**
     * Verifica si todos los documentos están completos y actualiza el estado general
     */
    private void verificarYActualizarEstadoGeneral(ParteroProfesional parteroProfesional) {
        boolean todosDocumentosSubidos = verificarTodosDocumentosSubidos(parteroProfesional);
        
        if (todosDocumentosSubidos) {
            // Si todos los documentos están subidos, cambiar estado general a "pendiente"
            parteroProfesional.setEstado_general(ParteroProfesional.Estado_General.pendiente);
        } else {
            // Si faltan documentos, establecer como "incompleto"
            parteroProfesional.setEstado_general(ParteroProfesional.Estado_General.incompleto);
        }
    }

    /**
     * Verificar si todos los documentos requeridos han sido subidos
     */
    private boolean verificarTodosDocumentosSubidos(ParteroProfesional parteroProfesional) {
        return parteroProfesional.getTitulo_profesional() != null && !parteroProfesional.getTitulo_profesional().isEmpty() &&
               parteroProfesional.getCedula_profesional() != null && !parteroProfesional.getCedula_profesional().isEmpty() &&
               parteroProfesional.getIne() != null && !parteroProfesional.getIne().isEmpty() &&
               parteroProfesional.getComprobante_domicilio() != null && !parteroProfesional.getComprobante_domicilio().isEmpty() &&
               parteroProfesional.getFotografia() != null && !parteroProfesional.getFotografia().isEmpty() &&
               parteroProfesional.getCv() != null && !parteroProfesional.getCv().isEmpty() &&
               parteroProfesional.getReanimacion_neonatal() != null && !parteroProfesional.getReanimacion_neonatal().isEmpty() &&
               parteroProfesional.getReanimacion_adultos() != null && !parteroProfesional.getReanimacion_adultos().isEmpty() &&
               parteroProfesional.getUrgencias_obstetricas() != null && !parteroProfesional.getUrgencias_obstetricas().isEmpty();
    }

    /**
     * Verifica si existe un registro para el id_partero
     */
    public boolean existeRegistro(Integer id_partero) {
        return parteroProfesionalRepository.existsByParteroId(id_partero);
    }

    /**
     * Crea un registro inicial para un partero
     */
    public ParteroProfesional crearRegistroInicial(Integer id_partero) {
        // Primero verificar que no exista ya un registro
        Optional<ParteroProfesional> existente = obtenerPorIdPartero(id_partero);
        if (existente.isPresent()) {
            return existente.get();
        }
        
        ParteroProfesional nuevo = new ParteroProfesional();
        nuevo.setId_partero(id_partero);
        nuevo.setEstado_general(ParteroProfesional.Estado_General.incompleto);
        
        // Inicializar estados de documentos como pendiente (serán "pendiente" cuando se suba el archivo)
        // Los dejamos como null por ahora
        
        return parteroProfesionalRepository.save(nuevo);
    }

    /**
     * Elimina un documento específico del sistema de archivos y de la base de datos
     */
    public boolean eliminarDocumento(Integer id_partero, String tipoDocumento) {
        try {
            Optional<ParteroProfesional> optional = obtenerPorIdPartero(id_partero);
            if (optional.isPresent()) {
                ParteroProfesional parteroProfesional = optional.get();
                String nombreArchivo = null;
                
                // Obtener el nombre del archivo según el tipo
                switch (tipoDocumento) {
                    case "titulo":
                        nombreArchivo = parteroProfesional.getTitulo_profesional();
                        parteroProfesional.setTitulo_profesional(null);
                        parteroProfesional.setEstado_titulo(null);
                        break;
                    case "cedula":
                        nombreArchivo = parteroProfesional.getCedula_profesional();
                        parteroProfesional.setCedula_profesional(null);
                        parteroProfesional.setEstado_cedula(null);
                        break;
                    case "ine":
                        nombreArchivo = parteroProfesional.getIne();
                        parteroProfesional.setIne(null);
                        parteroProfesional.setEstado_ine(null);
                        break;
                    case "comprobante":
                        nombreArchivo = parteroProfesional.getComprobante_domicilio();
                        parteroProfesional.setComprobante_domicilio(null);
                        parteroProfesional.setEstado_comprobante(null);
                        break;
                    case "fotografia":
                        nombreArchivo = parteroProfesional.getFotografia();
                        parteroProfesional.setFotografia(null);
                        parteroProfesional.setEstado_fotografia(null);
                        break;
                    case "cv":
                        nombreArchivo = parteroProfesional.getCv();
                        parteroProfesional.setCv(null);
                        parteroProfesional.setEstado_cv(null);
                        break;
                    case "reanimacion_neonatal":
                        nombreArchivo = parteroProfesional.getReanimacion_neonatal();
                        parteroProfesional.setReanimacion_neonatal(null);
                        parteroProfesional.setEstado_reanimacion_neonatal(null);
                        break;
                    case "reanimacion_adultos":
                        nombreArchivo = parteroProfesional.getReanimacion_adultos();
                        parteroProfesional.setReanimacion_adultos(null);
                        parteroProfesional.setEstado_reanimacion_adultos(null);
                        break;
                    case "urgencias_obstetricas":
                        nombreArchivo = parteroProfesional.getUrgencias_obstetricas();
                        parteroProfesional.setUrgencias_obstetricas(null);
                        parteroProfesional.setEstado_urgencias(null);
                        break;
                }
                
                // Si hay un archivo, eliminarlo del sistema de archivos
                if (nombreArchivo != null && !nombreArchivo.isEmpty()) {
                    documentoService.eliminarDocumento(nombreArchivo, id_partero);
                }
                
                // Actualizar estado general
                verificarYActualizarEstadoGeneral(parteroProfesional);
                
                // Guardar cambios
                guardarOActualizar(parteroProfesional);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}