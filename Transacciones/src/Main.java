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
        conex[0] = "jdbc:mysql://localhost:3306/bdbanco";
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
            System.out.println("1- Realizar un ingreso");
            System.out.println("2- Retirada de dinero");
            System.out.println("3- Transferencia entre cuentas");
            System.out.println("4- Balance de cuenta");
            System.out.println("5- Mostrar todas las cuentas");
            System.out.println("0- Salir");
            x = sc.nextInt();
            switch (x){
                // Realizar un ingreso
                case 1:
                    ingreso(conex);
                    break;
                // Retirada de dinero
                case 2:
                    retirada(conex);
                    break;
                // Transferencia entre cuentas
                case 3:
                    transferencia(conex);
                    break;
                // Balance de cuenta
                case 4:
                    balance(conex);
                    break;
                // Listar tabla cuentas
                case 5:
                    listarCuentas(conex);
                    break;
            }
        }
    }
    public static void ingreso(String[] conex) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Cual es el número de cuenta?");
        String cuentaOrigen = sc.nextLine();
        System.out.println("¿Qué cantidad desea ingresar?");
        float ingreso = sc.nextFloat();

        // Comprobamos si existe el numero de cuenta
       if(checkCuenta(conex, cuentaOrigen)) {
           ingresoQuery(conex,ingreso,cuentaOrigen);
       } else {
           System.out.println("El número de cuenta no existe");
       }
    }
    public static void retirada(String[] conex) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Cual es el número de cuenta?");
        String cuentaOrigen = sc.nextLine();
        System.out.println("¿Qué cantidad desea retirar?");
        float cantidad = sc.nextFloat();

        // Comprobamos si existe el numero de cuenta
        if(checkCuenta(conex, cuentaOrigen)) {
            if(checkSaldo(conex,cuentaOrigen,cantidad)) {
                retiradaQuery(conex,cantidad,cuentaOrigen);
            } else {
                System.out.println("Estás intentando retirar más dinero del disponible");
            }
        } else {
            System.out.println("El número de cuenta no existe");
        }
    }
    public static void transferencia(String[] conex) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Cual es la cuenta de origen?");
        String cuentaOrigen = sc.nextLine();
        System.out.println("¿Cual es la cuenta de destino?");
        String cuentaDestino = sc.nextLine();
        System.out.println("¿Qué cantidad desea transferir?");
        float cantidad = sc.nextFloat();
        if(checkCuenta(conex, cuentaDestino)) {
            if (checkSaldo(conex,cuentaOrigen,cantidad)) {
                retiradaQuery(conex,cantidad,cuentaOrigen);
                ingresoQuery(conex, cantidad, cuentaDestino);
            } else {
                System.out.println("Estás intentando retirar más dinero del disponible");
            }
        } else {
            System.out.println("La cuenta no existe");
        }
    }
    public static boolean checkCuenta(String[] conex, String cuenta) throws SQLException {
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM bdbanco.cuentas WHERE NumCuenta='" + cuenta + "'");
        return rs.isBeforeFirst();
    }
    public static boolean checkSaldo(String[] conex, String cuenta, float cantidad) throws SQLException {
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT SUM(Saldo  + -" + cantidad + ") AS result " +
                "FROM cuentas WHERE NumCuenta= " + cuenta + " HAVING result > 0");
        if (!rs.isBeforeFirst()) {
            return false;
        }
        return true;
    }
    public static void ingresoQuery(String[] conex, float cantidad, String cuentaDestino) throws SQLException {
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        try (Statement s = c.createStatement()) {
            c.setAutoCommit(false);
            s.execute("UPDATE cuentas " +
                    "SET Saldo=(SELECT SUM(Saldo + " + cantidad + ") WHERE NumCuenta='" + cuentaDestino + "')" +
                    " WHERE NumCuenta='" + cuentaDestino + "'");
            // Add ingreso -> tabla Movimientos
            s.execute("INSERT INTO movimientos (concepto, cuentadestino, cantidad)" +
                    " VALUES ('ingreso'"  + ", '" + cuentaDestino + "', " + cantidad + ")");
            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
                System.err.println("Se ha realizado un rollback");
                System.out.println("Excepcion: " + e.getMessage());
            } catch (SQLException ex) {
                System.err.println("Se ha producido un error al hacer el rollback");
                System.out.println("Excepcion: " + ex.getMessage());
            }
        }
    }
    public static void retiradaQuery(String[] conex, float cantidad, String cuentaDestino) throws SQLException {
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        try (Statement s = c.createStatement()) {
            c.setAutoCommit(false);
            s.execute("UPDATE cuentas SET Saldo=(SELECT SUM(Saldo + -" + cantidad + ") " +
                    "WHERE NumCuenta='" + cuentaDestino + "') " +
                    "WHERE NumCuenta='" + cuentaDestino + "'");
            s.execute("INSERT INTO movimientos (concepto, cuentadestino, cantidad)" +
                    " VALUES ('retirada'"  + ", '" + cuentaDestino + "', " + cantidad + ")");
            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
                System.err.println("Se ha realizado un rollback");
                System.out.println("Excepcion: " + e.getMessage());
            } catch (SQLException ex) {
                System.err.println("Se ha producido un error al hacer el rollback");
                System.out.println("Excepcion: " + ex.getMessage());
            }
        }
    }
    public static void listarCuentas(String[] conex) throws SQLException {
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        Statement s = c.createStatement();
        ResultSet rs = s.executeQuery("SELECT * FROM bdbanco.cuentas");
        System.out.println("Num Titular Saldo");
        while (rs.next()) {
            String numCuenta = rs.getString("NumCuenta");
            String titular = rs.getString("Titular");
            String saldo = rs.getString("Saldo");
            System.out.println(numCuenta + " " + titular + " " + saldo);
        }
    }
    public static void balance(String[] conex) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("¿Cual es el número de cuenta?");
        String cuentaOrigen = sc.nextLine();

        // Comprobamos si existe el numero de cuenta
        if(checkCuenta(conex, cuentaOrigen)) {
            balanceQuery(conex, cuentaOrigen);
        } else {
            System.out.println("El número de cuenta no existe");
        }
    }
    public static void balanceQuery(String[] conex, String numCuenta) throws SQLException {
        Connection c = DriverManager.getConnection(conex[0], conex[1], conex[2]);
        Statement s = c.createStatement();
        // Suma ingresos
        ResultSet rsIngreso = s.executeQuery("SELECT SUM(cantidad) AS total FROM movimientos WHERE concepto = 'ingreso' AND cuentadestino= '" + numCuenta + "'");
        rsIngreso.next();
        System.out.println("La suma de todos sus ingresos es: " + rsIngreso.getString(1));
        double totalIngresos = rsIngreso.getDouble("total");
        // Suma retiradas
        ResultSet rsRetiradas = s.executeQuery("SELECT SUM(cantidad) AS total FROM movimientos WHERE concepto = 'retirada' AND cuentadestino= '" + numCuenta + "'");
        rsRetiradas.next();
        System.out.println("La suma de todas sus retiradas es: " + rsRetiradas.getString(1));
        double totalRetiradas = rsRetiradas.getDouble("total");
        // Saldo cuenta
        ResultSet rsCuenta = s.executeQuery("SELECT Saldo FROM cuentas WHERE NumCuenta= '" + numCuenta + "'");
        rsCuenta.next();
        double saldo = rsCuenta.getDouble("Saldo");
        if (totalIngresos - totalRetiradas == saldo) {
            System.out.println("El balance coincide.");
        } else {
            System.out.println("El balance no coincide.");
        }
    }
}