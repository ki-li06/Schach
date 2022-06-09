package Spiel;

import GUI.BauernAuswahl.BauernAuswahlMouseListener;
import GUI.Teile.Eingabefeld;
import GUI.Teile.MainGameAnzeige;
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
import static util.ArrayPoint.get;

public class Spiel {
    private String weißspieler;
    private String schwarzspieler;
    private Feld[][] felder; //x - y (siehe überblick.txt)
    private List<Zug> WeißZüge = new ArrayList<>();
    private List<Zug> SchwarzZüge = new ArrayList<>();

    private MainGameAnzeige mga;
    private Point ausgewählt;


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
        //Setze die Figuren auf das Brett
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
            int ybauern = i==7?6:1;
            for (int j = 0; j < 8; j++) {
                felder[j][ybauern].setFigur(new Bauer(farbe));
            }
        }
        mga.updateBrett(felder);
        mga.fügeMouseListenerHinzu(new MausListener(this));
        mga.fügeMouseListenerBauernAuswahlHinzu(new BauernAuswahlMouseListener(this));
        //mouseListener = new MausListener(this);
    }

    /**
     * diese Methode wird vom MouseListener ausgeführt, wenn auf das Spielfeld geklickt wird
     */
    public void aufBrettGeklickt(int xfeld, int yfeld){
        System.out.println("Es wurde auf das Feld " + xfeld + " | " + yfeld + " geklickt");
        Point zugehörig = new Point(xfeld, yfeld);
        if(ausgewählt == null){
            ausgewählt = zugehörig;
            get(felder, ausgewählt).setStatus(Feld.Status.AUSGEWÄHLT());
            zeigeMöglicheZüge();
        }
        else if(!zugehörig.equals(ausgewählt)){
            get(felder, ausgewählt).setStatus(null);
            clearMöglicheZüge();
            ausgewählt = null;
        }
        System.out.println("ausgewählt: " + format(ausgewählt));
        System.out.println("möglicheZüge: " + format(möglicheZüge()));
        System.out.println("figur: " + felder[xfeld][yfeld].getFigur());
    }

    /**
     * diese Methode wird vom BauernAuswahlMouseListener ausgeführt, wenn auf ihn geklickt wird
     * und die BauernAuswahl angezeigt wird
     */
    public void aufBauernAuswahlGeklickt(int nummer){
        System.out.println("Spiel - BauernAuswahl geklickt: " + nummer);
    }

    /**
     * gibt alle Möglichen Züge aus
     */
    private List<Point> möglicheZüge (){
        if(ausgewählt == null){
            return new ArrayList<>();
        }
        return MöglicheZüge(getFiguren(felder), WeißZüge, SchwarzZüge, ausgewählt.x, ausgewählt.y);
    }

    /**
     * zeigt alle Möglichen Züge an (MÖGLICH_ZUG oder MÖGLICH_SCHLAGEN)
     */
    private void zeigeMöglicheZüge(){
        List<Point> möglich =  möglicheZüge();
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

    /**
     * entfernt die Anzeige aller Möglichen Züge (GegenMethode zu zeigeMöglicheZüge()
     */
    private void clearMöglicheZüge(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(felder[i][j].getStatus() != null && (
                        felder[i][j].getStatus().equals(Feld.Status.MÖGLICH_ZUG())
                        || felder[i][j].getStatus().equals(Feld.Status.MÖGLICH_SCHLAGEN()))
                    ){
                    felder[i][j].setStatus(null);
                }
            }
        }
        mga.updateBrett(felder);
    }

    public boolean BauernAuswahl(){
        return mga.BauernAuswahlSichtbar();
    }

    public boolean WeißDran() {
        /*
        System.out.println("WeißZüge: " + WeißZüge);
        System.out.println("SchwarzZüge: " + SchwarzZüge);
               */
        return WeißZüge.size() == SchwarzZüge.size();
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
