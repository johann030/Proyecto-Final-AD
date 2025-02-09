package modelo_hibernate;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "alumnos")
public class AlumnoH implements Serializable {

	private static final long serialVersionUID = -5869298698653042743L;

	@Id
	@Column(name = "NIA", nullable = false)
	private int nia;

	@Column(name = "Nombre", nullable = false, length = 100)
	private String nombre;

	@Column(name = "Apellidos", nullable = false, length = 100)
	private String apellidos;

	@Column(name = "Genero", nullable = false, length = 1)
	private String genero;

	@Column(name = "FechaNacimiento", nullable = false)
	private LocalDate nacimiento;

	@Column(name = "Ciclo", nullable = false, length = 100)
	private String ciclo;

	@Column(name = "Curso", nullable = false, length = 20)
	private String curso;

	@Column(name = "IdGrupo", nullable = false)
	private int id_grupo;

	public AlumnoH() {
	}

	public AlumnoH(int nia, String nombre, String apellidos, String genero, LocalDate nacimiento, String ciclo,
			String curso, int id_grupo) {
		this.nia = nia;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.genero = genero;
		this.nacimiento = nacimiento;
		this.ciclo = ciclo;
		this.curso = curso;
		this.id_grupo = id_grupo;
	}

	public int getNia() {
		return nia;
	}

	public void setNia(int nia) {
		this.nia = nia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public LocalDate getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(LocalDate nacimiento) {
		this.nacimiento = nacimiento;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public int getId_grupo() {
		return id_grupo;
	}

	public void setId_grupo(int id_grupo) {
		this.id_grupo = id_grupo;
	}

	@Override
	public String toString() {
		return "Alumno [nia=" + nia + ", nombre=" + nombre + ", apellidos=" + apellidos + ", genero=" + genero
				+ ", nacimiento=" + nacimiento + ", ciclo=" + ciclo + ", curso=" + curso + ", id_grupo=" + id_grupo
				+ "]";
	}
}