package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

/**
 * Alberto Naranjo
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        menu();
    }
    public static Session conectDB() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        if (session != null) {
            System.out.println("Se ha iniciado sesion");
        } else {
            System.out.println("No se pudo iniciar sesion");
        }
        return session;
    }

    public static ContactoClass findPersona(String dni) {
        Session session = conectDB();
        ContactoClass persona = session.get(ContactoClass.class, dni);
        if (persona == null) {
            System.out.println("No existe esta persona");
        } else {
            System.out.println("Esta persona es: ");
            System.out.println(persona.getNombre());
        }
        session.close();
        return persona;
    }

    public static void addPersona(String dni, String nombre) {
        Session session = conectDB();
        ContactoClass newPersona = new ContactoClass();
        if (findPersona(dni) == null) {
            newPersona.setNombre(nombre);
        } else {
            System.out.println("Ya existía un registro con DNI " + dni + " con nombre: " + findPersona(dni).getNombre());
            borrar(dni);
            System.out.println("El nombre se ha modificado a: " + nombre);
            newPersona.setNombre(nombre);
        }
        newPersona.setDni(dni);
        session.beginTransaction();
        session.persist(newPersona);
        session.getTransaction().commit();
        session.close();
    }

    public static void ver() {
        Session session = conectDB();
        Query q = session.createQuery("from ContactoClass");
        List<ContactoClass> list = q.list();
        for (ContactoClass contacto : list) {
            System.out.println(contacto.getDni() + " " + contacto.getNombre());
        }
    }

    public static void longitud() {
        Session session = conectDB();
        Query q = session.createQuery("from ContactoClass");
        List<ContactoClass> list = q.list();
        System.out.println("Hay " + list.size() + " contactos en la agenda.");
    }

    public static void borrar(String dni) {
        Session session = conectDB();
        ContactoClass persona = session.get(ContactoClass.class, dni);
        session.beginTransaction();
        session.remove(persona);
        session.getTransaction().commit();
        session.close();
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        String dni;
        String nombre;
        int x = -1;
        while (x != 0) {
            System.out.println("1- Añadir/Modificar una persona (Insertar)");
            System.out.println("2- Mostrar los contactos de la agenda (Ver)");
            System.out.println("3- Buscar un contacto (Buscar)");
            System.out.println("4- Mostrar cuantos contactos hay (Longitud)");
            System.out.println("5- Borrar un contacto (Borrar)");
            System.out.println("0- Salir");
            x = sc.nextInt();
            switch (x){
                case 1:
                    System.out.println("Indica el DNI");
                    dni = sc2.nextLine();
                    System.out.println("Indica el nombre");
                    nombre = sc2.nextLine();
                    addPersona(dni, nombre);
                    break;
                case 2:
                    ver();
                    break;
                case 3:
                    System.out.println("Indica el DNI de la persona a buscar");
                    dni = sc2.nextLine();
                    findPersona(dni);
                    break;
                case 4:
                    longitud();
                    break;
                case 5:
                    System.out.println("Indica el DNI de la persona a eliminar");
                    dni = sc2.nextLine();
                    borrar(dni);
                    break;
            }

        }
    }
}
