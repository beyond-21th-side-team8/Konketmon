package model;

import java.util.Random;

public class User {

    private int HP;

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
}
