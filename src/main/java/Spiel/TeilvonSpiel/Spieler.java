package Spiel.TeilvonSpiel;

import java.util.ArrayList;
import java.util.List;

public class Spieler {
    public String name;
    public List<Zug> züge = new ArrayList<>();
    public Spieler(String name){
        this.name = name;
    }
}
