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

    public boolean saveData(User user) {
        return userRepository.saveData(this.con, user);
    }

    public boolean deleteUser(User user) {
        return userRepository.removeUser(this.con, user);
    }

    public User getUser() {
        return user;
    }
}
