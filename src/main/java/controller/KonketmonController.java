package controller;


import dbconnection.DBCon;
import model.Konketmon;
import model.User;
import service.KonketDexService;
import service.KonketmonService;
import service.UserService;

import java.sql.*;

public class KonketmonController {
    UserService userService = new UserService();
    KonketmonService konketmonService = new KonketmonService();
    KonketDexService konketDexService = new KonketDexService();


    public KonketmonController() {
        konketmonService.initKonketmonList();
    }

    public void closeConnection() throws SQLException {
        DBCon.closeConnection();
    }

    public Konketmon findWildKonketmon() {
        return konketmonService.findWildKonketmon();
    }


    public boolean loginUser(String username, String password) throws SQLException {
        return userService.login(username, password);
    }

    public boolean registerUser(String username, String password) throws SQLException {
        return userService.register(username, password);

    }

    public boolean attackKonketmon(User user, Konketmon konketmon) {
        int power = user.getPower();
        System.out.println();
        System.out.println("=================================================");
        System.out.println(konketmon.getName() + "에게 " + power + "만큼 피해를 입혔다!");
        System.out.println("-------------------------------------------------");

        konketmon.setHP(konketmon.getHP() - power);

        if (konketmon.getHP() <= 0) {
            return false;
        } else {
            return true;
        }

    }

    public boolean attackUser(User user, Konketmon konketmon) {
        int power = konketmon.getPower();
        System.out.println();
        System.out.println();
        System.out.println("=================================================");
        System.out.println("나는 " + konketmon.getName() + "에게 " + power + "만큼 피해를 입었다!");
        System.out.println("-------------------------------------------------");

        user.setHP(user.getHP() - power);

        if (user.getHP() <= 0) {
            return false;
        } else {
            return true;
        }
    }


    public boolean catchKonketmon(Konketmon konketmon) {
        User user = userService.getUser();
        return konketDexService.catchKonketmon(user, konketmon);
    }

    public void saveData() throws SQLException {
        userService.saveData();
    }

    public void removeUser() {
        ;
        userService.deleteUser();
    }

    public void initKonketmon() {
        User user = userService.getUser();
        konketDexService.initCapturedKonketmon(user);
    }

    public int getMyKonket() {
        return konketDexService.getMyKonketDex();
    }

    public Konketmon getKonketmonSpecific(int id) {
        return konketDexService.getKonketmonSpecific(id);
    }

    public User getUser() {
        return userService.getUser();
    }

    public boolean deleteKonket(int id) {
        User user = userService.getUser();
        return konketDexService.deleteKonketmonInKonketdex(id, user);
    }

}
