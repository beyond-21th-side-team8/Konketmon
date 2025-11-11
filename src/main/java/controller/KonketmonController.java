package controller;


import model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class KonketmonController {
    Connection conn;
    User user = new User(100);

    public KonketmonController(Connection conn) {
        this.conn = conn;
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

}
