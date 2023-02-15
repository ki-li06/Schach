import GUI.Teile.StartInterface;
import Spielertypen.Spielertyp;
import Spiel.Spiel;

import static Spiel.TeilvonSpiel.Figur.andereFarbe;

public class Main {
    public static void main(String[] args) {
        StartInterface si = new StartInterface();
        Spielertyp gegner = Spielertyp.GegnerByString(
                andereFarbe(si.getFarbe()),
                si.getGegnerArt());
        System.out.println("Name: " + si.getSpielerName());
        System.out.println("Gegner-Art: " + gegner.returnArt());
        Spiel s = new Spiel(si.getSpielerName(), gegner, si.getGUI_Package());

    }
}
