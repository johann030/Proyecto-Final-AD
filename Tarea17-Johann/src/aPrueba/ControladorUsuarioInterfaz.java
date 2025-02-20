package aPrueba;
import java.util.List;

public class ControladorUsuarioInterfaz {
    private IUsuarioDAO usuarioDAO;
    private IVistaUsuario vistaUsuario;

    public ControladorUsuarioInterfaz(IUsuarioDAO usuarioDAO, IVistaUsuario vistaUsuario) {
        this.usuarioDAO = usuarioDAO;
        this.vistaUsuario = vistaUsuario;
    }

    public void ejecutar() {
        boolean continuar = true;
        while (continuar) {
            int opcion = vistaUsuario.mostrarMenu();

            switch (opcion) {
                case 1:
                    String nombre = vistaUsuario.pedirNombre();
                    String email = vistaUsuario.pedirEmail();
                    usuarioDAO.guardarUsuario(new Usuario(nombre, email));
                    vistaUsuario.mostrarMensaje("✅ Usuario registrado!");
                    break;

                case 2:
                    Long id = vistaUsuario.pedirId();
                    Usuario usuario = usuarioDAO.obtenerUsuario(id);
                    if (usuario != null) {
                        vistaUsuario.mostrarMensaje("👤 Usuario: " + usuario.getNombre() + " - 📧 Email: " + usuario.getEmail());
                    } else {
                        vistaUsuario.mostrarMensaje("⚠️ Usuario no encontrado.");
                    }
                    break;

                case 3:
                    List<Usuario> usuarios = usuarioDAO.listarUsuarios();
                    if (usuarios.isEmpty()) {
                        vistaUsuario.mostrarMensaje("⚠️ No hay usuarios registrados.");
                    } else {
                        usuarios.forEach(u -> vistaUsuario.mostrarMensaje("🆔 ID: " + u.getId() + ", Nombre: " + u.getNombre()));
                    }
                    break;

                case 4:
                    id = vistaUsuario.pedirId();
                    usuarioDAO.eliminarUsuario(id);
                    vistaUsuario.mostrarMensaje("🗑 Usuario eliminado.");
                    break;

                case 5:
                    vistaUsuario.mostrarMensaje("👋 Saliendo...");
                    continuar = false;
                    break;

                default:
                    vistaUsuario.mostrarMensaje("❌ Opción inválida.");
            }
        }
    }
}
