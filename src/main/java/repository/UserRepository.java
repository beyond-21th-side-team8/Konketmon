package repository;

import model.User;

import java.sql.*;

public class UserRepository {

    public User login(Connection con, String username, String password) {
        User user = null;
        try (PreparedStatement pstmt = con.prepareStatement("SELECT * FROM user WHERE id=? AND pw=?")) {


            pstmt.setString(1, username);
            pstmt.setString(2, password);


            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    user = new User(rs.getString(1), rs.getInt(3), rs.getBoolean(4));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User register(Connection con, String username, String password) {
        User user = null;
        try (PreparedStatement pstmt = con.prepareStatement("INSERT INTO user VALUES ( ?,?,?,?)")) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, 100);
            pstmt.setBoolean(4, false);
            int result = pstmt.executeUpdate();

            if (result > 0) {
                user = new User(username, 100, false);
            }
        } catch(SQLIntegrityConstraintViolationException e){
            System.out.println("중복된 아이디가 존재합니다.");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public boolean saveData(Connection con, User user) {
        int result = 0;
        try (PreparedStatement pstmt = con.prepareStatement("UPDATE user SET HP = ?, is_saved = TRUE where id = ?")) {

            pstmt.setInt(1, user.getHP());
            pstmt.setString(2, user.getUsername());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        return result > 0;
    }

    public boolean removeUser(Connection con, User user) {
        int result = 0;
        try (PreparedStatement pstmt = con.prepareStatement("DELETE FROM user WHERE id = ?")) {
            pstmt.setString(1, user.getUsername());
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            new RuntimeException(e);
        }
        return result > 0;
    }
}
