package controller;


import model.Monster;
import model.User;

import java.sql.*;
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

    public boolean loginUser(String username, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE id=? AND pw=?");
        stmt.setString(1, username);
        stmt.setString(2, password);
       ResultSet rs = stmt.executeQuery();
        return rs != null;
    }

    public boolean registerUser(String username, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO user VALUES ( ?,?,?,?)");
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setInt(3, 100);
        stmt.setBoolean(4, false);
        int result = stmt.executeUpdate();
        return result == 1;
    }
}
