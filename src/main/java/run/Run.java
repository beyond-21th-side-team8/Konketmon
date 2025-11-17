package run;

import controller.KonketmonController;
import view.KonketmonView;
import java.sql.SQLException;

public class Run {

    public static void main(String[] args) throws SQLException {



        KonketmonController konketController = new KonketmonController();
        KonketmonView konketmonView = new KonketmonView(konketController);

        konketmonView.mainmenu();
    }
}
