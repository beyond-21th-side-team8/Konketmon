package service;

import model.Konketmon;
import model.User;
import repository.KonketDexRepository;

import java.sql.Connection;
import java.util.Set;

import static dbconnection.DBCon.getConnection;

public class KonketDexService {
    Connection con = getConnection();
    KonketDexRepository konketDexRepository = new KonketDexRepository();
    public Set<Konketmon> getKonketdex(User user) {
        return konketDexRepository.getMyKonketDex(con, user);
    }

    public boolean deleteKonketMonInKonketdex(int id, Set<Konketmon> konketdex) {
        return konketDexRepository.deleteKonketmonInKonketDex(con, id, konketdex);
    }

    public boolean sendKonketDex(User user,Konketmon konketmon) {
        return konketDexRepository.sendKonketDex(con, user, konketmon);
    }
}
