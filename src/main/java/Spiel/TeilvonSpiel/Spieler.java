package Spiel.TeilvonSpiel;

import Gegner.Spielertyp;

import java.util.ArrayList;
import java.util.List;

public class Spieler {
    public List<Zug> züge = new ArrayList<>();
    private Spielertyp gegner;

    public Spieler (Spielertyp gegner){
        this.gegner = gegner;
    }

    public void setGegner(Spielertyp gegner) {
        this.gegner = gegner;
    }

    public Spielertyp getGegner() {
        return gegner;
    }

}
