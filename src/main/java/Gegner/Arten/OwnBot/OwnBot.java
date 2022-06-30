package Gegner.Arten.OwnBot;

import Gegner.Gegner;
import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;

import java.util.List;

public class OwnBot extends Gegner {
    public static final String OwnBot = "Eigener Bot";

    public OwnBot (String farbe){
        super(farbe);
    }


    /**
     * @Dave hier code einfügen
     */
    @Override
    public Zug ziehe(Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge) {

        //Hier Zug ausgeben


        return super.ziehe(figuren, WeißZüge, SchwarzZüge);
    }
}
