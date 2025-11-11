package model;

import java.util.Random;

public class Monster {
    private String name;
    private String asciiArt;
    private int HP;

    public int getPower() {
        Random rand = new Random();
        return rand.nextInt(4) + 1;
    }

    public int getHP() {
        return this.HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public Monster(int HP) {
        this.HP = HP;
    }

    public Monster(String name, String asciiArt, int HP) {
        this.name = name;
        this.asciiArt = asciiArt;
        this.HP = HP;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsciiArt() {
        return asciiArt;
    }

    public void setAsciiArt(String asciiArt) {
        this.asciiArt = asciiArt;
    }
}
