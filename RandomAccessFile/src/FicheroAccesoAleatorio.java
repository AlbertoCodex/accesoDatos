import java.io.*;

public class FicheroAccesoAleatorio {
    RandomAccessFile raf;

    // Constructor
    public FicheroAccesoAleatorio(String nombre) throws IOException {
        raf = new RandomAccessFile(nombre,"rw");
    }
    // Escribe el texto en el punto donde se encuentre el cursor del archivo.
    public void escribir(String texto) throws IOException {
        raf.writeBytes(" " + texto + " ");
    }
    // Coloca el cursor en la posición indicada.
    public void ir(long posicion) throws IOException {
        raf.seek(posicion);
    }
    // Adelanta el cursor en las posiciones indicadas.
    public void adelante(long posiciones) throws IOException {
        long posActual = raf.getFilePointer();
        raf.seek(posActual + posiciones);
    }
    // Atrasa el cursor en las posiciones indicadas.
    public void atras(long posiciones) throws IOException {
        long posActual = raf.getFilePointer();
        raf.seek(posActual - posiciones);
    }
    // Muestra por pantalla el caracter apuntado por el cursor.
    public void leer() throws IOException {
        // readChar() da EOF Exception
        int value = raf.read();
        char letra = (char) value;
        System.out.println(letra);
    }
    // Muestra el resto de la línea de la linea donde se encuentra el cursor.
    public void leerLinea() throws IOException {
        String line = raf.readLine();
        System.out.println(line);
    }
    // Muestra el contenido de un fichero.
    public void mostrarFichero() throws IOException {
        String line;
        while ((line = raf.readLine()) != null) {
            System.out.println(line);
        }
    }

    public RandomAccessFile getRaf() {
        return raf;
    }
}