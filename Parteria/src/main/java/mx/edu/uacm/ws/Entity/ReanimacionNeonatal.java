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
@Table(name = "certificacionesNeonatalesProfesionales")
public class ReanimacionNeonatal {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_neonatales")
    private Integer idNeonatales;
	
	@Column(name = "id_partero", nullable = false)
	private Integer idPartero;
    
	@Column(name = "capacitacion_neonatal")
	private String capacitacionNeonatal;
	
	@Enumerated(EnumType.STRING)
    @Column(name= "estado_neonatal")
    private EstadoNeonatal estadoNeonatal;
	
	public enum EstadoNeonatal{
		pendiente, rechazado, aprobado
	}
	
	public ReanimacionNeonatal() {}

	public ReanimacionNeonatal(Integer idNeonatales, Integer idPartero, String capacitacionNeonatal,
			EstadoNeonatal estadoNeonatal) {
		super();
		this.idNeonatales = idNeonatales;
		this.idPartero = idPartero;
		this.capacitacionNeonatal = capacitacionNeonatal;
		this.estadoNeonatal = estadoNeonatal;
	}

	public Integer getIdNeonatales() {
		return idNeonatales;
	}

	public void setIdNeonatales(Integer idNeonatales) {
		this.idNeonatales = idNeonatales;
	}

	public Integer getIdPartero() {
		return idPartero;
	}

	public void setIdPartero(Integer idPartero) {
		this.idPartero = idPartero;
	}

	public String getCapacitacionNeonatal() {
		return capacitacionNeonatal;
	}

	public void setCapacitacionNeonatal(String capacitacionNeonatal) {
		this.capacitacionNeonatal = capacitacionNeonatal;
	}

	public EstadoNeonatal getEstadoNeonatal() {
		return estadoNeonatal;
	}

	public void setEstadoNeonatal(EstadoNeonatal estadoNeonatal) {
		this.estadoNeonatal = estadoNeonatal;
	}
	
	
}
