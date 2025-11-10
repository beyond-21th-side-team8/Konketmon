package controller;


import java.sql.Connection;
import java.sql.SQLException;

public class KonketmonController {

    Connection conn;

    public KonketmonController(Connection conn) {
        this.conn = conn;
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

}
