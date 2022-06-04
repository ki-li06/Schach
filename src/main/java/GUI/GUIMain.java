package GUI;

import GUI.Teile.Eingabefeld;
import GUI.Teile.MainGameAnzeige;
import Spiel.TeilvonSpiel.Feld;
import Spiel.TeilvonSpiel.Figuren.Dame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static util.Delay.delay;
import static util.Random.random;

public class GUIMain {
    public static void main(String[] args) {
        Eingabefeld e = new Eingabefeld();
        System.out.println("Weiß: " + e.getSpielername_weiß());
        System.out.println("Schwarz: " + e.getSpielername_schwarz());
        MainGameAnzeige mga = new MainGameAnzeige(
                e.getSpielername_weiß(),
                e.getSpielername_schwarz(),
                e.getFrame(),
                e.getPanel(),
                e.getGroupLayout());
        //test
        delay(2000L);
        Feld[][] felder = new Feld[8][8];
        for (int i1 = 0; i1 < 8; i1++) {
            for (int i2 = 0; i2 < 8; i2++) {
                String farbe = "w";
                if(random(0, 1) == 1){
                    farbe = "b";
                }
                felder[i1][i2] = new Feld();
                felder[i1][i2].setFigur(new Dame(farbe));
            }
        }
        felder[3][4].setStatus(Feld.Status.MOVE());
        mga.updateBrett(felder, false);
        //Kommentar



        System.out.println("ENDE GUI-MAIN");
    }
}
