package Spiel;

import GUI.MainGameAnzeige;
import Spiel.TeilvonSpiel.Feld;
import Spiel.TeilvonSpiel.Figuren.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Spiel.TeilvonSpiel.Feld.getFiguren;
import static util.FormatPoint.format;

public class Spiel {
    private Feld[][] felder; //x - y (siehe überblick.txt)
    private MainGameAnzeige mga;

    public Spiel(){
        felder = new Feld[8][8];
        for (int x = 0; x < felder.length; x++) {
            for (int y = 0; y < felder[x].length; y++) {
                felder[x][y] = new Feld();
            }
        }
    }

    /**
     * diese Methode wird vom MouseListener ausgeführt, wenn auf das Spielfeld geklickt wird
     */
    public void aufFeldGeklickt(int xfeld, int yfeld){
        System.out.println("Es wurde auf das Feld " + xfeld + " | " + yfeld + " geklickt");
    }

    public static void main(String[] args) {
        Spiel s = new Spiel();
        s.mga = new MainGameAnzeige("s1", "s2");
        //erstelle eine theoretische Spielsituation
        s.felder[2][4].setFigur(new Turm("w"));
        s.felder[2][1].setFigur(new König("w"));
        s.felder[2][7].setFigur(new Turm("b"));
        //zeige sie an
        s.mga.updateBrett(s.felder);
        List<Point> möglicheZüge = new ArrayList<>(
                s.felder[2][4].getFigur().möglicheZüge(
                    getFiguren(s.felder),
                    null,                   //nicht relevant für den Turm
                    2, 4));

        //markiere alle Mögliche Züge
        for (Point point : möglicheZüge) {
            int x = (int) point.getX();
            int y = (int) point.getY();
            if (s.felder[x][y].getFigur() == null) {
                s.felder[x][y].setStatus(Feld.Status.MÖGLICH_ZUG());
            } else {
                s.felder[x][y].setStatus(Feld.Status.MÖGLICH_SCHLAGEN());
            }
        }
        s.mga.updateBrett(s.felder);
        System.out.println("Mögliche Züge: " + format(möglicheZüge));


    }
}
