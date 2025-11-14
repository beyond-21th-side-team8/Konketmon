package model;

import java.util.Random;

public class Konketmon {
    private int id;
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

    public Konketmon(int HP) {
        this.HP = HP;
    }

    public Konketmon(String name, String asciiArt, int HP) {
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

    public Konketmon(int id, String name, String asciiArt, int HP) {
        this.id = id;
        this.name = name;
        this.asciiArt = asciiArt;
        this.HP = HP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Monster{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", asciiArt='" + asciiArt + '\'' +
                ", HP=" + HP +
                '}';
    }
}
