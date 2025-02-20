package ficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import dao.AlumnoBD;
import dao.AlumnoDao;
import modelo.Alumno;
import modelo.Grupo;
import pool.Conexion;

public class FicherosBBDD implements Ficheros {
	private AlumnoDao bd = AlumnoBD.getInstance();

	private static FicherosBBDD instance;

	static {
		instance = new FicherosBBDD();
	}

	private FicherosBBDD() {
	}

	public static FicherosBBDD getInstance() {
		return instance;
	}

	@Override
	public void guardarTxtAlumnos() throws Exception {
		String sql = """
				SELECT NIA, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, id_grupo
				FROM alumnos
				""";

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("alumnos.txt"));
				Connection conn = Conexion.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				bw.write(String.format("%d, %s, %s, %s, %s, %s, %s, %d", rs.getInt("NIA"), rs.getString("nombre"),
						rs.getString("apellidos"), rs.getString("genero"), rs.getDate("fecha_nacimiento").toLocalDate(),
						rs.getString("ciclo"), rs.getString("curso"), rs.getInt("id_grupo")));
				bw.newLine();
			}

			System.out.println("Se han guardado los alumnos correctamente.");

		}
	}

	@Override
	public void leerTxtAlumnos() throws Exception {
		String sql = """
				INSERT INTO alumnos(NIA, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, id_grupo)
				VALUES (?,?,?,?,?,?,?,?)
				""";

		String lineas;
		try (BufferedReader br = new BufferedReader(new FileReader("alumnos.txt"));
				Connection conn = Conexion.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {

			while ((lineas = br.readLine()) != null) {
				String[] alumnoData = lineas.split(",");
				int nia = Integer.parseInt(alumnoData[0]);
				String nombre = alumnoData[1].trim();
				String apellidos = alumnoData[2].trim();
				String genero = alumnoData[3].trim();
				String fechaNacimiento = alumnoData[4].trim();
				String ciclo = alumnoData[5].trim();
				String curso = alumnoData[6].trim();
				int id_grupo = Integer.parseInt(alumnoData[7].trim());

				ps.setInt(1, nia);
				ps.setString(2, nombre);
				ps.setString(3, apellidos);
				ps.setString(4, genero);
				ps.setDate(5, Date.valueOf(fechaNacimiento));
				ps.setString(6, ciclo);
				ps.setString(7, curso);
				ps.setInt(8, id_grupo);

				ps.executeUpdate();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void guardarJSONGrupos() throws Exception {
		String sqlGrupos = """
				SELECT id_grupo, nombre_grupo,aula
				FROM grupos
				""";

		String sqlAlumnos = """
				SELECT NIA, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso
				FROM alumnos WHERE id_grupo = ?
				""";

		JSONArray listaGrupos = new JSONArray();

		try (Connection conn = Conexion.getConnection();
				PreparedStatement psGrupos = conn.prepareStatement(sqlGrupos);
				ResultSet rsGrupos = psGrupos.executeQuery();
				FileWriter JSON = new FileWriter("grupos.json")) {

			while (rsGrupos.next()) {
				JSONObject grupoJSON = new JSONObject();
				grupoJSON.put("id_grupo", rsGrupos.getInt("id_grupo"));
				grupoJSON.put("nombre_grupo", rsGrupos.getString("nombre_grupo"));
				grupoJSON.put("aula", rsGrupos.getInt("aula"));

				JSONArray listaAlumnos = new JSONArray();
				try (PreparedStatement psAlumnos = conn.prepareStatement(sqlAlumnos)) {
					psAlumnos.setInt(1, rsGrupos.getInt("id_grupo"));
					try (ResultSet rsAlumnos = psAlumnos.executeQuery()) {
						while (rsAlumnos.next()) {
							JSONObject alumnoJSON = new JSONObject();
							alumnoJSON.put("NIA", rsAlumnos.getInt("NIA"));
							alumnoJSON.put("nombre", rsAlumnos.getString("nombre"));
							alumnoJSON.put("apellidos", rsAlumnos.getString("apellidos"));
							alumnoJSON.put("genero", rsAlumnos.getString("genero"));
							alumnoJSON.put("fecha_nacimiento", rsAlumnos.getDate("fecha_nacimiento").toString());
							alumnoJSON.put("ciclo", rsAlumnos.getString("ciclo"));
							alumnoJSON.put("curso", rsAlumnos.getString("curso"));

							listaAlumnos.add(alumnoJSON);
						}
					}
				}
				grupoJSON.put("alumnos", listaAlumnos);

				listaGrupos.add(grupoJSON);

			}
			JSON.write(listaGrupos.toJSONString());
		}
	}

	@Override
	public void leerJSONGrupos() throws Exception {
		String ruta = "grupos.json";

		try {
			JSONParser parser = new JSONParser();
			JSONArray gruposArray = (JSONArray) parser.parse(new FileReader(ruta));

			for (Object grupoObj : gruposArray) {
				JSONObject grupoJSON = (JSONObject) grupoObj;

				int id_grupo = ((Long) grupoJSON.get("id_grupo")).intValue();
				String nombre_grupo = (String) grupoJSON.get("nombre_grupo");
				int aula = ((Long) grupoJSON.get("aula")).intValue();

				bd.insertarGrupo(new Grupo(id_grupo, nombre_grupo, aula));

				JSONArray alumnosArray = (JSONArray) grupoJSON.get("alumnos");
				for (Object alumnoObj : alumnosArray) {
					JSONObject alumnoJSON = (JSONObject) alumnoObj;

					int NIA = ((Long) alumnoJSON.get("NIA")).intValue();
					String nombre = (String) alumnoJSON.get("nombre");
					String apellidos = (String) alumnoJSON.get("apellidos");
					String genero = (String) alumnoJSON.get("genero");
					String fechaNacimientoStr = (String) alumnoJSON.get("fecha_nacimiento");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate fecha_nacimiento = LocalDate.parse(fechaNacimientoStr, formatter);
					String ciclo = (String) alumnoJSON.get("ciclo");
					String curso = (String) alumnoJSON.get("curso");
					bd.insertarAlumno(
							new Alumno(NIA, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, id_grupo));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void elegirGrupoJSON(int idGrupo) throws Exception {
		// TODO Auto-generated method stub

	}

}
