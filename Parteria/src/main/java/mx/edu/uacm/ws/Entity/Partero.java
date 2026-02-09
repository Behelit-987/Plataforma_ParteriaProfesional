package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_partero")
public class Partero {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_partero")
    private Integer id_partero;
    
    @Column(name = "nombres", nullable = false)
    private String nombres;
    
    @Column(name = "apellidos", nullable = false)
    private String apellidos;
    
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fecha_nacimiento;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private Sexo sexo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "nacionalidad", nullable = false)
    private Nacionalidad nacionalidad;
    
    @Column(name = "pais_origen")
    private String pais_origen;
    
    @Column(name = "doc_pais")
    private String doc_pais;
    
    @Column(name = "pais_nut")
    private Integer pais_nut;
    
    @Column(name = "fech_vencimiento")
    private LocalDate fech_vencimiento;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "perfil_profesional")
    private PerfilProfesional perfil_profesional;
    
    @Column(name = "profesion")
    private String profesion;
    
    @Column(name = "telefono", nullable = false)
    private String telefono;
    
    @Column(name = "curp", nullable = false, unique = true)
    private String curp;

	@Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    public enum Sexo {
        hombre, mujer
    }
    
    public enum Nacionalidad {
        si, no
    }
    
    public enum PerfilProfesional {
        Enfermeria_Perinatal, 
        Licenciatura_en_Enfermeria_y_Obstetrica,
        Licenciatura_en_Parteria, 
        Parteria_Tecnica, 
        Autonomo, 
        Otro
    }
    
    public Partero() {}

	public Partero(Integer id_partero, String nombres, String apellidos, LocalDate fecha_nacimiento, Sexo sexo,
			Nacionalidad nacionalidad, String pais_origen, String doc_pais, Integer pais_nut,
			LocalDate fech_vencimiento, PerfilProfesional perfil_profesional, String profesion, String telefono,
			String curp, String email, String passwordHash, LocalDateTime fechaRegistro) {
		super();
		this.id_partero = id_partero;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.fecha_nacimiento = fecha_nacimiento;
		this.sexo = sexo;
		this.nacionalidad = nacionalidad;
		this.pais_origen = pais_origen;
		this.doc_pais = doc_pais;
		this.pais_nut = pais_nut;
		this.fech_vencimiento = fech_vencimiento;
		this.perfil_profesional = perfil_profesional;
		this.profesion = profesion;
		this.telefono = telefono;
		this.curp = curp;
		this.email = email;
		this.passwordHash = passwordHash;
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getId_partero() {
		return id_partero;
	}

	public void setId_partero(Integer id_partero) {
		this.id_partero = id_partero;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public Nacionalidad getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(Nacionalidad nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getPais_origen() {
		return pais_origen;
	}

	public void setPais_origen(String pais_origen) {
		this.pais_origen = pais_origen;
	}

	public String getDoc_pais() {
		return doc_pais;
	}

	public void setDoc_pais(String doc_pais) {
		this.doc_pais = doc_pais;
	}

	public Integer getPais_nut() {
		return pais_nut;
	}

	public void setPais_nut(Integer pais_nut) {
		this.pais_nut = pais_nut;
	}

	public LocalDate getFech_vencimiento() {
		return fech_vencimiento;
	}

	public void setFech_vencimiento(LocalDate fech_vencimiento) {
		this.fech_vencimiento = fech_vencimiento;
	}

	public PerfilProfesional getPerfil_profesional() {
		return perfil_profesional;
	}

	public void setPerfil_profesional(PerfilProfesional perfil_profesional) {
		this.perfil_profesional = perfil_profesional;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
    
    
}
