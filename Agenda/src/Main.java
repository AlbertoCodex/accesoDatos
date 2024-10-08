import java.util.HashMap;

public class Main {
    static String NOMBRE = "Alberto";
    static int NUMERO = 1;

    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        menu(agenda);
    }

    public static void menu(Agenda agenda) {
        // Add nueva entrada
        agenda.insertar(NOMBRE, NUMERO);
        // Mostrar valor del nombre dado
        agenda.buscar(NOMBRE);
        // Mostrar size de la agenda
        agenda.longitud();
        // Mostrar key/value de agenda
        agenda.ver();
        // Borrar dado un nombre
        agenda.borrar(NOMBRE);
        // Revisar si se ha eliminado
        agenda.ver();
        // Modificar telefono
        agenda.insertar(NOMBRE, NUMERO);
        agenda.modificar(NOMBRE, NUMERO + 1);
        // Modificar null Key
        agenda.modificar("NULL", 0);
    }
}