package Spiel;

import GUI.MainGameAnzeige;
import Spiel.TeilvonSpiel.Feld;
import Spiel.TeilvonSpiel.Figuren.Springer;
import Spiel.TeilvonSpiel.Figuren.Turm;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Spiel.TeilvonSpiel.Feld.getFiguren;

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
        s.felder[2][4].setFigur(new Turm("w"));
        s.mga.updateBrett(s.felder);
        List<Point> möglicheZüge = new ArrayList<>();
        möglicheZüge.addAll(
                s.felder[2][4].getFigur().möglicheZüge(
                        getFiguren(s.felder),
                        null,
                        2, 4)
        );
        System.out.println(möglicheZüge);

    }
}
