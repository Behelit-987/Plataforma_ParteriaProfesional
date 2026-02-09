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
public class ParteroProfesional {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_PProfesional")
    private Integer id_PProfesional;
	
	@Column(name = "id_partero", nullable = false)
	private Integer id_partero;
    
    @Column(name = "titulo_profesional")
    private String titulo_profesional;
    
    @Column(name = "cedula_profesional")
    private String cedula_profesional;
    
    @Column(name = "INE")
    private String ine;
    
    @Column(name = "comprobante_domicilio")
    private String comprobante_domicilio;
    
    @Column(name = "fotografia")
    private String fotografia;
    
    @Column(name = "cv")
    private String cv;
    
    @Column(name = "reanimacion_neonatal")
    private String reanimacion_neonatal;
    
    @Column(name = "reanimacion_adultos")
    private String reanimacion_adultos;
    
    @Column(name = "urgencias_obstetricas")
    private String urgencias_obstetricas;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "areas_consulta_prenatal")
    private AreasConsultaPrenatal areas_consulta_prenatal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "triage_obstetrica")
    private TriageObstetrica triage_obstetrica;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "sala_labor")
    private SalaLabor sala_labor;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "recuperacion")
    private Recuperacion recuperacion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "atencion_parto")
    private AtencionParto atencion_parto;
    
    @Enumerated(EnumType.STRING)
    @Column(name  = "hospitalizacion")
    private Hospitalizacion hospitalizacion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "planificacion_familiar")
    private PlanificacionFamiliar planificacion_familiar;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "educacion_perinatal")
    private EducacionPerinatal educacion_perinatal;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estimulacion_temprana")
    private EstimulacionTemprana estimulacion_temprana;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "asociacion_perteneciente")
    private AsociacionPerteneciente asociacion_perteneciente;
    
    @Column(name = "asociacion")
    private String asociacion;
    
    @Column(name = "cargo")
    private String cargo;
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_titulo")
    private DocumentoEstado estado_titulo;
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_cedula")
    private DocumentoEstado estado_cedula;
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_ine")
    private DocumentoEstado estado_ine;
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_comprobante")
    private DocumentoEstado estado_comprobante;
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_fotografia")
    private DocumentoEstado estado_fotografia;
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_cv")
    private DocumentoEstado estado_cv;
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_reanimacion_neonatal")
    private DocumentoEstado estado_reanimacion_neonatal;
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_reanimacion_adultos")
    private DocumentoEstado estado_reanimacion_adultos;
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_urgencias")
    private DocumentoEstado estado_urgencias;
    
    @Enumerated(EnumType.STRING)
    @Column(name= "estado_general")
    private Estado_General estado_general;
    
    public enum DocumentoEstado{
    	pendiente, rechazado, aprobado
    }
    
    public enum Estado_General{
    	incompleto, pendiente, en_revision, aprobado, rechazado
    }
    
    public enum AreasConsultaPrenatal{
    	si, no
    }
    
    public enum TriageObstetrica{
    	si, no
    }
    
    public enum SalaLabor{
    	si, no
    }
    
    public enum Recuperacion{
    	si, no
    }
    
    public enum AtencionParto{
    	si, no
    }
    
    public enum Hospitalizacion{
    	si, no
    }
    
    public enum PlanificacionFamiliar{
    	si, no
    }
    
    public enum EducacionPerinatal{
    	si, no
    }
    
    public enum EstimulacionTemprana{
    	si, no
    }
    
    public enum AsociacionPerteneciente{
    	si, no
    }
    
    public ParteroProfesional(){}

	public ParteroProfesional(Integer id_PProfesional, Integer id_partero, String titulo_profesional,
			String cedula_profesional, String ine, String comprobante_domicilio, String fotografia, String cv,
			String reanimacion_neonatal, String reanimacion_adultos, String urgencias_obstetricas,
			AreasConsultaPrenatal areas_consulta_prenatal, TriageObstetrica triage_obstetrica, SalaLabor sala_labor,
			Recuperacion recuperacion, AtencionParto atencion_parto, Hospitalizacion hospitalizacion,
			PlanificacionFamiliar planificacion_familiar, EducacionPerinatal educacion_perinatal,
			EstimulacionTemprana estimulacion_temprana, AsociacionPerteneciente asociacion_perteneciente,
			String asociacion, String cargo, DocumentoEstado estado_titulo, DocumentoEstado estado_cedula,
			DocumentoEstado estado_ine, DocumentoEstado estado_comprobante, DocumentoEstado estado_fotografia,
			DocumentoEstado estado_cv, DocumentoEstado estado_reanimacion_neonatal,
			DocumentoEstado estado_reanimacion_adultos, DocumentoEstado estado_urgencias,
			Estado_General estado_general) {
		super();
		this.id_PProfesional = id_PProfesional;
		this.id_partero = id_partero;
		this.titulo_profesional = titulo_profesional;
		this.cedula_profesional = cedula_profesional;
		this.ine = ine;
		this.comprobante_domicilio = comprobante_domicilio;
		this.fotografia = fotografia;
		this.cv = cv;
		this.reanimacion_neonatal = reanimacion_neonatal;
		this.reanimacion_adultos = reanimacion_adultos;
		this.urgencias_obstetricas = urgencias_obstetricas;
		this.areas_consulta_prenatal = areas_consulta_prenatal;
		this.triage_obstetrica = triage_obstetrica;
		this.sala_labor = sala_labor;
		this.recuperacion = recuperacion;
		this.atencion_parto = atencion_parto;
		this.hospitalizacion = hospitalizacion;
		this.planificacion_familiar = planificacion_familiar;
		this.educacion_perinatal = educacion_perinatal;
		this.estimulacion_temprana = estimulacion_temprana;
		this.asociacion_perteneciente = asociacion_perteneciente;
		this.asociacion = asociacion;
		this.cargo = cargo;
		this.estado_titulo = estado_titulo;
		this.estado_cedula = estado_cedula;
		this.estado_ine = estado_ine;
		this.estado_comprobante = estado_comprobante;
		this.estado_fotografia = estado_fotografia;
		this.estado_cv = estado_cv;
		this.estado_reanimacion_neonatal = estado_reanimacion_neonatal;
		this.estado_reanimacion_adultos = estado_reanimacion_adultos;
		this.estado_urgencias = estado_urgencias;
		this.estado_general = estado_general;
	}



	public Integer getId_PProfesional() {
		return id_PProfesional;
	}

	public void setId_PProfesional(Integer id_PProfesional) {
		this.id_PProfesional = id_PProfesional;
	}

	public Integer getId_partero() {
		return id_partero;
	}

	public void setId_partero(Integer id_partero) {
		this.id_partero = id_partero;
	}

	public String getTitulo_profesional() {
		return titulo_profesional;
	}

	public void setTitulo_profesional(String titulo_profesional) {
		this.titulo_profesional = titulo_profesional;
	}

	public String getCedula_profesional() {
		return cedula_profesional;
	}

	public void setCedula_profesional(String cedula_profesional) {
		this.cedula_profesional = cedula_profesional;
	}


	public String getIne() {
		return ine;
	}

	public void setIne(String ine) {
		this.ine = ine;
	}

	public String getComprobante_domicilio() {
		return comprobante_domicilio;
	}

	public void setComprobante_domicilio(String comprobante_domicilio) {
		this.comprobante_domicilio = comprobante_domicilio;
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

	public String getReanimacion_neonatal() {
		return reanimacion_neonatal;
	}

	public void setReanimacion_neonatal(String reanimacion_neonatal) {
		this.reanimacion_neonatal = reanimacion_neonatal;
	}

	public String getReanimacion_adultos() {
		return reanimacion_adultos;
	}

	public void setReanimacion_adultos(String reanimacion_adultos) {
		this.reanimacion_adultos = reanimacion_adultos;
	}

	public String getUrgencias_obstetricas() {
		return urgencias_obstetricas;
	}

	public void setUrgencias_obstetricas(String urgencias_obstetricas) {
		this.urgencias_obstetricas = urgencias_obstetricas;
	}

	public AreasConsultaPrenatal getAreas_consulta_prenatal() {
		return areas_consulta_prenatal;
	}

	public void setAreas_consulta_prenatal(AreasConsultaPrenatal areas_consulta_prenatal) {
		this.areas_consulta_prenatal = areas_consulta_prenatal;
	}

	public TriageObstetrica getTriage_obstetrica() {
		return triage_obstetrica;
	}

	public void setTriage_obstetrica(TriageObstetrica triage_obstetrica) {
		this.triage_obstetrica = triage_obstetrica;
	}

	public SalaLabor getSala_labor() {
		return sala_labor;
	}

	public void setSala_labor(SalaLabor sala_labor) {
		this.sala_labor = sala_labor;
	}

	public Recuperacion getRecuperacion() {
		return recuperacion;
	}

	public void setRecuperacion(Recuperacion recuperacion) {
		this.recuperacion = recuperacion;
	}

	public AtencionParto getAtencion_parto() {
		return atencion_parto;
	}

	public void setAtencion_parto(AtencionParto atencion_parto) {
		this.atencion_parto = atencion_parto;
	}

	public Hospitalizacion getHospitalizacion() {
		return hospitalizacion;
	}

	public void setHospitalizacion(Hospitalizacion hospitalizacion) {
		this.hospitalizacion = hospitalizacion;
	}

	public PlanificacionFamiliar getPlanificacion_familiar() {
		return planificacion_familiar;
	}

	public void setPlanificacion_familiar(PlanificacionFamiliar planificacion_familiar) {
		this.planificacion_familiar = planificacion_familiar;
	}

	public EducacionPerinatal getEducacion_perinatal() {
		return educacion_perinatal;
	}

	public void setEducacion_perinatal(EducacionPerinatal educacion_perinatal) {
		this.educacion_perinatal = educacion_perinatal;
	}

	public EstimulacionTemprana getEstimulacion_temprana() {
		return estimulacion_temprana;
	}

	public void setEstimulacion_temprana(EstimulacionTemprana estimulacion_temprana) {
		this.estimulacion_temprana = estimulacion_temprana;
	}

	public AsociacionPerteneciente getAsociacion_perteneciente() {
		return asociacion_perteneciente;
	}

	public void setAsociacion_perteneciente(AsociacionPerteneciente asociacion_perteneciente) {
		this.asociacion_perteneciente = asociacion_perteneciente;
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

	public DocumentoEstado getEstado_titulo() {
		return estado_titulo;
	}

	public void setEstado_titulo(DocumentoEstado estado_titulo) {
		this.estado_titulo = estado_titulo;
	}

	public DocumentoEstado getEstado_cedula() {
		return estado_cedula;
	}

	public void setEstado_cedula(DocumentoEstado estado_cedula) {
		this.estado_cedula = estado_cedula;
	}

	public DocumentoEstado getEstado_ine() {
		return estado_ine;
	}

	public void setEstado_ine(DocumentoEstado estado_ine) {
		this.estado_ine = estado_ine;
	}

	public DocumentoEstado getEstado_comprobante() {
		return estado_comprobante;
	}

	public void setEstado_comprobante(DocumentoEstado estado_comprobante) {
		this.estado_comprobante = estado_comprobante;
	}

	public DocumentoEstado getEstado_fotografia() {
		return estado_fotografia;
	}

	public void setEstado_fotografia(DocumentoEstado estado_fotografia) {
		this.estado_fotografia = estado_fotografia;
	}

	public DocumentoEstado getEstado_cv() {
		return estado_cv;
	}

	public void setEstado_cv(DocumentoEstado estado_cv) {
		this.estado_cv = estado_cv;
	}

	public DocumentoEstado getEstado_reanimacion_neonatal() {
		return estado_reanimacion_neonatal;
	}

	public void setEstado_reanimacion_neonatal(DocumentoEstado estado_reanimacion_neonatal) {
		this.estado_reanimacion_neonatal = estado_reanimacion_neonatal;
	}

	public DocumentoEstado getEstado_reanimacion_adultos() {
		return estado_reanimacion_adultos;
	}

	public void setEstado_reanimacion_adultos(DocumentoEstado estado_reanimacion_adultos) {
		this.estado_reanimacion_adultos = estado_reanimacion_adultos;
	}

	public DocumentoEstado getEstado_urgencias() {
		return estado_urgencias;
	}

	public void setEstado_urgencias(DocumentoEstado estado_urgencias) {
		this.estado_urgencias = estado_urgencias;
	}

	public Estado_General getEstado_general() {
		return estado_general;
	}

	public void setEstado_general(Estado_General estado_general) {
		this.estado_general = estado_general;
	}
    
}

