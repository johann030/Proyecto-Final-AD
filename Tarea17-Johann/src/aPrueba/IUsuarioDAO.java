package aPrueba;
import java.util.List;

public interface IUsuarioDAO {
    void guardarUsuario(Usuario usuario);
    Usuario obtenerUsuario(Long id);
    List<Usuario> listarUsuarios();
    void eliminarUsuario(Long id);
}
