package Gegner;

import GUI.GUI_Package;
import Gegner.Arten.Lokal.Lokal;
import Gegner.Arten.OwnBot.OwnBot;
import Spiel.Spiel;
import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;
import util.ColPrint;

import java.util.List;

import static Gegner.Arten.Lokal.Lokal.LOKAL;
import static Gegner.Arten.OwnBot.OwnBot.OwnBot;
import static Spiel.TeilvonSpiel.Figur.BLACK;
import static Spiel.TeilvonSpiel.Figur.WHITE;

public abstract class Gegner {

    protected Spiel spiel;
    protected String farbe;
    protected String name = "KEIN_NAME";

    public Gegner(String farbe){
        this.farbe = farbe;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;

    }

    public String getName() {
        return name;
    }
    public void setName(String name){
        ColPrint.green.println("Name wurde von " + this.name + " zu " + name + " geändert");
        this.name = name;
    }
    public String getFarbe() {
        return farbe;
    }
    public boolean isWHITE(){
        return farbe.equals(WHITE);
    }
    public boolean isDran(){
        return spiel.FarbeDran().equals(farbe);
    }

    public String returnArt(){
        return this.getClass().getSimpleName();
    }

    /**
     * Diese Methode wird ausgeführt, nachdem die Art des Gegner ausgewählt wurde und bevor das Brett angezeigt wird.
     * Dabei wird z.B. der Name des Gegners eingegeben.
     * @param gui das GUI Package des Spiels
     */
    public abstract void start(GUI_Package gui);

    public abstract Zug ziehe ();

    /**
     * gibt alle möglichen Arten von Gegnern aus
     */
    public static String[] alleGegnerArten(){
        return new String[]{OwnBot, LOKAL, "-DRITTE AUSWAHL-", "-VIERTE AUSWAHL-"};
    }

    public static Gegner GegnerByString(String farbe, String art){
        return switch (art){
            case LOKAL -> new Lokal(farbe);
            case OwnBot ->  new OwnBot(farbe);
            default -> null;
        };
    }
}
