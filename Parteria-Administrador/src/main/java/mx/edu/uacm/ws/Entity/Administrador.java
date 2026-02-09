package mx.edu.uacm.ws.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrador")
public class Administrador {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "administrador_id")
    private Integer administrador_id;
    
    @Column(name = "nombres", nullable = false)
    private String nombres;
    
    @Column(name = "apellidos", nullable = false)
    private String apellidos;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column (name = "password_hash", nullable = false)
    private String password_hash;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    public Administrador() {}

	public Administrador(Integer administrador_id, String nombres, String apellidos, String email, String password_hash,
			LocalDateTime fechaRegistro) {
		super();
		this.administrador_id = administrador_id;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.email = email;
		this.password_hash = password_hash;
		this.fechaRegistro = fechaRegistro;
	}

	public Integer getAdministrador_id() {
		return administrador_id;
	}

	public void setAdministrador_id(Integer administrador_id) {
		this.administrador_id = administrador_id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword_hash() {
		return password_hash;
	}

	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
    
    
}
