package Spiel;

import GUI.Eingabefeld;
import GUI.MainGameAnzeige;
import GUI.MausListener;
import Spiel.TeilvonSpiel.Feld;
import Spiel.TeilvonSpiel.Figuren.*;
import Spiel.TeilvonSpiel.Zug;

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import static Spiel.TeilvonSpiel.Feld.getFiguren;
import static Spiel.TeilvonSpiel.Figur.MöglicheZüge;
import static util.FormatPoint.format;

public class Spiel {
    private String weißspieler;
    private String schwarzspieler;
    private Feld[][] felder; //x - y (siehe überblick.txt)
    private List<Zug> WeißZüge = new ArrayList<>();
    private List<Zug> SchwarzZüge = new ArrayList<>();

    private MainGameAnzeige mga;
    private MouseListener mouseListener;
    private Point ausgewählt;
    private boolean BauernAuswahl;


    public Spiel(){
        System.out.println("Namen Eingeben");
        Eingabefeld ef = new Eingabefeld();
        weißspieler = ef.getSpielername_weiß();
        schwarzspieler = ef.getSpielername_schwarz();

        System.out.println("Weiß: '" + weißspieler + "'");
        System.out.println("Schwarz: '" + schwarzspieler + "'");

        mga = new MainGameAnzeige(
                weißspieler,
                schwarzspieler,
                ef.getFrame(),
                ef.getPanel(),
                ef.getGroupLayout()
        );
        felder = new Feld[8][8];
        for (int x = 0; x < felder.length; x++) {
            for (int y = 0; y < felder[x].length; y++) {
                felder[x][y] = new Feld();
            }
        }
        for (int i = 0; i <= 7; i+= 7) {
            String farbe = i==7?"w":"b";
            felder[0][i].setFigur(new Turm(farbe));
            felder[1][i].setFigur(new Springer(farbe));
            felder[2][i].setFigur(new Läufer(farbe));
            felder[3][i].setFigur(new Dame(farbe));
            felder[4][i].setFigur(new König(farbe));
            felder[5][i].setFigur(new Läufer(farbe));
            felder[6][i].setFigur(new Springer(farbe));
            felder[7][i].setFigur(new Turm(farbe));
            System.out.println("i: " + i);
            int ybauern = i==7?6:1;
            for (int j = 0; j < 8; j++) {
                felder[j][ybauern].setFigur(new Bauer(farbe));
            }
        }
        mga.updateBrett(felder);
        mga.fügeMouseListenerhinzu(new MausListener(this));
        //mouseListener = new MausListener(this);
    }

    /**
     * diese Methode wird vom MouseListener ausgeführt, wenn auf das Spielfeld geklickt wird
     */
    public void aufFeldGeklickt(int xfeld, int yfeld){
        System.out.println("Es wurde auf das Feld " + xfeld + " | " + yfeld + " geklickt");
        ausgewählt = new Point(xfeld, yfeld);
        felder[ausgewählt.x][ausgewählt.y].setStatus(Feld.Status.AUSGEWÄHLT());
        zeigeMöglicheZüge();
        System.out.println("figur: " + felder[xfeld][yfeld].getFigur());
    }

    private void zeigeMöglicheZüge(){
        List<Point> möglich =  MöglicheZüge(getFiguren(felder), WeißZüge, SchwarzZüge, ausgewählt.x, ausgewählt.y);
        for (Point p : möglich) {
            if(felder[p.x][p.y].getFigur() == null){
                felder[p.x][p.y].setStatus(Feld.Status.MÖGLICH_ZUG());
            }
            else{
                felder[p.x][p.y].setStatus(Feld.Status.MÖGLICH_SCHLAGEN());
            }
        }
        mga.updateBrett(felder);
    }


    public boolean isGedreht() {
        System.out.println("WeißZüge: " + WeißZüge);
        System.out.println("SchwarzZüge: " + SchwarzZüge);
        return WeißZüge.size() != SchwarzZüge.size();
    }


    public static void main(String[] args) {
        System.out.println("main");
        Point ref = new Point(4, 7);
        Spiel s = new Spiel();
        s.mga = new MainGameAnzeige("s1", "s2");
        s.felder[ref.x][ref.y].setFigur(new König("w"));
        s.felder[ref.x][ref.y].setStatus(Feld.Status.AUSGEWÄHLT());
        //erstelle eine theoretische Spielsituation
        //s.felder[3][0].setFigur(new König("w"));
        s.felder[1][1].setFigur(new Turm("b"));
        s.felder[4][5].setFigur(new Läufer("b"));
        //s.felder[7][1].setFigur(new Turm("b"));

        //zeige sie an
        s.mga.updateBrett(s.felder);
        List<Point> möglicheZüge = new ArrayList<>(
                s.felder[ref.x][ref.y].getFigur().möglicheZüge(
                    getFiguren(s.felder),
                    new ArrayList<>(),
                        new ArrayList<>(),
                        ref.x, ref.y));

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
        System.out.println("Mögliche Züge für " + format(ref) + ": " + format(möglicheZüge));
        System.out.println(möglicheZüge.size() + " mögliche Züge");

    }
}
