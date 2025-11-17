package service;

import model.Konketmon;
import model.User;
import repository.KonketDexRepository;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import static dbconnection.DBCon.getConnection;

public class KonketDexService {
    Connection con = getConnection();
    Set<Konketmon> capturedKonketmon = null;
    KonketDexRepository konketDexRepository = new KonketDexRepository();

    public boolean deleteKonketmonInKonketdex(int id, User user) {
        boolean isSuccess = konketDexRepository.deleteKonketmonInKonketDex(con, id, user.getUsername(),this.capturedKonketmon);
        if (!isSuccess) {
            System.out.println("해당 콘켓몬을 삭제하지 못했습니다");
        }
        return isSuccess;
    }

    public boolean catchKonketmon(User user,Konketmon konketmon) {
        Random rand = new Random();

        final int MAXHP = 100;
        final double BASECATCHRATE = 0.05;

        double currCatchRate;
        double currHP = konketmon.getHP();

        double hpRatio = currHP / MAXHP;
        //포획율 계산
        currCatchRate = BASECATCHRATE + (1 - hpRatio) * 0.7;

        if (currCatchRate > 0.95) currCatchRate = 0.95;

        double roll = rand.nextDouble();
        boolean isCaptured = roll < currCatchRate;

        int printCurrCatchRate = (int) Math.round(currCatchRate * 100);
        System.out.println("현재 포획 확률 ... " + printCurrCatchRate + "%");

        int prev_size = capturedKonketmon.size();

        if (isCaptured) {
            capturedKonketmon.add(konketmon);
            if (prev_size == capturedKonketmon.size()) {
                // 몬스터를 넣기 전 사이즈와 이후 사이즈가 동일하다면 중복으로 값이 들어가지 않았음을 의미
                System.out.println("이미 존재하는 콘켓몬입니다.");
            } else konketDexRepository.sendKonketDex(con, user, konketmon);
        }
        return isCaptured;
    }

    public void initCapturedKonketmon(User user) {
        this.capturedKonketmon = konketDexRepository.getMyKonketDex(con, user);
        if (capturedKonketmon == null) {
            capturedKonketmon = new HashSet<>();
        }
    }

    public int getMyKonketDex() {

        Iterator<Konketmon> iterator = this.capturedKonketmon.iterator();
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
}
