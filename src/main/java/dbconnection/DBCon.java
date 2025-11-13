package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {
    private static Connection conn;

    public static Connection getConnection() throws SQLException {
//        String url = "jdbc:mariadb://localhost:3306/mon";
        String url = "jdbc:mariadb://192.168.0.11:3306/konketmon";
        String user = "root";
        String password = "mariadb1";

        if (conn == null) {
            conn = DriverManager.getConnection(url, user, password);
        }
        return conn;
    }

    public static void closeConnection() throws SQLException {
        if (conn != null) {
            if (!conn.isClosed()) {
                conn.close();
            }
        }
    }
}
