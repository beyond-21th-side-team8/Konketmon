package run;

import controller.KonketmonController;
import dbconnection.DBCon;
import repository.KonketDexRepository;
import repository.KonketmonRepository;
import repository.UserRepository;
import service.KonketDexService;
import service.KonketmonService;
import service.UserService;
import view.KonketmonView;

import java.sql.Connection;
import java.sql.SQLException;

public class Run {

    public static void main(String[] args) throws SQLException {



        KonketmonController konketController = new KonketmonController();
        KonketmonView konketmonView = new KonketmonView(konketController);

        konketmonView.mainmenu();
    }
}
