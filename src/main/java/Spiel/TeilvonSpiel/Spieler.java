package Spiel.TeilvonSpiel;

import Gegner.Gegner;

import java.util.ArrayList;
import java.util.List;

public class Spieler {
    public List<Zug> züge = new ArrayList<>();
    private Gegner gegner;

    public Spieler (Gegner gegner){
        this.gegner = gegner;
    }

    public void setGegner(Gegner gegner) {
        this.gegner = gegner;
    }

    public Gegner getGegner() {
        return gegner;
    }

}
