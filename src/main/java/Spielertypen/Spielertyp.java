package Spielertypen;

import GUI.GUI_Package;
import Spielertypen.Arten.Lokal.Lokal;
import Spielertypen.Arten.OwnBot.OwnBot;
import Spiel.Spiel;
import Spiel.TeilvonSpiel.Zug;
import util.ColPrint;

import static Spielertypen.Arten.Lokal.Lokal.LOKAL;
import static Spielertypen.Arten.OwnBot.OwnBot.OwnBot;

/**
 * Diese Klasse steht für einen Spielertyp wie zum Beispiel LokalerGegner oder Bot.
 * Diese Klasse ist der Grundbaustein für die Auswahl von verschiedenen Gegnern.
 */

public abstract class Spielertyp {

    protected Spiel spiel;
    protected String farbe;
    protected String name = "KEIN_NAME";

    public Spielertyp(String farbe){
        this.farbe = farbe;
        name = farbe + "_" + name;
    }

    /**
     * initialisieren des Attributs Spiel
     * Warum nicht im Konstruktor? In der Main.Main Methode wird erst ein Spielertyp
     * und dann das Spiel erzeugt
     * @param spiel hier wird eine wechselseitige Referenz auf das zugehörige Spiel erzeugt
     *              (die Spieler des Spiels haben schon einen Spielertyp)
     */
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
    public boolean isDran(){
        return spiel.FarbeDran().equals(farbe);
    }

    /**
     *
     * @return der Subklassenname des Spielertyp
     */
    public String returnArt(){
        return this.getClass().getSimpleName();
    }

    /**
     * Diese Methode wird ausgeführt, nachdem die Art des Gegner ausgewählt wurde und bevor das Brett angezeigt wird.
     * Dabei wird z.B. der Name des Gegners oder die Stärke des Bots eingegeben.
     * @param gui das GUI Package des Spiels (bspw. zur Eingabe von Spielernamen)
     */
    public abstract void start(GUI_Package gui);


    /**
     * Diese Methode ist das Herzstück des Spielertyp.
     * Der Spielertyp gibt einen Zug - seine Antwort - auf das Spielfeld, das er durch die
     * Referenz Spiel abrufen kann
     *
     * @return der Zug, den dieser Spielertyp spielt
     */
    public abstract Zug ziehe ();

    /**
     * gibt alle möglichen Arten von Gegnern aus
     */
    public static String[] alleGegnerArten(){
        return new String[]{LOKAL, OwnBot, "-DRITTE AUSWAHL-", "-VIERTE AUSWAHL-"};
    }

    public static Spielertyp GegnerByString(String farbe, String art){
        return switch (art){
            case LOKAL -> new Lokal(farbe);
            case OwnBot ->  new OwnBot(farbe);
            default -> null;
        };
    }
}
