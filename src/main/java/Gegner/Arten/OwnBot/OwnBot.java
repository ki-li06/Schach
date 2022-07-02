package Gegner.Arten.OwnBot;

import Gegner.Gegner;
import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;

import java.util.List;

import static Spiel.TeilvonSpiel.Feld.getFiguren;

public class OwnBot extends Gegner {
    public static final String OwnBot = "Eigener Bot";

    public OwnBot (String farbe){
        super(farbe);
    }


    /**
     * @Dave hier code einfügen
     */
    @Override
    public Zug ziehe() {
        Figur[][] figuren = getFiguren(spiel.felder);
        List<Zug> weißZüge = spiel.weiß.züge;
        List<Zug> schwarzZüge = spiel.schwarz.züge;
        //Hier Zug ausgeben


        return super.ziehe();
    }
}
