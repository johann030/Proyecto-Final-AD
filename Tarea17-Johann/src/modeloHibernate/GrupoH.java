package modeloHibernate;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "grupos")
public class GrupoH implements Serializable {

	private static final long serialVersionUID = 1655512437348433605L;

	@Id
	@Column(name = "IdGrupo", nullable = false)
	private int id_grupo;

	@Column(name = "NombreGrupo", length = 100)
	private String nombre;

	@Column(name = "Aula")
	private int aula;

	public GrupoH() {
	}

	public GrupoH(String nombre, int aula) {
		this.nombre = nombre;
		this.aula = aula;
	}

	public GrupoH(int id_grupo, String nombre, int aula) {
		this(nombre, aula);
		this.id_grupo = id_grupo;
	}

	public int getId_grupo() {
		return id_grupo;
	}

	public void setId_grupo(int id_grupo) {
		this.id_grupo = id_grupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getAula() {
		return aula;
	}

	public void setAula(int aula) {
		this.aula = aula;
	}
}