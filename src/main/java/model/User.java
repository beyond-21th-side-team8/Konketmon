package model;

import java.util.Random;

public class User {
    private String username;
    private int HP;
    boolean is_saved;

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

    public boolean isIs_saved() {
        return is_saved;
    }

    public void setIs_saved(boolean is_saved) {
        this.is_saved = is_saved;
    }

    public User(String username, int HP, boolean is_saved) {
        this.username = username;
        this.HP = HP;
        this.is_saved = is_saved;
    }
}
