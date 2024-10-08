import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        // Ruta = Argumento 0
        FicheroAccesoAleatorio faa = new FicheroAccesoAleatorio(args[0]);
        menu(faa);
    }

    public static void menu(FicheroAccesoAleatorio faa) throws IOException {
        int x = -1;
        long pos;

        while (x != 0) {
            System.out.println("El puntero está en la posición: " + faa.getRaf().getFilePointer());
            menuPreguntas();
            x = sc.nextInt();
            switch (x) {
                case 0:
                    System.out.println("Hasta luego");
                    break;
                case 1:
                    pos = faa.posicionActual();
                    if (sc.hasNextLine()) {sc.nextLine();} // Comprobar que esta limpio el scanner
                    System.out.println("Escribe el texto:");
                    faa.escribir(sc.nextLine());
                    faa.ir(pos);
                    break;
                case 2: // Coloca el cursor en la posición indicada.
                    if (sc.hasNextLine()) {sc.nextLine();} // Comprobar que esta limpio el scanner
                    System.out.println("¿A qué posición quieres ir?");
                    faa.ir(sc.nextInt());
                    break;
                case 3: // Adelanta el cursor en las posiciones indicadas.
                    if (sc.hasNextLine()) {sc.nextLine();} // Comprobar que esta limpio el scanner
                    System.out.println("¿Cuántas posiciones quieres adelantar?");
                    faa.adelante(sc.nextInt());
                    break;
                case 4: // Atrasa el cursor en las posiciones indicadas.
                    if (sc.hasNextLine()) {sc.nextLine();} // Comprobar que esta limpio el scanner
                    System.out.println("¿Cuántas posiciones quieres retrasar?");
                    faa.atras(sc.nextInt());
                    break;
                case 5: // Muestra por pantalla el caracter apuntado por el cursor.
                    pos = faa.posicionActual();
                    faa.leer();
                    faa.ir(pos);
                    break;
                case 6: // Muestra el resto de la línea de la linea donde se encuentra el cursor.
                    pos = faa.posicionActual();
                    faa.leerLinea();
                    faa.ir(pos);
                    break;
                case 7: // Muestra el contenido de un fichero
                    pos = faa.posicionActual();
                    faa.ir(0);
                    faa.mostrarFichero();
                    faa.ir(pos);
                    break;
            }
        }
    }
    // Souts del menu
    public static void menuPreguntas() {
        System.out.println("0 para salir" + '\n'
                + "1 para escribir el texto en el punto donde se encuentre el cursor del archivo." + '\n'
                + "2 para coloca el cursor en la posición indicada." + '\n'
                + "3 para adelantar el cursor en las posiciones indicadas." + '\n'
                + "4 para atrasar el cursor en las posiciones indicadas." + '\n'
                + "5 para mostrar por pantalla el caracter apuntado por el cursor." + '\n'
                + "6 para mostrar el resto de la línea de la linea donde se encuentra el cursor." + '\n'
                + "7 para mostrar el contenido de un fichero");
    }
}