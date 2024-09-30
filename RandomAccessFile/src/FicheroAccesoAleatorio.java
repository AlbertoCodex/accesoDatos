import java.io.*;

public class FicheroAccesoAleatorio {
    RandomAccessFile raf;
    FileReader fr;
    FileWriter fw;
    BufferedReader br;
    BufferedWriter bw;

    // Constructor
    public FicheroAccesoAleatorio(String nombre) throws IOException {
        fr = new FileReader(nombre);
        fw = new FileWriter(nombre);
        br = new BufferedReader(fr);
        bw = new BufferedWriter(fw);
        raf = new RandomAccessFile(nombre,"rw");
    }
    // Escribe el texto en el punto donde se encuentre el cursor del archivo.
    public void escribir(String texto) {

    }
    // Coloca el cursor en la posición indicada.
    public void ir(long posicion) {

    }
    // Adelanta el cursor en las posiciones indicadas.
    public void adelante(long posiciones) {

    }
    // Atrasa el cursor en las posiciones indicadas.
    public void atras(long posiciones) {

    }
    // Muestra por pantalla el caracter apuntado por el cursor.
    public void leer() {

    }
    // Muestra el resto de la línea de la linea donde se encuentra el cursor.
    public void leerLinea() {

    }
    // Muestra el contenido de un fichero.
    public void mostrarFichero() {

    }
}