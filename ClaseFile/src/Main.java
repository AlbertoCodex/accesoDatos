import java.io.*;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

     public static void main(String[] args) throws IOException {
        for (String fichero : args) {
            File ruta = new File(fichero);
            comprobarFichero(ruta);
        }
    }
    // exist && isFile
    public static void comprobarFichero(File ruta) throws IOException {
        if (ruta.exists()) {
            if (ruta.isFile()) {
                System.out.println("La ruta existe y es un archivo");
                menuArchivo(ruta);
            } else {
                System.out.println("La ruta existe y es un directorio");
                menuDirectorio(ruta);
            }
        } else {System.out.println("La ruta no existe o no se encuentra");}
    }
    // Listar contenido de directorios
    public static void menuDirectorio(File dir) {
        int x = -1;
        // Guardar los directorios y archivos
        File[] listaFicheros = dir.listFiles();

        while (x != 0) {
            System.out.println("0 para salir" + '\n'
                    + "1 para listar contenido" + '\n'
                    + "2 para listar el contenido recursivamente");
            x = sc.nextInt();
            switch (x) {
                case 0:
                    System.out.println("Hasta luego");
                    break;
                case 1: // Listar directorio
                    for (int i = 0; i < listaFicheros.length; i++) {
                        System.out.println(listaFicheros[i].getName());
                    }
                    break;
                case 2: // Listar directorio y subdirectorios
                    buscarDirectorio(listaFicheros);
                    break;
                default:
                    System.out.println("Introduce un número correcto");
                    break;
            }
        }
    }
    // Listar directorios y sus subdirectorios
    public static void buscarDirectorio(File[] listaFicheros) {
         File[] subDirectorio;
         // Lista ficheros
         for (int i = 0; i < listaFicheros.length; i++) {
             if (listaFicheros[i].isFile()) {System.out.println("archivo " + listaFicheros[i]);}
         }
         // Lista directorios
        for (int j = 0; j < listaFicheros.length; j++) {
            if (listaFicheros[j].isDirectory()) {
                System.out.println("\n" + "directorio " +listaFicheros[j]);
                subDirectorio = listaFicheros[j].listFiles();
                buscarDirectorio(subDirectorio);
            }
        }
    }
    // show, enum, find, append - text
    public static void menuArchivo(File archivo) throws IOException {
        BufferedReader br;
        int x = -1;
        String line;

        while (x != 0) {
            br = new BufferedReader(new FileReader(archivo));
            System.out.println("0 para salir" + '\n'
                    + "1 para listar contenido" + '\n'
                    + "2 para listar con Index" + '\n'
                    + "3 para buscar en el texto" + '\n'
                    + "4 para indexar un archivo");
            x = sc.nextInt();
            switch (x) {
                case 0:
                    System.out.println("Hasta luego");
                    break;
                case 1: // Muestra el contenido del archivo
                    while ((line = br.readLine()) != null) {System.out.println(line);}
                    break;
                case 2: // Enumera el contenido del archivo
                    int j = 1;
                    while ((line = br.readLine()) != null) {
                        System.out.println(j + "- " +line);
                        j++;
                    }
                    break;
                case 3: // Busca una cadena en el fichero
                    buscarString(br);
                    break;
                case 4: // Añade el texto del fichero dado al original
                    anexarFichero(archivo);
                    break;
                default:
                    System.out.println("Introduce un número correcto");
                    break;
            }
            br.close();
        }
    }
    // Busca una cadena y sus repeticiones en cada linea
    public static void buscarString(BufferedReader br) throws IOException {
         int contLine = 0;
         String line;

         if (sc.hasNextLine()) {sc.nextLine();} // Comprobar que esta limpio el scanner
         System.out.println("Introduce el texto a buscar");
         String texto = sc.nextLine();

         while ((line = br.readLine()) != null) {
             contLine++;
             int index = line.indexOf(texto);

             while (index != -1) {
                 System.out.println(line + " - línea " + contLine + " posición " + (index + 1));
                 index = line.indexOf(texto, index + 1); // Continuar buscando ocurrencias en la misma línea
             }
         }
    }
    // Copia el contenido de un archivo y lo copia al final del original
    public static void anexarFichero(File archivo) throws IOException {
        String line;
        StringBuilder fileText = new StringBuilder();
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));

        System.out.println("Introduce el nombre del fichero a anexar");
        if (sc.hasNextLine()) {sc.nextLine();} // Comprobar que esta limpio el scanner
        String file = sc.nextLine();
        File appendFile = new File(file);

        if (appendFile.exists() && appendFile.isFile()) {
            BufferedReader br = new BufferedReader(new FileReader(appendFile)); // Leer el archivo nuevo
            while ((line = br.readLine()) != null) {
                fileText.append(line).append("\n");
            }
            bw.newLine();
            bw.write(fileText.toString());
            br.close();
        } else {System.out.println("El archivo no se encuentra o no es un archivo");}
        bw.close();
    }
}