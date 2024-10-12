import java.io.*;
import java.util.HashMap;

public class Agenda {
    private HashMap<String, Integer> personas;
    private RandomAccessFile raf;
    // Constructor iniciar Map
    public Agenda() {
        personas = new HashMap<>();
    }
    // Nueva entrada al mapa
    public void insertar(String nombre, int numero) {
        personas.put(nombre, numero);
        System.out.println("Se ha añadido a: " + nombre + " con numero " + numero);
    }
    // Devuelve el número asociado al nombre
    public int buscar(String nombre) {
        System.out.println("El nombre " + nombre
                + " tiene asociado el numero " + personas.get(nombre));
        return personas.get(nombre);
    }
    // Devuelve el número de contactos de la agenda.
    public int longitud() {
        System.out.println("Hay " + personas.size() + " contactos");
        return personas.size();
    }
    // Muestra toda la agenda.
    public void ver() {
        for (String i : personas.keySet()) {
            System.out.println("Nombre: " + i + " Número: " + personas.get(i));
        }
    }
    // Borra el contacto con ese nombre
    public void borrar(String nombre) {
        if (personas.containsKey(nombre)) {
            personas.remove(nombre);
            System.out.println("Se ha borrado a: " + nombre);
        } else {
            System.out.println("No se encuentra ese nombre");
        }
    }
    // Modifica el número de telefono (por el pasado como argumento) del nombre solicitado.
    public void modificar(String nombre, int numero) {
        if (personas.containsKey(nombre)) {
            personas.put(nombre, numero);
            System.out.println("El nuevo número de " + nombre + " es " + numero);
        } else {
            System.out.println("No se encuentra ese nombre");
        }
    }
    // Metodo para convertir la información en un String[] (opcional).
    public void exportar(String ruta) throws IOException {
        raf = new RandomAccessFile(ruta,"rw");
        raf.setLength(0); // Limpiar el contenido del archivo
        for (String i : personas.keySet()) {
            raf.writeBytes((i + "," + personas.get(i) + "\n"));
        }
        raf.close();
    }
    // Cargar el contenido de la agenda (fichero CSV)
    public void importar(String ruta) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        String line;

        while((line=br.readLine()) != null) {
            String str[] = line.split(","); // Separados por coma
            if (str.length == 2) { // Minimo Key/Value
                String nombre = str[0].trim(); // Key
                int numero = Integer.parseInt(str[1].trim()); // Value
                personas.put(nombre,numero);
            } else {
                System.out.println("Esta linea no es valida " + line);
            }
        }
    }
}
