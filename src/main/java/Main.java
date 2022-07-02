import GUI.Teile.StartInterface;
import Gegner.Gegner;
import Spiel.Spiel;
import util.ColPrint;

import static Spiel.TeilvonSpiel.Figur.BLACK;

public class Main {
    public static void main(String[] args) {
        StartInterface si = new StartInterface();
        ColPrint.red.println("Ende StartInterface");
        Gegner gegner = Gegner.GegnerByString(BLACK, si.getGegnerArt());
        System.out.println("Name: " + si.getSpielerName());
        System.out.println("Art: " + gegner.returnArt());
        ColPrint.red.println("Erstelle Spiel");
        Spiel s = new Spiel(si.getSpielerName(), gegner, si.getGUI_Package());

    }
}
