package service;

import model.Konketmon;
import repository.KonketmonRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static dbconnection.DBCon.getConnection;

public class KonketmonService {
    Connection con = getConnection();

    List<Konketmon> konketmonList = new ArrayList<>();

    KonketmonRepository konketmonRepository = new KonketmonRepository();

    public void initKonketmonList(){
        this.konketmonList =  konketmonRepository.initKonketmon(this.con);
    }

    public List<Konketmon> getKonketmonList() {
        return konketmonList;
    }
}
