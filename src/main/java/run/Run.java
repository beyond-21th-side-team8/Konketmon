package run;

import controller.KonketmonController;
import dbconnection.DBCon;
import view.KonketmonView;

import java.sql.Connection;
import java.sql.SQLException;

public class Run {

    public static void main(String[] args) throws SQLException {
        Connection conn = DBCon.getConnection();

        KonketmonController konketController = new KonketmonController(conn);
        KonketmonView konketmonView = new KonketmonView(konketController);

        konketmonView.mainmenu();
    }
}
