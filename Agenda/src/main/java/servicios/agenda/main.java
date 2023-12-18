/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios.agenda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 *
 * @author Alberto
 */
public class main {

    public static void main(String[] args) throws IOException {
        Agenda agenda = new Agenda();
        menu(args[0], agenda);
    }

    public static String leer(File f) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line = "";
        String texto = "";
        if (f.exists() && f.isFile()) {
            while ((line = br.readLine()) != null) {
                texto += line + '\n';
            }
        }
        br.close();
        return texto;
    }

    public static void crearCopia(File fichero) throws IOException {
        String copia = fichero.getName().replaceFirst("[.][^.]+$", "") + "_copia.txt";
        File copiaFile = new File(copia);
        FileWriter fw = new FileWriter(copia, false);
        BufferedWriter bw = new BufferedWriter(fw);

        System.out.println("Se ha realizado una copia en " + copia);
        bw.write(leer(fichero));
        bw.close();
    }

    public static void menu(String agendaFichero, Agenda agenda) throws IOException {
        Scanner sc = new Scanner(System.in);
        File f = new File(agendaFichero);
        int x = 1;

        while (x != 0) {
            System.out.println(leer(f));;
            System.out.println("0 para salir" + '\n'
                    + "1 para hacer cambios en el fichero" + '\n'
                    + "2 para crear una copia" + '\n');
            x = sc.nextInt();

            switch (x) {
                case 1:
                    menuCambios(agenda, f);
                    break;

                case 2:
                    crearCopia(f);
                    break;

                default:
                    break;
            }
        }
    }

    public static void menuCambios(Agenda agenda, File f) throws IOException {
        cargarHash(agenda, f);
        String nombreEjemplo = "Test Borrar";
        String numeroModificado = "543254534534";
        Scanner sc = new Scanner(System.in);
        FileWriter fw = new FileWriter(f, true);
        BufferedWriter bw = new BufferedWriter(fw);
        int x = -1;

        while (x != 0) {
            System.out.println("0 para salir" + '\n'
                    + "1 para insertar un contacto" + '\n'
                    + "2 para buscar un contacto" + '\n'
                    + "3 para ver cuantos contactos hay" + '\n'
                    + "4 para ver la agenda" + '\n'
                    + "5 para borrar un contacto de la agenda" + '\n'
                    + "6 para modificar un contacto de la agenda");
            x = sc.nextInt();

            switch (x) {
                case 1:
                    bw = new BufferedWriter(fw);
                    String newContacto = agenda.insertar(nombreEjemplo, String.valueOf((int) (Math.random() * 999999999)));
                    bw.write(newContacto);
                    break;
                case 2:
                    agenda.buscar("Alberto Naranjo,");
                    break;
                case 3:
                    agenda.longitud();
                    break;
                case 4:
                    agenda.ver();
                    break;
                case 5:
                    agenda.borrar(nombreEjemplo);
                    deleteContact(f, nombreEjemplo);
                    break;
                case 6:
                    bw = new BufferedWriter(fw);
                    deleteContact(f, nombreEjemplo);
                    bw.newLine();
                    bw.write(agenda.insertar(nombreEjemplo, numeroModificado));
                    break;
                default:
                    break;
            }
        }
        
    }

    public static void cargarHash(Agenda agenda, File f) throws IOException {
        String texto = leer(f);
        System.out.println(texto);
        String nombre = "";
        String numero = "";
        String value = "";

        for (int i = 0; i < texto.length(); i++) {
            value += texto.charAt(i);
            if (texto.charAt(i) == ',' || texto.charAt(i) == '=') {
                nombre = value;
                value = "";
            }
            if (texto.charAt(i) == '\n') {
                numero = value;
                agenda.insertar(nombre, numero);
                nombre = "";
                numero = "";
                value = "";
            }
        }
    }

    public static void deleteContact(File f, String nombre) throws IOException {
        RandomAccessFile file = new RandomAccessFile(f, "rw");
        String linea;
        String texto = "";
        while ((linea = file.readLine()) != null) {
            if (linea.startsWith(nombre)) {
                continue;
            }
            texto += linea + "\n";
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write(texto);
        file.close();
        bw.close();
    }
}
