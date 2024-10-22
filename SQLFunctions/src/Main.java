import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        String[] conex = conexionBD();
        menu(conex);
    }
    public static String[] conexionBD() throws SQLException {
        Scanner sc = new Scanner(System.in);
        String[] conex = new String[3];
        conex[0] = "jdbc:mysql://localhost:3306/bdfiguras";
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
        int codigo;
        int x = -1;
        while (x != 0) {
            System.out.println("1- Introducir 10 rectangulos");
            System.out.println("2- Calcular area de un rectangulo");
            System.out.println("3- Calcular perimetro de un rectangulo");
            System.out.println("0- Salir");
            x = sc.nextInt();
            switch (x) {
                case 1:
                    // TO DO
            }
        }
    }
}