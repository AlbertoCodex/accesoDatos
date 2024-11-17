import java.sql.*;
import java.util.Scanner;

public static void main(String[] args) throws SQLException {
    String[] conex = conexionBD();
    menu(conex);
}
// Conexion a la base de datos (revisar mayusculas)
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
// Menu (Add, Area, Perimetro, List)
public static void menu(String[] conex) throws SQLException {
    Scanner sc = new Scanner(System.in);
    int codigo;
    int x = -1;
    while (x != 0) {
        System.out.println("1- Introducir 10 rectangulos");
        System.out.println("2- Calcular area de un rectangulo");
        System.out.println("3- Calcular perimetro de un rectangulo");
        System.out.println("4- Listar tabla");
        System.out.println("0- Salir");
        x = sc.nextInt();
        switch (x) {
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
            case 4:
                listarTabla(conex);
        }

    }
}
// Add 10 rectangulos (altura = random 1-10 -> base = altura*2)
public static void addRectangulo(String[] conex) throws SQLException {
    int codigo, base, altura;
    Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
    try (Statement s = c.createStatement()) {
        for (int i = 0; i < 10; i++) {
            codigo = i;
            altura = (int) (Math.random() * 10 + 1);
            base = altura*2;
            s.execute("INSERT INTO rectangulos " +
                    "(codigo, base, altura) VALUES ('" + codigo + " ', '" + base + "' , ' " + altura + "')");
        }
    }
}
// Llamada del procedimiento
public static void areaRec(String[] conex, int codigo) throws SQLException {
    System.out.println("Utilizando llamada al procedimiento");
    Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
    try (CallableStatement cs = c.prepareCall("{call areaRectangulo(?, ?)}")) {
        cs.setInt(1, codigo);
        cs.registerOutParameter(2, Types.INTEGER);
        cs.execute();
        int res = cs.getInt(2);
        System.out.println(res);
    }
}
// Llamada del procedimiento
public static void perimetroRec(String[] conex, int codigo) throws SQLException {
    System.out.println("Utilizando llamada al procedimiento");
    Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
    try (CallableStatement cs = c.prepareCall("{call perimetroRectangulo(?, ?)}")) {
        cs.setInt(1, codigo);
        cs.registerOutParameter(2, Types.INTEGER);
        cs.execute();
        int res = cs.getInt(2);
        System.out.println(res);
    }
}
// Llamada de la funcion
public static void areaRecF(String[] conex, int codigo) throws SQLException {
    System.out.println("Utilizando llamada a la funcion");
    Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
    String query = "SELECT areaRec(?) AS Area";

    try (PreparedStatement ps = c.prepareStatement(query)) {
        ps.setInt(1, codigo); // Parámetro de entrada
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int area = rs.getInt("Area");
            System.out.println("Área: " + area);
        }
    }
}
// Llamada de la funcion
public static void perimetroRecF(String[] conex, int codigo) throws SQLException {
    System.out.println("Utilizando llamada a la funcion");
    Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
    String query = "SELECT perimetroRec(?) AS Perimetro";

    try (PreparedStatement ps = c.prepareStatement(query)) {
        ps.setInt(1, codigo); // Parámetro de entrada
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int perimetro = rs.getInt("Perimetro");
            System.out.println("Perimetro: " + perimetro);
        }
    }
}
// Imprime la base y altura del rec seleccionado
public static void baRec(String[] conex, int codigo) throws SQLException {
    int base = 0, altura = 0;
    Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
    try (Statement s = c.createStatement()) {
        ResultSet rs = s.executeQuery("SELECT base, altura FROM rectangulos " +
                "WHERE codigo=" + codigo);
        while (rs.next()) {
            base = rs.getInt(1);
            altura = rs.getInt(2);
        }
        System.out.println("La base es: " + base + " y la altura es: " + altura);
    }
}
// Imprime todos los resultados de la tabla
public static void listarTabla(String[] conex) throws SQLException {
    Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
    Statement s = c.createStatement();
    ResultSet rs = s.executeQuery("SELECT * FROM bdfiguras.rectangulos");
    while (rs.next()) {
        String codigo = rs.getString(1);
        String base = rs.getString(2);
        String altura = rs.getString(3);
        System.out.println(codigo + ", " + base + ", " + altura);
    }
    s.close();
    c.close();
}