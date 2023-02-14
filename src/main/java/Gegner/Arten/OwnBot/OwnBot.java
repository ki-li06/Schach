package Gegner.Arten.OwnBot;

import GUI.GUI_Package;
import Gegner.Spielertyp;
import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;
import util.ColPrint;

import java.util.List;

import static Spiel.TeilvonSpiel.Feld.getFiguren;
import static util.Delay.delay;

/**
 * MANGELHAFT FUNKTIONSTÜCHTIG
 * wählt Züge aus, ist somit ein Gegner, berechnet aber noch keine eigenen Züge
 */

public class OwnBot extends Spielertyp {
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
        ColPrint.cyan.println("OwnBot start");

        setName(farbe + "_OwnBot");
    }


    @Override
    public Zug ziehe() {

        delay(500);
        Figur[][] figuren = getFiguren(spiel.felder);
        List<Zug> weißZüge = spiel.SpielerWeiß().züge;
        List<Zug> schwarzZüge = spiel.SpielerSchwarz().züge;
        //Hier Zug ausgeben
        Berechnung berechnung = new Berechnung(farbe, figuren, weißZüge, schwarzZüge);



        return berechnung.getBesterZug();
    }
}
