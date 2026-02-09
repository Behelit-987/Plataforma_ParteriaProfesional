package mx.edu.uacm.ws;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.annotation.PostConstruct;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import mx.edu.uacm.ws.Entity.Administrador;
import mx.edu.uacm.ws.Entity.DocumentosProfesionales;
import mx.edu.uacm.ws.Service.AdministradorService;
import mx.edu.uacm.ws.Service.DocumentosProfesionalesService;

@Controller
public class AdministradorController {
    
    @Autowired
    private AdministradorService administradorService;
    
    @Autowired
    private DocumentosProfesionalesService documentosProfesionalesService;
    
    private static final String UPLOAD_DIR = "C:/uploads/parteria";
    private final Path uploadPath = Paths.get(UPLOAD_DIR);
    
    @GetMapping("/")
    public String home() {
        return "redirect:/login-administrador-partero";
    }

    @GetMapping("/inicio-administrador")
    public String inicioAdministrador(Authentication authentication) {
        // Esta es la ruta a la que redirige después del login exitoso
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Administrador administrador = administradorService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
            
            // Redirigir al dashboard del administrador
            return "redirect:/administrador/dashboard";
        }
        // Si no está autenticado, redirigir al login
        return "redirect:/login-administrador-partero";
    }

    @GetMapping("/login-administrador-partero")
    public String loginAdministrador(@RequestParam(value = "error", required = false) String error,
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
        return "log-administrador/index";
    }

    @GetMapping("/administrador/dashboard")
    public String dashboardAdministrador(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Administrador administrador = administradorService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
            
            String nombreCompleto = administrador.getNombres() + " " + administrador.getApellidos();
            model.addAttribute("nombreUsuario", nombreCompleto.trim());
            model.addAttribute("administrador", administrador);
        }
        return "administrador/index";
    }

    @GetMapping("/login-administrador/registro")
    public String mostrarRegistroAdministrador(Model model) {
        model.addAttribute("administrador", new Administrador());
        return "log-administrador/registro-admins";
    }

    @PostMapping("/login-administrador/registrar")
    public String registrarAdministrador(@ModelAttribute Administrador administrador,
                                        @RequestParam("confirmPassword") String confirmPassword,
                                        RedirectAttributes redirectAttributes) {
        try {
            // Validaciones básicas
            if (administrador.getNombres() == null || administrador.getNombres().trim().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "El nombre es obligatorio");
                return "redirect:/login-administrador/registro";
            }
            
            if (administradorService.existsEmail(administrador.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "El correo electrónico ya está registrado");
                return "redirect:/login-administrador/registro";
            }

            if (!administrador.getPassword_hash().equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden");
                return "redirect:/login-administrador/registro";
            }

            administradorService.registrarAdministrador(administrador);
            redirectAttributes.addFlashAttribute("success", 
                "Administrador registrado exitosamente. Ahora puede iniciar sesión.");
            return "redirect:/login-administrador-partero?registro=true";
            
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/login-administrador/registro";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error en el registro: " + e.getMessage());
            return "redirect:/login-administrador/registro";
        }
    }
    
    @GetMapping("/administrador/documentos")
    public String listarDocumentos(Model model, Authentication authentication) {
    	if(authentication != null && authentication.isAuthenticated()) {
    		String email = authentication.getName();
    		Administrador administrador = administradorService.findByEmail(email).orElseThrow(() -> 
    			new RuntimeException("Administrador no encontrado"));
    		
    		String nombreCompleto = administrador.getNombres() + "" + administrador.getApellidos();
    		model.addAttribute("nombreUsuario", nombreCompleto.trim());
    		List<DocumentosProfesionales> documentos = documentosProfesionalesService.obtenerDocumentosPorRevisar();
    		model.addAttribute("documentos", documentos);
    		model.addAttribute("documentosPendientes", documentosProfesionalesService.contarDocumentosPendientes());
    		model.addAttribute("documentosAprobados", documentosProfesionalesService.contarDocumentosAprobados());
    		model.addAttribute("documentosRechazados", documentosProfesionalesService.contarDocumentosRechazados());
    	}
    	return "administrador/documentos";
    }
    
    /*
    @GetMapping("/administrador/documentos/{id}")
    public String verDocumento(@PathVariable Integer id, Model model, Authentication authentication) {
    	if(authentication != null && authentication.isAuthenticated()) {
    		String email = authentication.getName();
    		Administrador administrador = administradorService.findByEmail(email)
    				.orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
    		
    		String nombreCompleto = administrador.getNombres() + " " + administrador.getApellidos();
    		model.addAttribute("nombreUsuario", nombreCompleto.trim());
    		DocumentosProfesionales documento = documentosProfesionalesService.obtenerDocumentoPorId(id);
    		model.addAttribute("documento", documento);
    		model.addAttribute("estadoDocumentos", DocumentosProfesionales.DocumentoEstado.values());
    		model.addAttribute("estadosGenerales", DocumentosProfesionales.EstadoGeneral.values());
    	}
    	
    	return "redirect:/administrador/documentos";
    }
    */
    
    /*
    @PostMapping("/administrador/documentos/{id}/validar")
    public String validarDocumentos(@PathVariable Integer id, @RequestParam DocumentosProfesionales.DocumentoEstado estadoTitulo,
	        @RequestParam DocumentosProfesionales.DocumentoEstado estadoCedula,
	        @RequestParam DocumentosProfesionales.DocumentoEstado estadoIne,
	        @RequestParam DocumentosProfesionales.DocumentoEstado estadoComprobante,
	        @RequestParam DocumentosProfesionales.DocumentoEstado estadoFotografia,
	        @RequestParam DocumentosProfesionales.DocumentoEstado estadoCv,
	        @RequestParam DocumentosProfesionales.DocumentoEstado estadoReanimacionNeonatal,
	        @RequestParam DocumentosProfesionales.DocumentoEstado estadoReanimacionAdultos,
	        @RequestParam DocumentosProfesionales.DocumentoEstado estadoUrgencias,
	        @RequestParam DocumentosProfesionales.EstadoGeneral estadoGeneral,
	        RedirectAttributes redirectAttributes, Authentication authentication) {
    	
    	if(authentication != null && authentication.isAuthenticated()) {
    		try {
    			documentosProfesionalesService.actualizarEstadoDocumento(id, estadoTitulo, estadoCedula, estadoIne, estadoComprobante, 
    					estadoFotografia, estadoCv, estadoReanimacionNeonatal, estadoReanimacionAdultos, 
    					estadoUrgencias, estadoGeneral, null);
    			redirectAttributes.addFlashAttribute("success", "Documento actualizado correctamente");
    		} catch(Exception e) {
    			redirectAttributes.addFlashAttribute("error", "Error al actualizar el documento: " + e.getMessage());
    		}
    	}
    	
    	return "redirect:/administrador/documentos";
    }
    */
    
    @GetMapping("/administrador/documentos/{id}")
    public String verDocumento(@PathVariable Integer id, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Administrador administrador = administradorService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
            
            String nombreCompleto = administrador.getNombres() + " " + administrador.getApellidos();
            model.addAttribute("nombreUsuario", nombreCompleto.trim());
            
            DocumentosProfesionales documento = documentosProfesionalesService.obtenerDocumentoPorId(id);
            model.addAttribute("documento", documento);
            
            model.addAttribute("estadoDocumentos", DocumentosProfesionales.DocumentoEstado.values());
            model.addAttribute("estadosGenerales", DocumentosProfesionales.EstadoGeneral.values());
            
            return "administrador/ver-documento";
        }
        
        return "redirect:/login-administrador-partero";
    }
    
    @PostMapping("/administrador/documentos/{id}/validar")
    public String validarDocumento(@PathVariable Integer id,
            @RequestParam(required = false) DocumentosProfesionales.DocumentoEstado estadoTitulo,
            @RequestParam(required = false) DocumentosProfesionales.DocumentoEstado estadoCedula,
            @RequestParam(required = false) DocumentosProfesionales.DocumentoEstado estadoIne,
            @RequestParam(required = false) DocumentosProfesionales.DocumentoEstado estadoComprobante,
            @RequestParam(required = false) DocumentosProfesionales.DocumentoEstado estadoFotografia,
            @RequestParam(required = false) DocumentosProfesionales.DocumentoEstado estadoCv,
            @RequestParam(required = false) DocumentosProfesionales.DocumentoEstado estadoReanimacionNeonatal,
            @RequestParam(required = false) DocumentosProfesionales.DocumentoEstado estadoReanimacionAdultos,
            @RequestParam(required = false) DocumentosProfesionales.DocumentoEstado estadoUrgencias,
            @RequestParam DocumentosProfesionales.EstadoGeneral estadoGeneral,
            @RequestParam(required = false) String observaciones,
            RedirectAttributes redirectAttributes, Authentication authentication) {
        
        if (authentication != null && authentication.isAuthenticated()) {
            try {
                documentosProfesionalesService.actualizarEstadoDocumento(id, 
                        estadoTitulo, estadoCedula, estadoIne, estadoComprobante, 
                        estadoFotografia, estadoCv, estadoReanimacionNeonatal, 
                        estadoReanimacionAdultos, estadoUrgencias, 
                        estadoGeneral, observaciones);
                
                redirectAttributes.addFlashAttribute("success", "Documento validado correctamente");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error al validar el documento: " + e.getMessage());
            }
        }
        
        return "redirect:/administrador/documentos";
    }
    
    // Método auxiliar para obtener la ruta correcta del archivo
    private Path getFilePath(String filename, Integer parteroId) {
        // Construir la ruta: C:/uploads/parteria/partero_{id}/filename
        Path parteroDir = Paths.get(UPLOAD_DIR, "partero_" + parteroId);
        return parteroDir.resolve(filename).normalize();
    }
    
    @GetMapping("/api/documentos/view/{parteroId}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> viewDocument(@PathVariable Integer parteroId, 
                                                 @PathVariable String filename) {
        try {
            // Construir la ruta: C:/uploads/parteria/partero_{id}/filename
            Path filePath = Paths.get(UPLOAD_DIR, "partero_" + parteroId, filename).normalize();
            System.out.println("Visualizando archivo en: " + filePath.toAbsolutePath());
            
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists() && resource.isReadable()) {
                String contentType = determineContentType(filename);
                System.out.println("Archivo encontrado para visualización: " + filename + " - Tipo: " + contentType);
                
                // Para imágenes y PDFs, mostrarlos en el navegador
                if (contentType.startsWith("image/") || contentType.equals("application/pdf")) {
                    return ResponseEntity.ok()
                            .contentType(MediaType.parseMediaType(contentType))
                            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                            .body(resource);
                } else {
                    // Para otros tipos, ofrecer descarga
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .body(resource);
                }
            } else {
                System.err.println("Archivo NO encontrado en: " + filePath.toAbsolutePath());
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Error al visualizar archivo " + filename + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    // OPCIÓN: Mantener endpoint de descarga por si acaso (opcional)
    @GetMapping("/api/documentos/download/{parteroId}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadDocument(@PathVariable Integer parteroId, 
                                                     @PathVariable String filename) {
        try {
            Path filePath = Paths.get(UPLOAD_DIR, "partero_" + parteroId, filename).normalize();
            System.out.println("Descargando archivo de: " + filePath.toAbsolutePath());
            
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                System.err.println("Archivo NO encontrado para descarga: " + filePath.toAbsolutePath());
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Error al descargar archivo " + filename + ": " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    private String determineContentType(String filename) {
        String contentType = "application/octet-stream";
        try {
            String lowerFilename = filename.toLowerCase();
            
            // Imágenes
            if (lowerFilename.endsWith(".jpg") || lowerFilename.endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else if (lowerFilename.endsWith(".png")) {
                contentType = "image/png";
            } else if (lowerFilename.endsWith(".gif")) {
                contentType = "image/gif";
            } else if (lowerFilename.endsWith(".bmp")) {
                contentType = "image/bmp";
            } else if (lowerFilename.endsWith(".webp")) {
                contentType = "image/webp";
            } 
            // PDF
            else if (lowerFilename.endsWith(".pdf")) {
                contentType = "application/pdf";
            }
            // Documentos de texto
            else if (lowerFilename.endsWith(".txt")) {
                contentType = "text/plain";
            } else if (lowerFilename.endsWith(".html") || lowerFilename.endsWith(".htm")) {
                contentType = "text/html";
            }
            // Documentos de Office
            else if (lowerFilename.endsWith(".doc")) {
                contentType = "application/msword";
            } else if (lowerFilename.endsWith(".docx")) {
                contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            } else if (lowerFilename.endsWith(".xls")) {
                contentType = "application/vnd.ms-excel";
            } else if (lowerFilename.endsWith(".xlsx")) {
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            }
        } catch (Exception e) {
            // Usar el tipo por defecto
        }
        return contentType;
    }
    
    @PostConstruct
    public void init() {
        try {
            // Verificar que el directorio base existe
            Path basePath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(basePath)) {
                Files.createDirectories(basePath);
                System.out.println("Directorio base creado: " + basePath.toAbsolutePath());
            } else {
                System.out.println("Directorio base ya existe: " + basePath.toAbsolutePath());
                
                // Listar carpetas de parteros para debug
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(basePath, "partero_*")) {
                    System.out.println("Carpetas de parteros encontradas:");
                    for (Path folder : stream) {
                        if (Files.isDirectory(folder)) {
                            System.out.println("  - " + folder.getFileName());
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Error al listar carpetas: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al crear/verificar directorio: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // Este es para el ver que documentos tiene
    
    @GetMapping("/administrador/documentos/todos")
    public String verTodosDocumentos(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            Administrador administrador = administradorService.findByEmail(email)
            		.orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
            
            String nombreCompleto = administrador.getNombres() + " " + administrador.getApellidos();
            model.addAttribute("nombreUsuario", nombreCompleto.trim());
            
            List<DocumentosProfesionales> todosDocumentos = documentosProfesionalesService.obtenerTodosDocumentos();
            model.addAttribute("documentos", todosDocumentos);
            model.addAttribute("mostrarTodos", true);
        }
        return "administrador/documentos";
    }
    
    
}