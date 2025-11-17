package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {
    private static Connection conn;

    public static Connection getConnection()  {
//        String url = "jdbc:mariadb://localhost:3306/mon";
        String url = "jdbc:mariadb://192.168.0.11:3306/konketmon";
        String user = "root";
        String password = "mariadb1";

        if (conn == null) {

            try {
                conn = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
