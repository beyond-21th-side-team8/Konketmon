package controller;


import dbconnection.DBCon;
import model.Konketmon;
import model.User;
import repository.KonketDexRepository;
import repository.KonketmonRepository;
import repository.UserRepository;
import service.KonketDexService;
import service.KonketmonService;
import service.UserService;

import java.sql.*;
import java.util.*;

public class KonketmonController {
    Set<Konketmon> capturedKonketmon = null;
    UserService userService = new UserService();
    KonketmonService konketmonService = new KonketmonService();
    KonketDexService konketDexService = new KonketDexService();


    public KonketmonController() {
        konketmonService.initKonketmonList();
    }

    public void closeConnection() throws SQLException {
        DBCon.closeConnection();
    }

    public Konketmon findWildMonster() {
        Random rand = new Random();

        int randNum = rand.nextInt(konketmonService.getKonketmonList().size());

        return new Konketmon(konketmonService.getKonketmonList().get(randNum).getId(),
                konketmonService.getKonketmonList().get(randNum).getName(),
                konketmonService.getKonketmonList().get(randNum).getAsciiArt(),
                konketmonService.getKonketmonList().get(randNum).getHP());

    }


    public boolean loginUser(String username, String password) throws SQLException {


        return userService.login(username, password);
    }

    public boolean registerUser(String username, String password) throws SQLException {

        return userService.register(username, password);

    }

    public boolean attackMonster(User user, Konketmon konketmon) {
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


    public boolean catchMonster(Konketmon konketmon) {
        Random rand = new Random();

        final int MAXHP = 100;
        final double BASECATCHRATE = 0.05;

        double currCatchRate;
        double currHP = konketmon.getHP();

        double hpRatio = (double) currHP / MAXHP;
        //포획율 계산
        currCatchRate = BASECATCHRATE + (1 - hpRatio) * 0.7;

        if (currCatchRate > 0.95) currCatchRate = 0.95;

        double roll = rand.nextDouble();
        boolean isCaptured = roll < currCatchRate;

        int printCurrCatchRate = (int) Math.round(currCatchRate * 100);
        System.out.println("현재 포획 확률 ... " + printCurrCatchRate + "%");

        if (isCaptured) {
            capturedKonketmon.add(konketmon);
            sendKonketDex(konketmon);
        }
        return isCaptured;
    }

    public void saveData() throws SQLException {
        User user = userService.getUser();
        boolean isSuccess = userService.saveData(user);
        if (!isSuccess) {
            System.out.println("저장에 실패했습니다.");
        }
    }

    public void removeUser() {
        User user = userService.getUser();
        boolean isSuccess = userService.deleteUser(user);
        if (!isSuccess) {
            System.out.println("삭제에 실패했습니다.");
        }
    }

    public void initKonketmon() {
        User user = userService.getUser();
        this.capturedKonketmon = konketDexService.getKonketdex(user);
        if (capturedKonketmon == null) {
            capturedKonketmon = new HashSet<>();
        }
    }

    public int getMyKonket() {

        Iterator<Konketmon> iterator = capturedKonketmon.iterator();
        int i = 1;
        while (iterator.hasNext()) {
            Konketmon konketmon = iterator.next();
            System.out.println(i + ". " + konketmon.getName());
            i++;
        }

        return this.capturedKonketmon.size();
    }

    public Konketmon getKonketmonSpecific(int id) {
        Iterator<Konketmon> iterator = this.capturedKonketmon.iterator();
        int i = 1;
        while (iterator.hasNext()) {
            Konketmon konketmon = iterator.next();
            if (i == id) {
                return konketmon;
            }
            i++;
        }
        return null;

    }

    public User getUser() {
        return userService.getUser();
    }

    public boolean deleteKonket(int id) {
        boolean isSuccess = konketDexService.deleteKonketMonInKonketdex(id, capturedKonketmon);
        if (!isSuccess) {
            System.out.println("해당 콘켓몬을 삭제하지 못했습니다");
        }
        return isSuccess;
    }

    public boolean sendKonketDex(Konketmon konketmon) {
        User user = userService.getUser();
        boolean isSuccess = konketDexService.sendKonketDex(user, konketmon);
        if (!isSuccess) {
            System.out.println("해당 콘켓몬을 도감에 넣지 못했습니다.");
        }
        return isSuccess;
    }
}
