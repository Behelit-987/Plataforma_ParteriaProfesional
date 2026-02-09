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
@Table(name = "certificacionesAdultasProfesionales")
public class ReanimacionAdulta {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Adultas")
    private Integer idAdultas;
	
	@Column(name = "id_partero", nullable = false)
	private Integer idPartero;
    
	@Column(name = "capacitacion_adultas")
	private String capacitacionAdultas;
	
	@Enumerated(EnumType.STRING)
    @Column(name= "estado_adultas")
    private EstadoAdultas estadoAdultas;
	
	public enum EstadoAdultas{
		pendiente, rechazado, aprobado
	}
	
	public ReanimacionAdulta() {}

	public ReanimacionAdulta(Integer idAdultas, Integer idPartero, String capacitacionAdultas,
			EstadoAdultas estadoAdultas) {
		super();
		this.idAdultas = idAdultas;
		this.idPartero = idPartero;
		this.capacitacionAdultas = capacitacionAdultas;
		this.estadoAdultas = estadoAdultas;
	}

	public Integer getIdAdultas() {
		return idAdultas;
	}

	public void setIdAdultas(Integer idAdultas) {
		this.idAdultas = idAdultas;
	}

	public Integer getIdPartero() {
		return idPartero;
	}

	public void setIdPartero(Integer idPartero) {
		this.idPartero = idPartero;
	}

	public String getCapacitacionAdultas() {
		return capacitacionAdultas;
	}

	public void setCapacitacionAdultas(String capacitacionAdultas) {
		this.capacitacionAdultas = capacitacionAdultas;
	}

	public EstadoAdultas getEstadoAdultas() {
		return estadoAdultas;
	}

	public void setEstadoAdultas(EstadoAdultas estadoAdultas) {
		this.estadoAdultas = estadoAdultas;
	}

	@Override
	public String toString() {
		return "ReanimacionAdulta [idAdultas=" + idAdultas + ", idPartero=" + idPartero + ", capacitacionAdultas="
				+ capacitacionAdultas + ", estadoAdultas=" + estadoAdultas + "]";
	}
	
	
}
