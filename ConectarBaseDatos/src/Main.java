import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        String[] conex = conexionBD();
        menu(conex);
    }

    public static String[] conexionBD() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String[] conex = new String[3];
        conex[0] = "jdbc:mysql://localhost:3306/bdejer1";
        System.out.println("Pon el usuario");
        conex[1] = sc.nextLine();
        System.out.println("Pon la contraseña");
        conex[2] = sc.nextLine();
        try (Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2])) {
            System.out.println("Conexion realizada");
            return conex;
        }
    }

    public static void menu(String[] conex) throws SQLException {
        Scanner sc = new Scanner(System.in);
        int x = -1;
        while (x != 0) {
            System.out.println("1- Introducir una nueva persona");
            System.out.println("2- Listar el contenido de la tabla");
            System.out.println("3- Listar el contenido al revés");
            System.out.println("0- Salir");
            x = sc.nextInt();
            switch (x){
                case 1:
                    addPersona(conex);
                    break;
                case 2:
                    listarTabla(conex);
                    break;
                case 3:
                    listarTablaReverse(conex);
                    break;
            }

        }
    }

    public static void addPersona(String[] conex) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce el DNI (9 caracteres)");
        String dni = sc.nextLine();
        System.out.println("Introduce el nombre");
        String nombre = sc.nextLine();
        System.out.println("Introduce el apellido");
        String apellido = sc.nextLine();
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        try (Statement s = c.createStatement()) {
            s.execute("INSERT INTO personas " +
                    "(DNI, Nombre, Apellidos) VALUES ('" + dni + " ', '" + nombre + "' , ' "+ apellido +"')");
        }
    }
    public static void listarTabla(String[] conex) throws SQLException {
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM bdejer1.personas");
        while (rs.next()) {
            String dni = rs.getString(1);
            String nombre = rs.getString(2);
            String apellido = rs.getString(3);
            System.out.println(dni + ", " + nombre + ", " + apellido);
        }
        s.close();
        c.close();
    }
    public static void listarTablaReverse(String[] conex) throws SQLException {
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        Statement s = c.createStatement();
        // El campo DNI en mi tabla es mayuscula, si en la del profesor es minuscula puede fallar
        ResultSet rs = s.executeQuery("SELECT * FROM bdejer1.personas ORDER BY DNI DESC");
        while (rs.next()) {
            String dni = rs.getString(1);
            String nombre = rs.getString(2);
            String apellido = rs.getString(3);
            System.out.println(dni + ", " + nombre + ", " + apellido);
        }
        s.close();
        c.close();
    }
}