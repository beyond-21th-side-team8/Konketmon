package model;

import java.util.Random;

public class Monster {

    private int HP;

    public int getPower() {
        Random rand = new Random();
        return rand.nextInt(4) + 1;
    }

    public int getHP () {
        return this.HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
    public Monster(int HP) {
        this.HP = HP;
    }
}
