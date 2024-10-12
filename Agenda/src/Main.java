import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static Agenda agenda = new Agenda();

    public static void main(String[] args) throws IOException {
        String ruta = args[0];
        agenda.importar(ruta);

        menu(agenda, ruta);
    }
    public static void menu(Agenda agenda, String ruta) throws IOException {
        int x = -1;
        String nombre;
        int numero;

        while (x != 0) {
            menuPreguntas();
            x = sc.nextInt();
            switch (x) {
                case 0:
                    System.out.println("Hasta luego");
                    break;
                case 1: // Add nueva entrada
                    if (sc.hasNextLine()) {
                        sc.nextLine();
                    }
                    System.out.println("¿Cuál es el nombre?");
                    nombre = sc.nextLine();
                    System.out.println("¿Cuál es el numero?");
                    numero = sc.nextInt();
                    agenda.insertar(nombre, numero);
                    break;
                case 2: // Mostrar valor del nombre dado
                    if (sc.hasNextLine()) {
                        sc.nextLine();
                    }
                    System.out.println("Introduce el nombre del contacto");
                    nombre = sc.nextLine();
                    agenda.buscar(nombre);
                    break;
                case 3: // Mostrar size de la agenda
                    agenda.longitud();
                    break;
                case 4: // Mostrar key/value de agenda
                    agenda.ver();
                    break;
                case 5: // Borrar dado un nombre
                    if (sc.hasNextLine()) {
                        sc.nextLine();
                    }
                    System.out.println("Introduce el nombre del contacto");
                    nombre = sc.nextLine();
                    agenda.borrar(nombre);
                    break;
                case 6: // Modificar telefono
                    if (sc.hasNextLine()) {
                        sc.nextLine();
                    }
                    System.out.println("Introduce el nombre del contacto");
                    nombre = sc.nextLine();
                    System.out.println("Introduce el nuevo telefono del contacto");
                    numero = sc.nextInt();
                    agenda.modificar(nombre, numero);
                    break;
                case 7: // Exportar agenda
                    agenda.exportar(ruta);
            }
        }
    }
    public static void menuPreguntas() {
        System.out.println("0 para salir" + '\n'
                + "1 para insertar un nuevo contacto" + '\n'
                + "2 para buscar un contacto. (Devuelve el numero asociado)" + '\n'
                + "3 para mostrar la cantidad de contactos" + '\n'
                + "4 para mostrar los contactos" + '\n'
                + "5 para borrar un contacto" + '\n'
                + "6 para modificar el numero de telefono" + '\n'
                + "7 para exportar");
    }
}