package controller;


import model.Monster;
import model.User;

import java.sql.*;
import java.util.*;

public class KonketmonController {
    Connection conn;
    List<Monster> monsterSet = new ArrayList<Monster>();
    User user = null;
    Set<Monster> capturedMonster = new HashSet<>();

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
        Random rand = new Random();

        int randNum = rand.nextInt(monsterSet.size());

        return new Monster(monsterSet.get(randNum).getName(),monsterSet.get(randNum).getAsciiArt(),monsterSet.get(randNum).getHP());
        
    }

    public User getUser() {
        return this.user;
    }


    public boolean loginUser(String username, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE id=? AND pw=?");
        stmt.setString(1, username);
        stmt.setString(2, password);
       ResultSet rs = stmt.executeQuery();
       while (rs.next()) {
           user = new User(rs.getString(1),rs.getInt(3),rs.getBoolean(4));
       }
        return rs != null;
    }

    public boolean registerUser(String username, String password) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO user VALUES ( ?,?,?,?)");
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setInt(3, 100);
        stmt.setBoolean(4, false);
        int result = stmt.executeUpdate();
        user = new User(username,100,false);
        return result == 1;
    }

    public boolean attackMonster(User user, Monster monster) {
        int power = user.getPower();
        System.out.println();
        System.out.println("=================================================");
        System.out.println(monster.getName()+ "에게 " + power +"만큼 피해를 입혔다!");
        System.out.println("-------------------------------------------------");

        monster.setHP(monster.getHP() - power);

        if(monster.getHP() <= 0) {
            return false;
        }
        else{
            return true;
        }

    }

    public boolean attackUser(User user, Monster monster) {
        int power = monster.getPower();
        System.out.println();
        System.out.println();
        System.out.println("=================================================");
        System.out.println("나는 " + monster.getName() + "에게 "+ power +"만큼 피해를 입었다!");
        System.out.println("-------------------------------------------------");

        user.setHP(user.getHP() - power);

        if(user.getHP() <= 0) {
            return false;
        }
        else{
            return true;
        }
    }


    public boolean catchMonster(User user, Monster monster) {
        Random rand = new Random();

        final int MAXHP = 100;
        final double BASECATCHRATE = 0.05;

        double currCatchRate;
        double currHP = user.getHP();

        double hpRatio = (double) currHP / MAXHP;
        //포획율 계산
        currCatchRate = BASECATCHRATE + (1-hpRatio)*0.9;

        if (currCatchRate>0.95) currCatchRate = 0.95;

        double roll = rand.nextDouble();
        boolean isCaptured = roll < currCatchRate;

        return isCaptured;
    }
}
