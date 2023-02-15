package Spielertypen.Arten.Stockfish;

import GUI.GUI_Package;
import Spielertypen.Spielertyp;
import Spiel.TeilvonSpiel.Zug;

/**
 * NICHT FUNKTIONSTÜCHTIG
 */


public class StockfishGegner extends Spielertyp {


    public StockfishGegner(String farbe) {
        super(farbe);
    }

    /**
     * z.B. Auswahl der Spielstärke
     * @param gui das GUI Package des Spiels
     */
    @Override
    public void start(GUI_Package gui) {

    }

    @Override
    public Zug ziehe() {
        return null;
    }
}
