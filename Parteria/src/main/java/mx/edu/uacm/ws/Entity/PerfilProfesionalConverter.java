package mx.edu.uacm.ws.Entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import mx.edu.uacm.ws.Entity.Partero.PerfilProfesional;

@Converter(autoApply = true)
public class PerfilProfesionalConverter implements AttributeConverter<PerfilProfesional, String> {
    
    @Override
    public String convertToDatabaseColumn(PerfilProfesional perfil) {
        if (perfil == null) {
            return null;
        }
        
        // Convertir el enum Java a String para la base de datos
        switch (perfil) {
            case Enfermeria_Perinatal:
                return "Enfermeria Perinatal";
            case Licenciatura_en_Enfermeria_y_Obstetrica:
                return "Licenciatura en Enfermeria y Obstetrica";
            case Licenciatura_en_Parteria:
                return "Licenciatura en Parteria";
            case Parteria_Tecnica:
                return "Parteria Tecnica";
            case Autonomo:
                return "Autonomo";
            case Otro:
                return "Otro";
            default:
                throw new IllegalArgumentException("Valor desconocido: " + perfil);
        }
    }
    
    @Override
    public PerfilProfesional convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        
        // Convertir el String de la base de datos a enum 
        switch (dbData) {
            case "Enfermeria Perinatal":
                return PerfilProfesional.Enfermeria_Perinatal;
            case "Licenciatura en Enfermeria y Obstetrica":
                return PerfilProfesional.Licenciatura_en_Enfermeria_y_Obstetrica;
            case "Licenciatura en Parteria":
                return PerfilProfesional.Licenciatura_en_Parteria;
            case "Parteria Tecnica":
                return PerfilProfesional.Parteria_Tecnica;
            case "Autonomo":
                return PerfilProfesional.Autonomo;
            case "Otro":
                return PerfilProfesional.Otro;
            default:
                throw new IllegalArgumentException("Valor desconocido: " + dbData);
        }
    }
}