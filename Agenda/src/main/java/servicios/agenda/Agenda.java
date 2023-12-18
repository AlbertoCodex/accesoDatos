/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package servicios.agenda;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alberto
 */
public class Agenda {
    HashMap<String, String> agendaContactos = new HashMap<String, String>();

    public Agenda() {
    }

    public String insertar(String nombre, String numero) {
        Map.Entry<String,String> lastEntry = null;
        agendaContactos.put(nombre, numero);
        lastEntry = agendaContactos.entrySet().stream().reduce((first,second) -> second).get();
        return lastEntry.toString();
    }
    
    public void buscar(String nombre) {
        System.out.println(nombre + " " + agendaContactos.get(nombre));
    }
    
    public void longitud() {
        System.out.println("Hay un total de " + agendaContactos.size() + " contactos");
    }
    
    public void ver() {
        for (String i : agendaContactos.keySet()) {
            System.out.println("key: " + i + " value: " + agendaContactos.get(i));
        }
    }
    
    public void borrar(String nombre) {
        agendaContactos.remove(nombre);
        System.out.println("Se ha eliminado de la agenda a: " + nombre);
        
    }
    
    public void modificar(String nombre, String numero) {
        System.out.println("El contacto " + nombre + " con numero de telefono: " 
                + agendaContactos.get(nombre) + " ha sido actualizado" );
        agendaContactos.put(nombre, numero);
        System.out.println("El nuevo telefono de " + nombre + " es: " + agendaContactos.get(nombre));
    }
    
    public void exportar() {
        
    }
}
