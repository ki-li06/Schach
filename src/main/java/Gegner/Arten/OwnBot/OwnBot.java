package Gegner.Arten.OwnBot;

import GUI.GUI_Package;
import Gegner.Gegner;
import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;

import java.util.List;

import static Spiel.TeilvonSpiel.Feld.getFiguren;
import static Spiel.TeilvonSpiel.Figur.AlleMöglichenZügeEinerFarbe;
import static util.Delay.delay;

/**
 * MANGELHAFT FUNKTIONSTÜCHTIG
 * wählt Züge aus, ist somit ein Gegner, berechnet aber noch keine eigenen Züge
 */

public class OwnBot extends Gegner {
    public static final String OwnBot = "Eigener Bot";

    public OwnBot (String farbe){
        super(farbe);
    }

    @Override
    public String returnArt() {
        return "OwnBot";
    }

    @Override
    public void start(GUI_Package gui) {

    }

    /**
     * @Dave hier code einfügen
     */
    @Override
    public Zug ziehe() {
        delay(1000);
        Figur[][] figuren = getFiguren(spiel.felder);
        List<Zug> weißZüge = spiel.weiß().züge;
        List<Zug> schwarzZüge = spiel.schwarz().züge;
        //Hier Zug ausgeben


        return AlleMöglichenZügeEinerFarbe(figuren, weißZüge, schwarzZüge, farbe).get(0);
    }
}
