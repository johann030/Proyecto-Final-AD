package modelo_hibernate;

public class GrupoH {
	private int id_grupo;
	private String nombre;
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