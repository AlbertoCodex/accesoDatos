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
            switch (x){
                case 1:
                    addRectangulo(conex);
                    break;
                case 2:
                    System.out.println("¿Cual rectangulo quieres utilizar? (0-9)");
                    codigo = sc.nextInt();
                    baRec(conex, codigo);
                    areaRecF(conex, codigo);
                    areaRec(conex, codigo);
                    break;
                case 3:
                    System.out.println("¿Cual rectangulo quieres utilizar? (0-9)");
                    codigo = sc.nextInt();
                    baRec(conex, codigo);
                    perimetroRec(conex, codigo);
                    perimetroRecF(conex, codigo);
                    break;
            }

        }
    }

    public static void addRectangulo(String[] conex) throws SQLException {
        int codigo, base, altura;
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        try (Statement s = c.createStatement()) {
            for (int i = 0; i < 10; i++) {
                codigo = i;
                base = (int) (Math.random() * 10+1);
                altura = (int) (Math.random() * 10+1);
                s.execute("INSERT INTO rectangulos " +
                        "(codigo, base, altura) VALUES ('" + codigo + " ', '" + base + "' , ' " + altura + "')");
            }
        }
    }

    // Llamada del procedimiento
    public static void areaRec(String[] conex, int codigo) throws SQLException {
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        try (CallableStatement cs = c.prepareCall("{?=call areaRec(?)}")) {
            cs.setInt(2,codigo);
            cs.registerOutParameter(1, Types.INTEGER);
            cs.execute();
            int res = cs.getInt(1);
            System.out.println("Utilizando llamada al procedimiento");
            System.out.println(res);
        }
    }
    // Llamada del procedimiento
    public static void perimetroRec(String[] conex, int codigo) throws SQLException {
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        try (CallableStatement cs = c.prepareCall("{?=call perimetroRec(?)}")) {
            cs.setInt(2,codigo);
            cs.registerOutParameter(1, Types.INTEGER);
            cs.execute();
            int res = cs.getInt(1);
            System.out.println("Utilizando llamada al procedimiento");
            System.out.println(res);
        }
    }

    // Llamada de la funcion
    public static void areaRecF(String[] conex, int codigo) throws SQLException {
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        try (CallableStatement cs = c.prepareCall("{?=call areaRecF(?)}")) {
            cs.setInt(2,codigo);
            cs.registerOutParameter(1, Types.INTEGER);
            cs.execute();
            int res = cs.getInt(1);
            System.out.println("Utilizando llamada a la funcion");
            System.out.println(res);
        }
    }
    // Llamada de la funcion
    public static void perimetroRecF(String[] conex, int codigo) throws SQLException {
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        try (CallableStatement cs = c.prepareCall("{?=call perimetroRecF(?)}")) {
            cs.setInt(2,codigo);
            cs.registerOutParameter(1, Types.INTEGER);
            cs.execute();
            int res = cs.getInt(1);
            System.out.println("Utilizando llamada a la funcion");
            System.out.println(res);
        }
    }

    public static void baRec(String[] conex, int codigo) throws SQLException {
        int base = 0, altura = 0;
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        try (Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT base, altura FROM rectangulos " +
                    "WHERE codigo="+codigo);
            while (rs.next()) {
                base = rs.getInt(1);
                altura = rs.getInt(2);
            }
            System.out.println("La base es: "+ base + " y la altura es: "+ altura);
        }
    }
}