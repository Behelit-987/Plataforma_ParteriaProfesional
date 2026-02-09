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
@Table(name = "certificacionesObstetricasProfesionales")
public class ReanimacionObstetrica {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Obstetricas")
    private Integer idAObstetricas;
	
	@Column(name = "id_partero", nullable = false)
	private Integer idPartero;
    
	@Column(name = "capacitacion_obstetricas")
	private String capacitacionObstetricas;
	
	@Enumerated(EnumType.STRING)
    @Column(name= "estado_obstetricas")
	private EstadoObstetrico estadoObstetrico;
	
	public enum EstadoObstetrico{
		pendiente, rechazado, aprobado
	}
	
	public ReanimacionObstetrica() {}

	public ReanimacionObstetrica(Integer idAObstetricas, Integer idPartero, String capacitacionObstetricas,
			EstadoObstetrico estadoObstetrico) {
		super();
		this.idAObstetricas = idAObstetricas;
		this.idPartero = idPartero;
		this.capacitacionObstetricas = capacitacionObstetricas;
		this.estadoObstetrico = estadoObstetrico;
	}

	public Integer getIdAObstetricas() {
		return idAObstetricas;
	}

	public void setIdAObstetricas(Integer idAObstetricas) {
		this.idAObstetricas = idAObstetricas;
	}

	public Integer getIdPartero() {
		return idPartero;
	}

	public void setIdPartero(Integer idPartero) {
		this.idPartero = idPartero;
	}

	public String getCapacitacionObstetricas() {
		return capacitacionObstetricas;
	}

	public void setCapacitacionObstetricas(String capacitacionObstetricas) {
		this.capacitacionObstetricas = capacitacionObstetricas;
	}

	public EstadoObstetrico getEstadoObstetrico() {
		return estadoObstetrico;
	}

	public void setEstadoObstetrico(EstadoObstetrico estadoObstetrico) {
		this.estadoObstetrico = estadoObstetrico;
	}
	
	
	
}
