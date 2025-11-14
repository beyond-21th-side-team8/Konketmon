package repository;

import model.Konketmon;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class KonketDexRepository {

    public Set<Konketmon> getMyKonketDex(Connection con, User user) {
        Set<Konketmon> myKonketDex = new HashSet<>();
        String sql = "SELECT konketmon.id, konketmon.name, konketmon.hp, konketmon.ascii_art " +
                "from konketdex " +
                "join konketmon on konketmon.id = konketdex.konket_id " +
                "where konketdex.user_id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());

            try (ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    Konketmon konketmon = new Konketmon(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getInt(3));
                    myKonketDex.add(konketmon);
                }
            }
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        return myKonketDex;
    }

    public boolean deleteKonketmonInKonketDex(Connection con, int id, Set<Konketmon> myKonketDex) {
        String sql = "DELETE FROM konketdex WHERE konket_id = ?";
        int result = 0;
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            new RuntimeException(e);
        }

        if (result > 0) {
            Iterator<Konketmon> iterator = myKonketDex.iterator();
            while (iterator.hasNext()) {
                Konketmon konketmon = iterator.next();
                if (konketmon.getId() == id) {
                    myKonketDex.remove(konketmon);
                }
            }
        }

        return result > 0;
    }

    public boolean sendKonketDex(Connection con, User user, Konketmon konketmon) {
        String sql = "INSERT INTO konketdex (user_id, konket_id) VALUES (?,?)";
        int result = 0;
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setInt(2, konketmon.getId());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        return result == 1;
    }
}
