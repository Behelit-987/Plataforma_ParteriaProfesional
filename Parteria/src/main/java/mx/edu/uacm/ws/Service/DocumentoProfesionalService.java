// src/main/java/mx/edu/uacm/ws/Service/DocumentoService.java
package mx.edu.uacm.ws.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class DocumentoProfesionalService {

    @Value("${app.upload.dir:C:/uploads/parteria}")
    private String uploadDir;

    public String guardarDocumento(MultipartFile archivo, Integer idPartero, String tipoDocumento) throws IOException {
        // Crear subcarpeta para el partero
        String subcarpeta = "partero_" + idPartero;
        Path directorio = Paths.get(uploadDir, subcarpeta);
        
        if (!Files.exists(directorio)) {
            Files.createDirectories(directorio);
        }

        // Generar nombre único para el archivo
        String nombreOriginal = archivo.getOriginalFilename();
        String extension = "";
        if (nombreOriginal != null && nombreOriginal.contains(".")) {
            extension = nombreOriginal.substring(nombreOriginal.lastIndexOf("."));
        }
        String nombreArchivo = tipoDocumento + "_" + UUID.randomUUID().toString() + extension;

        // Guardar archivo
        Path rutaArchivo = directorio.resolve(nombreArchivo);
        Files.copy(archivo.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

        // Devolver solo el nombre del archivo (sin ruta completa)
        return nombreArchivo;
    }

    public boolean eliminarDocumento(String nombreArchivo, Integer idPartero) {
        try {
            String subcarpeta = "partero_" + idPartero;
            Path rutaArchivo = Paths.get(uploadDir, subcarpeta, nombreArchivo);
            return Files.deleteIfExists(rutaArchivo);
        } catch (IOException e) {
            return false;
        }
    }

    public Path obtenerRutaCompleta(String nombreArchivo, Integer idPartero) {
        String subcarpeta = "partero_" + idPartero;
        return Paths.get(uploadDir, subcarpeta, nombreArchivo);
    }
}