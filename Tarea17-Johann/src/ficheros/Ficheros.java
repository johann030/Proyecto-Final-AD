package ficheros;

public interface Ficheros {

	void guardarTxtAlumnos() throws Exception;

	void leerTxtAlumnos() throws Exception;

	void guardarJSONGrupos() throws Exception;

	void leerJSONGrupos() throws Exception;

	void elegirGrupoJSON(int idGrupo) throws Exception;
}