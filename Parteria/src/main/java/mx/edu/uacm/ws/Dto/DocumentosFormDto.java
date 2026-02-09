package mx.edu.uacm.ws.Dto;

import org.springframework.web.multipart.MultipartFile;

public class DocumentosFormDto {
	private MultipartFile titulo_profesional;
    private MultipartFile cedula_profesional;
    private MultipartFile ine;
    private MultipartFile comprobante_domicilio;
    private MultipartFile fotografia;
    private MultipartFile cv;
    private MultipartFile reanimacion_neonatal;
    private MultipartFile reanimacion_adultos;
    private MultipartFile urgencias_obstetricas;
    
    // Campos booleanos
    private String areas_consulta_prenatal;
    private String triage_obstetrica;
    private String sala_labor;
    private String recuperacion;
    private String atencion_parto;
    private String hospitalizacion;
    private String planificacion_familiar;
    private String educacion_perinatal;
    private String estimulacion_temprana;
    private String asociacion_perteneciente;
    
    private String asociacion;
    private String cargo;
    
    public DocumentosFormDto() {}

	public DocumentosFormDto(MultipartFile titulo_profesional, MultipartFile cedula_profesional, MultipartFile ine,
			MultipartFile comprobante_domicilio, MultipartFile fotografia, MultipartFile cv,
			MultipartFile reanimacion_neonatal, MultipartFile reanimacion_adultos, MultipartFile urgencias_obstetricas,
			String areas_consulta_prenatal, String triage_obstetrica, String sala_labor, String recuperacion,
			String atencion_parto, String hospitalizacion, String planificacion_familiar, String educacion_perinatal,
			String estimulacion_temprana, String asociacion_perteneciente, String asociacion, String cargo) {
		super();
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
	}

	public MultipartFile getTitulo_profesional() {
		return titulo_profesional;
	}

	public void setTitulo_profesional(MultipartFile titulo_profesional) {
		this.titulo_profesional = titulo_profesional;
	}

	public MultipartFile getCedula_profesional() {
		return cedula_profesional;
	}

	public void setCedula_profesional(MultipartFile cedula_profesional) {
		this.cedula_profesional = cedula_profesional;
	}

	public MultipartFile getIne() {
		return ine;
	}

	public void setIne(MultipartFile ine) {
		this.ine = ine;
	}

	public MultipartFile getComprobante_domicilio() {
		return comprobante_domicilio;
	}

	public void setComprobante_domicilio(MultipartFile comprobante_domicilio) {
		this.comprobante_domicilio = comprobante_domicilio;
	}

	public MultipartFile getFotografia() {
		return fotografia;
	}

	public void setFotografia(MultipartFile fotografia) {
		this.fotografia = fotografia;
	}

	public MultipartFile getCv() {
		return cv;
	}

	public void setCv(MultipartFile cv) {
		this.cv = cv;
	}

	public MultipartFile getReanimacion_neonatal() {
		return reanimacion_neonatal;
	}

	public void setReanimacion_neonatal(MultipartFile reanimacion_neonatal) {
		this.reanimacion_neonatal = reanimacion_neonatal;
	}

	public MultipartFile getReanimacion_adultos() {
		return reanimacion_adultos;
	}

	public void setReanimacion_adultos(MultipartFile reanimacion_adultos) {
		this.reanimacion_adultos = reanimacion_adultos;
	}

	public MultipartFile getUrgencias_obstetricas() {
		return urgencias_obstetricas;
	}

	public void setUrgencias_obstetricas(MultipartFile urgencias_obstetricas) {
		this.urgencias_obstetricas = urgencias_obstetricas;
	}

	public String getAreas_consulta_prenatal() {
		return areas_consulta_prenatal;
	}

	public void setAreas_consulta_prenatal(String areas_consulta_prenatal) {
		this.areas_consulta_prenatal = areas_consulta_prenatal;
	}

	public String getTriage_obstetrica() {
		return triage_obstetrica;
	}

	public void setTriage_obstetrica(String triage_obstetrica) {
		this.triage_obstetrica = triage_obstetrica;
	}

	public String getSala_labor() {
		return sala_labor;
	}

	public void setSala_labor(String sala_labor) {
		this.sala_labor = sala_labor;
	}

	public String getRecuperacion() {
		return recuperacion;
	}

	public void setRecuperacion(String recuperacion) {
		this.recuperacion = recuperacion;
	}

	public String getAtencion_parto() {
		return atencion_parto;
	}

	public void setAtencion_parto(String atencion_parto) {
		this.atencion_parto = atencion_parto;
	}

	public String getHospitalizacion() {
		return hospitalizacion;
	}

	public void setHospitalizacion(String hospitalizacion) {
		this.hospitalizacion = hospitalizacion;
	}

	public String getPlanificacion_familiar() {
		return planificacion_familiar;
	}

	public void setPlanificacion_familiar(String planificacion_familiar) {
		this.planificacion_familiar = planificacion_familiar;
	}

	public String getEducacion_perinatal() {
		return educacion_perinatal;
	}

	public void setEducacion_perinatal(String educacion_perinatal) {
		this.educacion_perinatal = educacion_perinatal;
	}

	public String getEstimulacion_temprana() {
		return estimulacion_temprana;
	}

	public void setEstimulacion_temprana(String estimulacion_temprana) {
		this.estimulacion_temprana = estimulacion_temprana;
	}

	public String getAsociacion_perteneciente() {
		return asociacion_perteneciente;
	}

	public void setAsociacion_perteneciente(String asociacion_perteneciente) {
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

}
