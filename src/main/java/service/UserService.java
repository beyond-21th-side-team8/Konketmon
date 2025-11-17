package service;

import model.User;
import repository.UserRepository;

import java.sql.Connection;

import static dbconnection.DBCon.getConnection;

public class UserService {
    Connection con = getConnection();
    User user = null;
    UserRepository userRepository = new UserRepository();

    public boolean login(String username, String password) {
        this.user = userRepository.login(this.con, username, password);
        return user != null;
    }

    public boolean register(String username, String password) {
        this.user = userRepository.register(this.con, username, password);
        return user != null;
    }

    public void saveData() {
        boolean isSuccess = userRepository.saveData(this.con, this.user);
        if (!isSuccess) {
            System.out.println("저장에 실패했습니다.");
        }
    }

    public void deleteUser() {
        boolean isSuccess = userRepository.removeUser(this.con, this.user);
        if (!isSuccess) {
            System.out.println("삭제에 실패했습니다.");
        }
    }

    public User getUser() {
        return user;
    }
}
