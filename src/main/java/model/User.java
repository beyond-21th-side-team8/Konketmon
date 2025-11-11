package model;

import java.util.Random;

public class User {
    private String username;
    private int HP;
    boolean isSaved;

    public int getPower() {
        Random rand = new Random();
        return rand.nextInt(4) + 1;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public User(int HP) {
        this.HP = HP;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(String username, int HP, boolean isSaved) {
        this.username = username;
        this.HP = HP;
        this.isSaved = isSaved;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
