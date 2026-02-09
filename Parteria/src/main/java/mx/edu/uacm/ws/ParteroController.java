package mx.edu.uacm.ws;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.transaction.Transactional;
import mx.edu.uacm.ws.Dto.DocumentosFormDto;
import mx.edu.uacm.ws.Entity.Partero;
import mx.edu.uacm.ws.Entity.ParteroProfesional;
import mx.edu.uacm.ws.Service.ParteroProfesionalService;
import mx.edu.uacm.ws.Service.ParteroService;

@Controller
public class ParteroController {

    @Autowired
    private ParteroService parteroService;
    
    @Autowired
    private ParteroProfesionalService parteroProfesionalService;

    @GetMapping("/")
    public String home() {
        // Redirigir directamente al login
        return "redirect:/login-parteria";
    }

    @GetMapping("/inicio")
    public String inicio(Authentication authentication) {
        // Esta es la ruta a la que redirige después del login exitoso
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Partero partero = parteroService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            // Redirigir según el perfil profesional
            return determinarPlataformaPorPerfil(partero.getPerfil_profesional());
        }
        // Si no está autenticado, redirigir al login
        return "redirect:/login-parteria";
    }

    @GetMapping("/plataforma-parteria-profesional")
    public String plataformaProfesional(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Partero partero = parteroService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            model.addAttribute("partero", partero);
            
            String nombreCompleto = partero.getNombres() + " " + partero.getApellidos();
            model.addAttribute("nombreUsuario", nombreCompleto.trim());
        }
        return "plataforma-parteria-profesional/index";
    }

    @GetMapping("/plataforma-parteria-tecnica")
    public String plataformaTecnica(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Partero partero = parteroService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            model.addAttribute("partero", partero);
        }
        return "plataforma-parteria-tecnica/index";
    }

    @GetMapping("/plataforma-parteria-no-profesional")
    public String plataformaNoProfesional(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Partero partero = parteroService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            model.addAttribute("partero", partero);
        }
        return "plataforma-parteria-no-profesional/index";
    }

    @GetMapping("/login-parteria")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       @RequestParam(value = "registro", required = false) String registro,
                       Model model) {
        if (error != null) {
            model.addAttribute("error", "Correo o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("message", "Sesión cerrada exitosamente");
        }
        if (registro != null) {
            model.addAttribute("success", "Registro exitoso. Ahora puedes iniciar sesión.");
        }
        return "login/index";
    }

    private String determinarPlataformaPorPerfil(Partero.PerfilProfesional perfil) {
        if (perfil == null) {
            return "redirect:/plataforma-parteria-no-profesional";
        }
        
        switch (perfil) {
            case Enfermeria_Perinatal:
            case Licenciatura_en_Enfermeria_y_Obstetrica:
            //case Licenciatura_en_Parteria:
                return "redirect:/plataforma-parteria-profesional";
                
            case Parteria_Tecnica:
                return "redirect:/plataforma-parteria-tecnica";
                
            case Autonomo:
            case Otro:
            default:
                return "redirect:/plataforma-parteria-no-profesional";
        }
    }

    // Mostrar formulario de registro
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("partero", new Partero());
        return "login/registro-partero";
    }

    // Procesar registro
    @PostMapping("/registrar")
    public String registrarPartero(@ModelAttribute Partero partero, 
                                  RedirectAttributes redirectAttributes) {
        try {
        	
            if (partero.getNombres() == null || partero.getNombres().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El nombre es obligatorio");
                return "redirect:/registro";
            }
            
            if (parteroService.existsEmail(partero.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "El correo electrónico ya está registrado");
                return "redirect:/registro";
            }

            parteroService.registrarPartero(partero);
            redirectAttributes.addFlashAttribute("success", 
                "Registro exitoso. Ahora puedes iniciar sesión.");
            return "redirect:/login-parteria?registro=true";
            
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/registro";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error en el registro: " + e.getMessage());
            return "redirect:/registro";
        }
    }

    @GetMapping("/actualizar-contrasena")
    public String mostrarActualizarContrasena() {
        return "login/update-password";
    }

    @PostMapping("/actualizar-password")
    public String actualizarPassword(@RequestParam String email,
                                    @RequestParam String passwordNow,
                                    @RequestParam String passwordNew,
                                    RedirectAttributes redirectAttributes) {
        try {
            if (email == null || email.trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El correo electrónico es obligatorio");
                return "redirect:/actualizar-contrasena";
            }

            boolean actualizado = parteroService.actualizarPassword(email, passwordNow, passwordNew);
            if (actualizado) {
                redirectAttributes.addFlashAttribute("success", "Contraseña actualizada exitosamente");
                return "redirect:/login-parteria";
            } else {
                redirectAttributes.addFlashAttribute("error", 
                    "Error al actualizar la contraseña. Verifica tu correo y contraseña actual.");
                return "redirect:/actualizar-contrasena";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
            return "redirect:/actualizar-contrasena";
        }
    }
    
    //Parte de subir archivos
    @GetMapping("/plataforma-parteria-profesional/subir-documentos")
    public String mostrarSubirDocumentos(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Partero partero = parteroService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            // Verificar si ya existe un registro de documentos
            Optional<ParteroProfesional> documentos = 
                parteroProfesionalService.obtenerPorIdPartero(partero.getId_partero());
            
            ParteroProfesional documentosObj = documentos.orElse(new ParteroProfesional());
            
            model.addAttribute("partero", partero);
            model.addAttribute("documentos", documentosObj);
            model.addAttribute("documentosForm", new DocumentosFormDto());
        }
        return "plataforma-parteria-profesional/subir-documentos-profesional";
    }

    /*
    @PostMapping("/plataforma-parteria-profesional/subir-documentos")
    public String procesarDocumentos(@ModelAttribute DocumentosFormDto documentosForm,
                                    Authentication authentication,
                                    RedirectAttributes redirectAttributes) {
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                String email = authentication.getName();
                Partero partero = parteroService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
                Integer idPartero = partero.getId_partero();
                
                // Obtener o crear registro existente
                Optional<ParteroProfesional> optional = parteroProfesionalService.obtenerPorIdPartero(idPartero);
                ParteroProfesional parteroProfesional = optional.orElseGet(() -> {
                    ParteroProfesional nuevo = new ParteroProfesional();
                    nuevo.setId_partero(idPartero);
                    return nuevo;
                });
                
                // Procesar archivos individualmente
                procesarArchivoIndividual(documentosForm.getTitulo_profesional(), "titulo", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getCedula_profesional(), "cedula", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getIne(), "ine", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getComprobante_domicilio(), "comprobante", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getFotografia(), "fotografia", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getCv(), "cv", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getReanimacion_neonatal(), "reanimacion_neonatal", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getReanimacion_adultos(), "reanimacion_adultos", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getUrgencias_obstetricas(), "urgencias_obstetricas", idPartero, parteroProfesional);
                
                // Actualizar campos booleanos y de texto
                actualizarCamposNoArchivos(documentosForm, parteroProfesional);
                
                // Guardar cambios
                parteroProfesionalService.guardarOActualizar(parteroProfesional);
                
                redirectAttributes.addFlashAttribute("success", "Documentos guardados exitosamente");
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar documentos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "redirect:/plataforma-parteria-profesional/subir-documentos";
    }
    */
    
    // Aqui
    
    @PostMapping("/plataforma-parteria-profesional/subir-documentos")
    public String procesarDocumentos(@ModelAttribute DocumentosFormDto documentosForm,
                                    Authentication authentication,
                                    RedirectAttributes redirectAttributes) {
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                String email = authentication.getName();
                Partero partero = parteroService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
                Integer idPartero = partero.getId_partero();
                
                // Obtener o crear registro existente (usando el método actualizado)
                Optional<ParteroProfesional> optional = parteroProfesionalService.obtenerPorIdPartero(idPartero);
                ParteroProfesional parteroProfesional;
                
                if (optional.isPresent()) {
                    parteroProfesional = optional.get();
                } else {
                    parteroProfesional = new ParteroProfesional();
                    parteroProfesional.setId_partero(idPartero);
                    parteroProfesional.setEstado_general(ParteroProfesional.Estado_General.incompleto);
                }
                
                // Procesar archivos individualmente
                procesarArchivoIndividual(documentosForm.getTitulo_profesional(), "titulo", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getCedula_profesional(), "cedula", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getIne(), "ine", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getComprobante_domicilio(), "comprobante", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getFotografia(), "fotografia", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getCv(), "cv", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getReanimacion_neonatal(), "reanimacion_neonatal", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getReanimacion_adultos(), "reanimacion_adultos", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getUrgencias_obstetricas(), "urgencias_obstetricas", idPartero, parteroProfesional);
                
                // Actualizar campos booleanos y de texto
                actualizarCamposNoArchivos(documentosForm, parteroProfesional);
                
                // Guardar cambios usando el método que previene duplicados
                parteroProfesionalService.guardarOActualizar(parteroProfesional);
                
                // Verificar si todos los documentos están completos para mostrar mensaje específico
                boolean todosCompletos = verificarTodosDocumentosCompletos(parteroProfesional);
                
                if (todosCompletos) {
                    redirectAttributes.addFlashAttribute("success", 
                        "¡Todos los documentos han sido subidos! Tu solicitud está ahora en estado PENDIENTE de revisión.");
                } else {
                    redirectAttributes.addFlashAttribute("success", "Documentos guardados exitosamente");
                }
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar documentos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "redirect:/plataforma-parteria-profesional/subir-documentos";
    }
    
    /*
    @PostMapping("/plataforma-parteria-profesional/subir-documentos")
    public String procesarDocumentos(@ModelAttribute DocumentosFormDto documentosForm,
                                    Authentication authentication,
                                    RedirectAttributes redirectAttributes) {
        try {
            if (authentication != null && authentication.isAuthenticated()) {
                String email = authentication.getName();
                Partero partero = parteroService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
                Integer idPartero = partero.getId_partero();
                
                // Obtener o crear registro existente
                Optional<ParteroProfesional> optional = parteroProfesionalService.obtenerPorIdPartero(idPartero);
                ParteroProfesional parteroProfesional = optional.orElseGet(() -> {
                    ParteroProfesional nuevo = new ParteroProfesional();
                    nuevo.setId_partero(idPartero);
                    nuevo.setEstado_general(ParteroProfesional.Estado_General.incompleto);
                    return nuevo;
                });
                
                // Procesar archivos individualmente
                procesarArchivoIndividual(documentosForm.getTitulo_profesional(), "titulo", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getCedula_profesional(), "cedula", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getIne(), "ine", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getComprobante_domicilio(), "comprobante", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getFotografia(), "fotografia", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getCv(), "cv", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getReanimacion_neonatal(), "reanimacion_neonatal", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getReanimacion_adultos(), "reanimacion_adultos", idPartero, parteroProfesional);
                procesarArchivoIndividual(documentosForm.getUrgencias_obstetricas(), "urgencias_obstetricas", idPartero, parteroProfesional);
                
                // Actualizar campos booleanos y de texto
                actualizarCamposNoArchivos(documentosForm, parteroProfesional);
                
                // Guardar cambios - El estado general se actualiza automáticamente en el service
                parteroProfesionalService.guardarOActualizar(parteroProfesional);
                
                // Verificar si todos los documentos están completos para mostrar mensaje específico
                boolean todosCompletos = verificarTodosDocumentosCompletos(parteroProfesional);
                
                if (todosCompletos) {
                    redirectAttributes.addFlashAttribute("success", 
                        "¡Todos los documentos han sido subidos! Tu solicitud está ahora en estado PENDIENTE de revisión.");
                } else {
                    redirectAttributes.addFlashAttribute("success", "Documentos guardados exitosamente");
                }
            }
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar documentos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return "redirect:/plataforma-parteria-profesional/subir-documentos";
    }
    */

    // Método auxiliar para verificar si todos los documentos están completos
    private boolean verificarTodosDocumentosCompletos(ParteroProfesional parteroProfesional) {
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
    // Aqui chingue su madre el que nacio desnudo >:)
    
    @GetMapping("/plataforma-parteria-profesional/modificar-documentos")
    public String mostrarModificarDocumentos(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Partero partero = parteroService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            // Obtener documentos existentes
            Optional<ParteroProfesional> documentos = 
                parteroProfesionalService.obtenerPorIdPartero(partero.getId_partero());
            
            if (!documentos.isPresent()) {
                return "redirect:/plataforma-parteria-profesional/subir-documentos";
            }
            
            ParteroProfesional documentosObj = documentos.get();
            
            model.addAttribute("partero", partero);
            model.addAttribute("documentos", documentosObj);
            model.addAttribute("documentosForm", new DocumentosFormDto());
            
            String nombreCompleto = partero.getNombres() + " " + partero.getApellidos();
            model.addAttribute("nombreUsuario", nombreCompleto.trim());
        }
        return "plataforma-parteria-profesional/modificar-documentos";
    }

    @GetMapping("/plataforma-parteria-profesional/estatus-documentos")
    public String mostrarEstatusDocumentos(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Partero partero = parteroService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            Optional<ParteroProfesional> documentos = 
                parteroProfesionalService.obtenerPorIdPartero(partero.getId_partero());
            
            ParteroProfesional documentosObj = documentos.orElse(new ParteroProfesional());
            
            model.addAttribute("partero", partero);
            model.addAttribute("documentos", documentosObj);
            
            String nombreCompleto = partero.getNombres() + " " + partero.getApellidos();
            model.addAttribute("nombreUsuario", nombreCompleto.trim());
            
            calcularProgresoDocumentos(model, documentosObj);
        }
        return "plataforma-parteria-profesional/estatus-documentos";
    }

    private void procesarArchivoIndividual(MultipartFile archivo, String tipo, Integer idPartero, ParteroProfesional parteroProfesional) {
        if (archivo != null && !archivo.isEmpty()) {
            try {
                parteroProfesionalService.guardarDocumentoYPersistir(archivo, idPartero, tipo);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar el archivo: " + tipo, e);
            }
        }
    }
    
    @GetMapping("/limpiar-duplicados")
    @Transactional
    public String limpiarDuplicados(Authentication authentication, RedirectAttributes redirectAttributes) {
        if (authentication != null && authentication.isAuthenticated()) {
            try {
                String email = authentication.getName();
                Partero partero = parteroService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
                // Obtener todos los registros para diagnóstico
                List<ParteroProfesional> registros = parteroProfesionalService.obtenerTodosPorIdPartero(partero.getId_partero());
                
                if (registros.size() > 1) {
                    // Limpiar duplicados
                    parteroProfesionalService.eliminarDuplicados(partero.getId_partero());
                    redirectAttributes.addFlashAttribute("success", 
                        "Se eliminaron " + (registros.size() - 1) + " registros duplicados. Quedó 1 registro.");
                } else {
                    redirectAttributes.addFlashAttribute("info", "No se encontraron registros duplicados.");
                }
                
                // Mostrar información de diagnóstico
                redirectAttributes.addFlashAttribute("diagnostico", 
                    "Total registros encontrados: " + registros.size());
                    
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error al limpiar duplicados: " + e.getMessage());
            }
        }
        return "redirect:/plataforma-parteria-profesional/subir-documentos";
    }

    private void actualizarCamposNoArchivos(DocumentosFormDto form, ParteroProfesional parteroProfesional) {
        // Actualizar campos booleanos con valores por defecto "no"
        parteroProfesional.setAreas_consulta_prenatal(
            form.getAreas_consulta_prenatal() != null && !form.getAreas_consulta_prenatal().isEmpty() 
                ? ParteroProfesional.AreasConsultaPrenatal.valueOf(form.getAreas_consulta_prenatal())
                : ParteroProfesional.AreasConsultaPrenatal.no
        );
        
        parteroProfesional.setTriage_obstetrica(
            form.getTriage_obstetrica() != null && !form.getTriage_obstetrica().isEmpty() 
                ? ParteroProfesional.TriageObstetrica.valueOf(form.getTriage_obstetrica())
                : ParteroProfesional.TriageObstetrica.no
        );
        
        parteroProfesional.setSala_labor(
            form.getSala_labor() != null && !form.getSala_labor().isEmpty() 
                ? ParteroProfesional.SalaLabor.valueOf(form.getSala_labor())
                : ParteroProfesional.SalaLabor.no
        );
        
        parteroProfesional.setRecuperacion(
            form.getRecuperacion() != null && !form.getRecuperacion().isEmpty() 
                ? ParteroProfesional.Recuperacion.valueOf(form.getRecuperacion())
                : ParteroProfesional.Recuperacion.no
        );
        
        parteroProfesional.setAtencion_parto(
            form.getAtencion_parto() != null && !form.getAtencion_parto().isEmpty() 
                ? ParteroProfesional.AtencionParto.valueOf(form.getAtencion_parto())
                : ParteroProfesional.AtencionParto.no
        );
        
        parteroProfesional.setHospitalizacion(
            form.getHospitalizacion() != null && !form.getHospitalizacion().isEmpty() 
                ? ParteroProfesional.Hospitalizacion.valueOf(form.getHospitalizacion())
                : ParteroProfesional.Hospitalizacion.no
        );
        
        parteroProfesional.setPlanificacion_familiar(
            form.getPlanificacion_familiar() != null && !form.getPlanificacion_familiar().isEmpty() 
                ? ParteroProfesional.PlanificacionFamiliar.valueOf(form.getPlanificacion_familiar())
                : ParteroProfesional.PlanificacionFamiliar.no
        );
        
        parteroProfesional.setEducacion_perinatal(
            form.getEducacion_perinatal() != null && !form.getEducacion_perinatal().isEmpty() 
                ? ParteroProfesional.EducacionPerinatal.valueOf(form.getEducacion_perinatal())
                : ParteroProfesional.EducacionPerinatal.no
        );
        
        parteroProfesional.setEstimulacion_temprana(
            form.getEstimulacion_temprana() != null && !form.getEstimulacion_temprana().isEmpty() 
                ? ParteroProfesional.EstimulacionTemprana.valueOf(form.getEstimulacion_temprana())
                : ParteroProfesional.EstimulacionTemprana.no
        );
        
        // Asociacion_perteneciente puede ser null (no es requerido)
        if (form.getAsociacion_perteneciente() != null && !form.getAsociacion_perteneciente().isEmpty()) {
            parteroProfesional.setAsociacion_perteneciente(
                ParteroProfesional.AsociacionPerteneciente.valueOf(form.getAsociacion_perteneciente())
            );
        }
        
        // Actualizar campos de texto
        if (form.getAsociacion() != null) {
            parteroProfesional.setAsociacion(form.getAsociacion());
        }
        if (form.getCargo() != null) {
            parteroProfesional.setCargo(form.getCargo());
        }
    }
    
    private void calcularProgresoDocumentos(Model model, ParteroProfesional documentos) {
        int totalDocumentos = 9; // Total de documentos que se pueden subir
        int documentosSubidos = 0;
        
        if (documentos.getTitulo_profesional() != null && !documentos.getTitulo_profesional().isEmpty()) documentosSubidos++;
        if (documentos.getCedula_profesional() != null && !documentos.getCedula_profesional().isEmpty()) documentosSubidos++;
        if (documentos.getIne() != null && !documentos.getIne().isEmpty()) documentosSubidos++;
        if (documentos.getComprobante_domicilio() != null && !documentos.getComprobante_domicilio().isEmpty()) documentosSubidos++;
        if (documentos.getFotografia() != null && !documentos.getFotografia().isEmpty()) documentosSubidos++;
        if (documentos.getCv() != null && !documentos.getCv().isEmpty()) documentosSubidos++;
        if (documentos.getReanimacion_neonatal() != null && !documentos.getReanimacion_neonatal().isEmpty()) documentosSubidos++;
        if (documentos.getReanimacion_adultos() != null && !documentos.getReanimacion_adultos().isEmpty()) documentosSubidos++;
        if (documentos.getUrgencias_obstetricas() != null && !documentos.getUrgencias_obstetricas().isEmpty()) documentosSubidos++;
        
        double progreso = (double) documentosSubidos / totalDocumentos * 100;
        model.addAttribute("progreso", Math.round(progreso));
        model.addAttribute("documentosSubidos", documentosSubidos);
        model.addAttribute("totalDocumentos", totalDocumentos);
    }
}
