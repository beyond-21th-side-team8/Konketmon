package repository;

import model.Konketmon;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KonketmonRepository {

    public List<Konketmon> initKonketmon(Connection con) {
        List<Konketmon> konketmonList = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM konketmon")
        ) {
            while (rs.next()) {
                Konketmon konketmon = new Konketmon(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)

                );
                konketmonList.add(konketmon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return konketmonList;
    }


}
