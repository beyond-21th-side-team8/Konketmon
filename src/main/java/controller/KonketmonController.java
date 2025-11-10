package controller;


import java.sql.Connection;

public class KonketmonController {

    Connection conn;

    public KonketmonController(Connection conn) {
        this.conn = conn;
    }
}
