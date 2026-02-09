package mx.edu.uacm.ws.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Partero_Profesionales")
public class DocumentosProfesionales {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_PProfesional")
    private Integer idPProfesional;  // Cambiado: id_PProfesional → idPProfesional
    
    @Column(name = "id_partero", nullable = false)
    private Integer idPartero;  // Cambiado: id_partero → idPartero
    
    @Column(name = "titulo_profesional")
    private String tituloProfesional;  // Cambiado: titulo_profesional → tituloProfesional
    
    @Column(name = "cedula_profesional")
    private String cedulaProfesional;  // Cambiado: cedula_profesional → cedulaProfesional
    
    @Column(name = "INE")
    private String ine;  // OK - ya está en camelCase
    
    @Column(name = "comprobante_domicilio")
    private String comprobanteDomicilio;  // Cambiado: comprobante_domicilio → comprobanteDomicilio
    
    @Column(name = "fotografia")
    private String fotografia;  // OK - ya está en camelCase
    
    @Column(name = "cv")
    private String cv;  // OK - ya está en camelCase
    
    @Column(name = "reanimacion_neonatal")
    private String reanimacionNeonatal;  // Cambiado: reanimacion_neonatal → reanimacionNeonatal
    
    @Column(name = "reanimacion_adultos")
    private String reanimacionAdultos;  // Cambiado: reanimacion_adultos → reanimacionAdultos
    
    @Column(name = "urgencias_obstetricas")
    private String urgenciasObstetricas;  // Cambiado: urgencias_obstetricas → urgenciasObstetricas
    
    @Enumerated(EnumType.STRING)
    @Column(name = "areas_consulta_prenatal")
    private Respuesta areasConsultaPrenatal;  // Cambiado: areas_consulta_prenatal → areasConsultaPrenatal
    
    @Enumerated(EnumType.STRING)
    @Column(name = "triage_obstetrica")
    private Respuesta triageObstetrica;  // Cambiado: triage_obstetrica → triageObstetrica
    
    @Enumerated(EnumType.STRING)
    @Column(name = "sala_labor")
    private Respuesta salaLabor;  // Cambiado: sala_labor → salaLabor
    
    @Enumerated(EnumType.STRING)
    @Column(name = "recuperacion")
    private Respuesta recuperacion;  // OK - ya está en camelCase
    
    @Enumerated(EnumType.STRING)
    @Column(name = "atencion_parto")
    private Respuesta atencionParto;  // Cambiado: atencion_parto → atencionParto
    
    @Enumerated(EnumType.STRING)
    @Column(name  = "hospitalizacion")
    private Respuesta hospitalizacion;  // OK - ya está en camelCase
    
    @Enumerated(EnumType.STRING)
    @Column(name = "planificacion_familiar")
    private Respuesta planificacionFamiliar;  // Cambiado: planificacion_familiar → planificacionFamiliar
    
    @Enumerated(EnumType.STRING)
    @Column(name = "educacion_perinatal")
    private Respuesta educacionPerinatal;  // Cambiado: educacion_perinatal → educacionPerinatal
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estimulacion_temprana")
    private Respuesta estimulacionTemprana;  // Cambiado: estimulacion_temprana → estimulacionTemprana
    
    @Enumerated(EnumType.STRING)
    @Column(name = "asociacion_perteneciente")
    private Respuesta asociacionPerteneciente;  // Cambiado: asociacion_perteneciente → asociacionPerteneciente
    
    @Column(name = "asociacion")
    private String asociacion;  // OK - ya está en camelCase
    
    @Column(name = "cargo")
    private String cargo;  // OK - ya está en camelCase
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_titulo")
    private DocumentoEstado estadoTitulo;  // Cambiado: estado_titulo → estadoTitulo
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_cedula")
    private DocumentoEstado estadoCedula;  // Cambiado: estado_cedula → estadoCedula
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_ine")
    private DocumentoEstado estadoIne;  // Cambiado: estado_ine → estadoIne
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_comprobante")
    private DocumentoEstado estadoComprobante;  // Cambiado: estado_comprobante → estadoComprobante
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_fotografia")
    private DocumentoEstado estadoFotografia;  // Cambiado: estado_fotografia → estadoFotografia
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_cv")
    private DocumentoEstado estadoCv;  // Cambiado: estado_cv → estadoCv
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_reanimacion_neonatal")
    private DocumentoEstado estadoReanimacionNeonatal;  // Cambiado: estado_reanimacion_neonatal → estadoReanimacionNeonatal
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_reanimacion_adultos")
    private DocumentoEstado estadoReanimacionAdultos;  // Cambiado: estado_reanimacion_adultos → estadoReanimacionAdultos
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_urgencias")
    private DocumentoEstado estadoUrgencias;  // Cambiado: estado_urgencias → estadoUrgencias
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_general")
    private EstadoGeneral estadoGeneral;  // Cambiado: estado_general → estadoGeneral
    
    public enum DocumentoEstado {
        pendiente, rechazado, aprobado
    }
    
    public enum EstadoGeneral {
        incompleto, pendiente, en_revision, aprobado, rechazado
    }
    
    public enum Respuesta {
        si, no
    }
    
    // Constructores
    public DocumentosProfesionales() {}

    // Constructor completo (ajusta según los cambios)
    public DocumentosProfesionales(Integer idPProfesional, Integer idPartero, String tituloProfesional,
            String cedulaProfesional, String ine, String comprobanteDomicilio, String fotografia, String cv,
            String reanimacionNeonatal, String reanimacionAdultos, String urgenciasObstetricas,
            Respuesta areasConsultaPrenatal, Respuesta triageObstetrica, Respuesta salaLabor,
            Respuesta recuperacion, Respuesta atencionParto, Respuesta hospitalizacion,
            Respuesta planificacionFamiliar, Respuesta educacionPerinatal, Respuesta estimulacionTemprana,
            Respuesta asociacionPerteneciente, String asociacion, String cargo, DocumentoEstado estadoTitulo,
            DocumentoEstado estadoCedula, DocumentoEstado estadoIne, DocumentoEstado estadoComprobante,
            DocumentoEstado estadoFotografia, DocumentoEstado estadoCv, DocumentoEstado estadoReanimacionNeonatal,
            DocumentoEstado estadoReanimacionAdultos, DocumentoEstado estadoUrgencias,
            EstadoGeneral estadoGeneral) {
        super();
        this.idPProfesional = idPProfesional;
        this.idPartero = idPartero;
        this.tituloProfesional = tituloProfesional; 
        this.cedulaProfesional = cedulaProfesional;
        this.ine = ine;
        this.comprobanteDomicilio = comprobanteDomicilio;
        this.fotografia = fotografia;
        this.cv = cv;
        this.reanimacionNeonatal = reanimacionNeonatal;
        this.reanimacionAdultos = reanimacionAdultos;
        this.urgenciasObstetricas = urgenciasObstetricas;
        this.areasConsultaPrenatal = areasConsultaPrenatal;
        this.triageObstetrica = triageObstetrica;
        this.salaLabor = salaLabor;
        this.recuperacion = recuperacion;
        this.atencionParto = atencionParto;
        this.hospitalizacion = hospitalizacion;
        this.planificacionFamiliar = planificacionFamiliar;
        this.educacionPerinatal = educacionPerinatal;
        this.estimulacionTemprana = estimulacionTemprana;
        this.asociacionPerteneciente = asociacionPerteneciente;
        this.asociacion = asociacion;
        this.cargo = cargo;
        this.estadoTitulo = estadoTitulo;
        this.estadoCedula = estadoCedula;
        this.estadoIne = estadoIne;
        this.estadoComprobante = estadoComprobante;
        this.estadoFotografia = estadoFotografia;
        this.estadoCv = estadoCv;
        this.estadoReanimacionNeonatal = estadoReanimacionNeonatal;
        this.estadoReanimacionAdultos = estadoReanimacionAdultos;
        this.estadoUrgencias = estadoUrgencias;
        this.estadoGeneral = estadoGeneral;
    }

    // Getters y Setters
    public Integer getIdPProfesional() {
        return idPProfesional;
    }

    public void setIdPProfesional(Integer idPProfesional) {
        this.idPProfesional = idPProfesional;
    }

    public Integer getIdPartero() {
        return idPartero;
    }

    public void setIdPartero(Integer idPartero) {
        this.idPartero = idPartero;
    }

    public String getTituloProfesional() {
        return tituloProfesional;
    }

    public void setTituloProfesional(String tituloProfesional) {
        this.tituloProfesional = tituloProfesional;
    }

    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    public String getIne() {
        return ine;
    }

    public void setIne(String ine) {
        this.ine = ine;
    }

    public String getComprobanteDomicilio() {
        return comprobanteDomicilio;
    }

    public void setComprobanteDomicilio(String comprobanteDomicilio) {
        this.comprobanteDomicilio = comprobanteDomicilio;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getReanimacionNeonatal() {
        return reanimacionNeonatal;
    }

    public void setReanimacionNeonatal(String reanimacionNeonatal) {
        this.reanimacionNeonatal = reanimacionNeonatal;
    }

    public String getReanimacionAdultos() {
        return reanimacionAdultos;
    }

    public void setReanimacionAdultos(String reanimacionAdultos) {
        this.reanimacionAdultos = reanimacionAdultos;
    }

    public String getUrgenciasObstetricas() {
        return urgenciasObstetricas;
    }

    public void setUrgenciasObstetricas(String urgenciasObstetricas) {
        this.urgenciasObstetricas = urgenciasObstetricas;
    }

    public Respuesta getAreasConsultaPrenatal() {
        return areasConsultaPrenatal;
    }

    public void setAreasConsultaPrenatal(Respuesta areasConsultaPrenatal) {
        this.areasConsultaPrenatal = areasConsultaPrenatal;
    }

    public Respuesta getTriageObstetrica() {
        return triageObstetrica;
    }

    public void setTriageObstetrica(Respuesta triageObstetrica) {
        this.triageObstetrica = triageObstetrica;
    }

    public Respuesta getSalaLabor() {
        return salaLabor;
    }

    public void setSalaLabor(Respuesta salaLabor) {
        this.salaLabor = salaLabor;
    }

    public Respuesta getRecuperacion() {
        return recuperacion;
    }

    public void setRecuperacion(Respuesta recuperacion) {
        this.recuperacion = recuperacion;
    }

    public Respuesta getAtencionParto() {
        return atencionParto;
    }

    public void setAtencionParto(Respuesta atencionParto) {
        this.atencionParto = atencionParto;
    }

    public Respuesta getHospitalizacion() {
        return hospitalizacion;
    }

    public void setHospitalizacion(Respuesta hospitalizacion) {
        this.hospitalizacion = hospitalizacion;
    }

    public Respuesta getPlanificacionFamiliar() {
        return planificacionFamiliar;
    }

    public void setPlanificacionFamiliar(Respuesta planificacionFamiliar) {
        this.planificacionFamiliar = planificacionFamiliar;
    }

    public Respuesta getEducacionPerinatal() {
        return educacionPerinatal;
    }

    public void setEducacionPerinatal(Respuesta educacionPerinatal) {
        this.educacionPerinatal = educacionPerinatal;
    }

    public Respuesta getEstimulacionTemprana() {
        return estimulacionTemprana;
    }

    public void setEstimulacionTemprana(Respuesta estimulacionTemprana) {
        this.estimulacionTemprana = estimulacionTemprana;
    }

    public Respuesta getAsociacionPerteneciente() {
        return asociacionPerteneciente;
    }

    public void setAsociacionPerteneciente(Respuesta asociacionPerteneciente) {
        this.asociacionPerteneciente = asociacionPerteneciente;
    }

    public String getAsociacion() {
        return asociacion;
    }

    public void setAsociacion(String asociacion) {
        this.asociacion = asociacion;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public DocumentoEstado getEstadoTitulo() {
        return estadoTitulo;
    }

    public void setEstadoTitulo(DocumentoEstado estadoTitulo) {
        this.estadoTitulo = estadoTitulo;
    }

    public DocumentoEstado getEstadoCedula() {
        return estadoCedula;
    }

    public void setEstadoCedula(DocumentoEstado estadoCedula) {
        this.estadoCedula = estadoCedula;
    }

    public DocumentoEstado getEstadoIne() {
        return estadoIne;
    }

    public void setEstadoIne(DocumentoEstado estadoIne) {
        this.estadoIne = estadoIne;
    }

    public DocumentoEstado getEstadoComprobante() {
        return estadoComprobante;
    }

    public void setEstadoComprobante(DocumentoEstado estadoComprobante) {
        this.estadoComprobante = estadoComprobante;
    }

    public DocumentoEstado getEstadoFotografia() {
        return estadoFotografia;
    }

    public void setEstadoFotografia(DocumentoEstado estadoFotografia) {
        this.estadoFotografia = estadoFotografia;
    }

    public DocumentoEstado getEstadoCv() {
        return estadoCv;
    }

    public void setEstadoCv(DocumentoEstado estadoCv) {
        this.estadoCv = estadoCv;
    }

    public DocumentoEstado getEstadoReanimacionNeonatal() {
        return estadoReanimacionNeonatal;
    }

    public void setEstadoReanimacionNeonatal(DocumentoEstado estadoReanimacionNeonatal) {
        this.estadoReanimacionNeonatal = estadoReanimacionNeonatal;
    }

    public DocumentoEstado getEstadoReanimacionAdultos() {
        return estadoReanimacionAdultos;
    }

    public void setEstadoReanimacionAdultos(DocumentoEstado estadoReanimacionAdultos) {
        this.estadoReanimacionAdultos = estadoReanimacionAdultos;
    }

    public DocumentoEstado getEstadoUrgencias() {
        return estadoUrgencias;
    }

    public void setEstadoUrgencias(DocumentoEstado estadoUrgencias) {
        this.estadoUrgencias = estadoUrgencias;
    }

    public EstadoGeneral getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(EstadoGeneral estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }
}