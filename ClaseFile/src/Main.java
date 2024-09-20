import java.io.File;
import java.util.Scanner;

public class Main {
     public static void main(String[] args) {
        File file = new File("/home/alberto/Desktop/acdatos/ClaseFile");
        menuDirectorio(file);
        /*for (String fichero : args) {
            File file = new File("/home/alberto/Desktop/acdatos/ClaseFile");
            if (comprobarFichero(file)) {
                menuDirectorio(file);
            }
        }
         */
    }

    public static boolean comprobarFichero(File fichero) {
        if (fichero.exists()) {
            if (fichero.isFile()) {
                System.out.println("El fichero existe y es un archivo");
            } else {
                System.out.println("El fichero existe y es un directorio");
            }
        } else {
            System.out.println("El fichero no existe o no se encuentra");
            return false;
        }
        return true;
    }

    public static void menuDirectorio(File fichero) {
        Scanner sc = new Scanner(System.in);
        int x = -1;
        // Array de ficheros de un directorio
        String[] listaFichero = fichero.list();
        File[] listaRecFicheros = fichero.listFiles();

        while (x != 0) {
            x = sc.nextInt();
            switch (x) {
                case 1:
                    for (int i = 0; i < fichero.listFiles().length; i++) {
                        System.out.println(listaFichero[i]);
                    }
                    break;
                case 2:
                    buscarDirectorio(listaRecFicheros);
            }
        }
    }

    public static void buscarDirectorio(File[] listaFicheros) {
         File[] subDirectorio;
    }
}