package aPrueba;
import java.util.Scanner;

public class VistaUsuario {
    private Scanner scanner = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("\n1. Registrar Usuario");
        System.out.println("2. Mostrar Usuario");
        System.out.println("3. Listar Usuarios");
        System.out.println("4. Eliminar Usuario");
        System.out.println("5. Salir");
        System.out.print("Elige una opción: ");
        return scanner.nextInt();
    }

    public String pedirNombre() {
        System.out.print("Nombre: ");
        return scanner.next();
    }

    public String pedirEmail() {
        System.out.print("Email: ");
        return scanner.next();
    }

    public Long pedirId() {
        System.out.print("ID del usuario: ");
        return scanner.nextLong();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
