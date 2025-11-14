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
                rs.getInt(4),
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

        return new Monster(monsterSet.get(randNum).getId(),monsterSet.get(randNum).getName(),monsterSet.get(randNum).getAsciiArt(),monsterSet.get(randNum).getHP());

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
            this.user = new User(rs.getString(1),rs.getInt(3),rs.getBoolean(4));
        }

        return this.user != null;
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


    public boolean catchMonster(Monster monster) {
        Random rand = new Random();

        final int MAXHP = 100;
        final double BASECATCHRATE = 0.05;

        double currCatchRate;
        double currHP = monster.getHP();

        double hpRatio = (double) currHP / MAXHP;
        //포획율 계산
        currCatchRate = BASECATCHRATE + (1-hpRatio)*0.7;

        if (currCatchRate>0.95) currCatchRate = 0.95;

        double roll = rand.nextDouble();
        boolean isCaptured = roll < currCatchRate;

        int printCurrCatchRate = (int) Math.round(currCatchRate*100);
        System.out.println("현재 포획 확률 ... " + printCurrCatchRate + "%");

        if (isCaptured) {
            capturedMonster.add(monster);
            int result = sendKonketDex(monster);
        }
        return isCaptured;
    }

    public void saveData(User user) throws SQLException {
        String sql = "UPDATE user SET HP = ?, is_saved = TRUE where id = ?";
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getHP());
            pstmt.setString(2, user.getUsername());

            pstmt.executeUpdate();
        }catch (SQLException e){
            new RuntimeException(e);
        }
        finally {
            pstmt.close();
        }
    }

    public void removeUser(User user) {
        String sql = "DELETE FROM user WHERE id = ?";
        PreparedStatement pstmt = null;
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.user.getUsername());
            pstmt.executeUpdate();
        }catch (SQLException e){
            new RuntimeException(e);
        }
    }

    public void initKonketmon(){
        PreparedStatement stmt = null;
        String sql = "SELECT konketmon.id, konketmon.name, konketmon.hp, konketmon.ascii_art " +
                "from poketbox " +
                "join konketmon on konketmon.id = poketbox.konket_id " +
                "where poketbox.user_id = ?";

        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, this.user.getUsername() );
            ResultSet rset = stmt.executeQuery();
            while(rset.next()){
                Monster monster = new Monster(rset.getInt(1),rset.getString(2),rset.getString(4),rset.getInt(3));
                this.capturedMonster.add(monster);
            }
        }
        catch (SQLException e){
            new RuntimeException(e);
        }
    }

    public int getMyKonket() {

        Iterator<Monster> iterator = capturedMonster.iterator();
        int i=1;
        while(iterator.hasNext()) {
            Monster monster = iterator.next();
            System.out.println(i +". "+monster.getName());
            i++;
        }

        return this.capturedMonster.size();
    }

    public Monster getKonketmonSpecific(int id){
        Iterator<Monster> iterator = this.capturedMonster.iterator();
        int i=1;
        while(iterator.hasNext()) {
            Monster monster = iterator.next();
            if(i == id) {
                return monster;
            }
            i++;
        }
        return null;

    }

    public int deleteKonket(int id) {
        String sql = "DELETE FROM poketbox WHERE konket_id = ?";
        PreparedStatement pstmt = null;
        int result = 0;
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            result = pstmt.executeUpdate();
        }catch (SQLException e){
            new RuntimeException(e);
        }

        if (result > 0) {
            Iterator<Monster> iterator = this.capturedMonster.iterator();
            while(iterator.hasNext()) {
                Monster monster = iterator.next();
                if(monster.getId() == id) {
                    this.capturedMonster.remove(monster);
                }
            }
        }

        return result;
    }

    public int sendKonketDex(Monster monster){
        String sql = "INSERT INTO poketbox (user_id, konket_id) VALUES (?,?)";
        PreparedStatement pstmt = null;
        int result = 0;
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, this.user.getUsername());
            pstmt.setInt(2, monster.getId());
            result = pstmt.executeUpdate();
        }catch (SQLException e){
            new RuntimeException(e);
        }
        return result;
    }
}
