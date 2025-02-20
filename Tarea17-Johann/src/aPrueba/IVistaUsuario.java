package aPrueba;
public interface IVistaUsuario {
    int mostrarMenu();
    String pedirNombre();
    String pedirEmail();
    Long pedirId();
    void mostrarMensaje(String mensaje);
}
