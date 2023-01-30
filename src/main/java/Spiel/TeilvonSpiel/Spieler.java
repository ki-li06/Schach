package Spiel.TeilvonSpiel;

import Gegner.Gegner;

import java.util.ArrayList;
import java.util.List;

public class Spieler {
    public String name;
    public List<Zug> züge = new ArrayList<>();
    public String farbe;
    public Gegner gegner;
    public Spieler(String name, String farbe){
        this.name = name;
        this.farbe = farbe;
    }

    public void setGegner(Gegner gegner) {
        this.gegner = gegner;
    }

    public Gegner getGegner() {
        return gegner;
    }
}
