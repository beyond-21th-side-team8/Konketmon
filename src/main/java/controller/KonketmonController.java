package controller;


import model.Monster;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class KonketmonController {
    Connection conn;
    Set<Monster> monsterSet = new HashSet<Monster>();
    User user = new User(100);

    public KonketmonController(Connection conn) throws SQLException {
        this.conn = conn;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM konketmon");
        while (rs.next()) {
            Monster monster = new Monster(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getInt(3)
            );
            monsterSet.add(monster);
        }
    }

    public void getMonster() throws SQLException {
        Iterator<Monster> iterator = monsterSet.iterator();
        while(iterator.hasNext()) {
            Monster monster = iterator.next();
            System.out.println(monster);
        }
    }

    public void closeConnection() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public Monster findWildMonster() {
        return new Monster(11);
    }

    public boolean loginUser(String username, String password) {
        return false;
    }

    public boolean registerUser(String username, String password) {
        return false;
    }
}
