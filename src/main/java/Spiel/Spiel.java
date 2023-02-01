package Spiel;

import GUI.GUI_Package;
import GUI.Teile.MainGameAnzeige;
import Gegner.Arten.Lokal.Lokal;
import Gegner.Gegner;
import Spiel.TeilvonSpiel.*;
import Spiel.TeilvonSpiel.Figuren.*;
import util.ColPrint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Spiel.TeilvonSpiel.Feld.getFiguren;
import static Spiel.TeilvonSpiel.Feld.setFiguren;
import static Spiel.TeilvonSpiel.Figur.BLACK;
import static Spiel.TeilvonSpiel.Figur.WHITE;
import static Spiel.TeilvonSpiel.Figur.andereFarbe;
import static Spiel.TeilvonSpiel.Figur.FarbeAusgeschrieben;
import static Spiel.TeilvonSpiel.Figur.MöglicheZüge;
import static Spiel.TeilvonSpiel.Figur.SchachAuf;
import static Spiel.TeilvonSpiel.Figur.ToteStellung;
import static Spiel.TeilvonSpiel.Figur.ImMatt;
import static Spiel.TeilvonSpiel.Figur.ImPatt;
import static util.ArrayPoint.get;
import static util.ArrayPoint.indexOf;
import static util.Delay.delay;
import static util.FormatPoint.format;
import static util.Listen.getLast;

public class Spiel {
    public final Spieler selbst;
    public final Spieler gegner;
    public Feld[][] felder; //x - y (siehe überblick.txt)
    private Ende ende;


    public final MainGameAnzeige mga;


    public Spiel(String spielername, Gegner g, GUI_Package gui) {
        g.setSpiel(this);

        Lokal lokal = new Lokal(andereFarbe(g.getFarbe()));
        lokal.setName(spielername);
        lokal.setSpiel(this);
        lokal.addMouseListeners();

        selbst = new Spieler(spielername, g.getFarbe());
        selbst.setGegner(lokal);

        gegner = new Spieler(g.getName(), andereFarbe(g.getFarbe()));
        gegner.setGegner(g);

        g.start(gui);


        System.out.println("Weiß: '" + selbst.name + "'");
        System.out.println("Schwarz: '" + gegner.name + "'");


        mga = new MainGameAnzeige(
                selbst.name,
                gegner.name,
                gui
        );

        erstelleFelder();

        updateBrett();

        while(ende == null){
            System.out.println("FarbeDran: " + FarbeAusgeschrieben(FarbeDran()));
            Zug zug;
            if(selbstDran()) {
                ColPrint.purple.println("selbst dran");
                zug = selbst.getGegner().ziehe();
            }
            else{
                ColPrint.purple.println("gegner dran");
                zug = gegner.getGegner().ziehe();
            }
            ziehe(zug);

        }
    }

    /**
     * In dieser Methode wird das Attribut felder initialisiert.
     */
    private void erstelleFelder() {
        felder = new Feld[8][8];
        for (int x = 0; x < felder.length; x++) {
            for (int y = 0; y < felder[x].length; y++) {
                felder[x][y] = new Feld();
            }
        }
        //Setze die Figuren auf das Brett
        for (int i = 0; i <= 7; i += 7) {
            String farbe = i == 7 ? "w" : "b";
            felder[0][i].setFigur(new Turm(farbe));
            felder[1][i].setFigur(new Springer(farbe));
            felder[2][i].setFigur(new Läufer(farbe));
            felder[3][i].setFigur(new Dame(farbe));
            felder[4][i].setFigur(new König(farbe));
            felder[5][i].setFigur(new Läufer(farbe));
            felder[6][i].setFigur(new Springer(farbe));
            felder[7][i].setFigur(new Turm(farbe));
            int ybauern = i == 7 ? 6 : 1;
            for (int j = 0; j < 8; j++) {
                felder[j][ybauern].setFigur(new Bauer(farbe));
            }
        }
    }


    /**
     * zieht den angegeben Zug, löscht die Markierungen alter Züge und markiert die neuen Felder (und Schach)
     */
    public void ziehe(Zug zug) {
        if (get(felder, zug.alt) != null) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    felder[x][y].setStatus(null);
                }
            }
            updateBrett();
            ColPrint.blue.println("ziehe den zug : " + zug);
            setFiguren(felder, zug.ziehe(getFiguren(felder)));
            if (selbstDran()) {
                selbst.züge.add(zug);
            } else {
                gegner.züge.add(zug);
            }
            System.out.println("Zug gezogen");
            SchachCheck(FarbeDran());
            markiereLetztenZug();
            EndeCheck();
        }
        else{
            ColPrint.red.println("FEHLER - UNGÜLTIGER ZUG: " + zug);
        }
    }

    /**
     * dreht das Brett in der MainGameAnzeige
     */
    public void dreheBrett() {
        mga.dreheBrett(felder);
    }

    public void updateBrett(){
        mga.updateBrett(felder);
    }

    /**
     * überprüft ob ein Schach (Königsangriff) vorliegt und markiert dieses auf dem Brett
     *
     * @param farbe die Farbe, bei der das Schach überprüft werden soll (angegriffene Farbe)
     */
    public void SchachCheck(String farbe) {
        Point KönigPosition = indexOf(getFiguren(felder), new König(farbe));
        if (SchachAuf(getFiguren(felder), weiß().züge, schwarz().züge, farbe)) {
            get(felder, KönigPosition).setStatus(Feld.Status.SCHACH());
            updateBrett();
        }
    }

    /**
     * überprüft ob das Ende des Spiels vorliegt und stellt es in der GUI dar
     * Mögliche Enden sind:
     * - Matt
     * - Remis
     */
    private void EndeCheck() {
        Ende e = ende(getFiguren(felder), weiß(), schwarz());
        if (e != null) {
            ende = e;
            mga.setzeEnde(ende);
            ColPrint.green.println("ENDE");
        }
    }

    /**
     * gibt alle Möglichen Züge aus;
     * SonderRegelung Bauern:
     * Wenn ein Bauer auf die Grundreihe ziehen kann, wird dies in dieser Methode hinzugefügt.
     */
    public List<Point> möglicheZüge(Point ausgewählt) {
        List<Point> ausgabe = new ArrayList<>();
        if (ausgewählt == null) {
            return ausgabe;
        }
        //Weißer Bauer
        if (get(felder, ausgewählt).getFigur().equals(new Bauer("w")) && ausgewählt.y == 1) {
            if (felder[ausgewählt.x][0].getFigur() == null) {
                ausgabe.add(new Point(ausgewählt.x, 0));
            }
            if (ausgewählt.x > 0 && null != felder[ausgewählt.x - 1][0].getFigur() &&
                    felder[ausgewählt.x - 1][0].getFigur().getFarbe().equals(BLACK)) {
                ausgabe.add(new Point(ausgewählt.x - 1, 0));
            }
            if (ausgewählt.x < 7 && null != felder[ausgewählt.x + 1][0].getFigur() &&
                    felder[ausgewählt.x + 1][0].getFigur().getFarbe().equals(BLACK)) {
                ausgabe.add(new Point(ausgewählt.x + 1, 0));
            }
        }
        //Schwarzer Bauer
        else if (get(felder, ausgewählt).getFigur().equals(new Bauer("b")) && ausgewählt.y == 6) {
            if (felder[ausgewählt.x][7].getFigur() == null) {
                ausgabe.add(new Point(ausgewählt.x, 7));
            }
            if (ausgewählt.x > 0 && null != felder[ausgewählt.x - 1][7].getFigur() &&
                    felder[ausgewählt.x - 1][7].getFigur().getFarbe().equals(WHITE)) {
                ausgabe.add(new Point(ausgewählt.x - 1, 7));
            }
            if (ausgewählt.x < 7 && felder[ausgewählt.x + 1][7].getFigur() != null &&
                    felder[ausgewählt.x + 1][7].getFigur().getFarbe().equals(WHITE)) {
                ausgabe.add(new Point(ausgewählt.x + 1, 7));
            }
        }
        ausgabe.addAll(MöglicheZüge(getFiguren(felder), weiß().züge, schwarz().züge, ausgewählt.x, ausgewählt.y));
        return ausgabe;
    }

    /**
     * zeige alle Mögliche Züge für den ausgewählten Punkt aus
     */
    public void zeigeMöglicheZüge(Point ausgewählt) {
        ColPrint.white.println("zeigeMöglicheZüge");
        List<Point> möglich = möglicheZüge(ausgewählt);
        for (Point p : möglich) {
            if (p.y >= 0 && p.y < 8) {
                if (felder[p.x][p.y].getFigur() == null) {
                    felder[p.x][p.y].setStatus(Feld.Status.MÖGLICH_ZUG());
                } else {
                    felder[p.x][p.y].setStatus(Feld.Status.MÖGLICH_SCHLAGEN());
                }
            }
        }
        get(felder, ausgewählt).setStatus(Feld.Status.AUSGEWÄHLT());
        updateBrett();
    }

    /**
     * entfernt die Anzeige aller Möglichen Züge (GegenMethode zu zeigeMöglicheZüge()
     */
    public void clearMöglicheZüge() {
        ColPrint.white.println("clearMöglicheZüge");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Feld.Status status = felder[i][j].getStatus();
                if(status != null &&
                            (status.equals(Feld.Status.MÖGLICH_ZUG())
                         ||  status.equals(Feld.Status.MÖGLICH_SCHLAGEN())
                         ||  status.equals(Feld.Status.AUSGEWÄHLT()))) {
                    felder[i][j].setStatus(null);
                }
            }
        }
        markiereLetztenZug();
    }

    /**
     * Markiert den letzten Zug des Spieles mit dem entsprechenden Status
     * (löst auch updateBrett() aus)
     */
    private void markiereLetztenZug() {
        if (selbst.züge.size() > 0) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    if (felder[x][y].getStatus() != null &&
                            felder[x][y].getStatus().equals(Feld.Status.ZUG()))
                    felder[x][y].setStatus(null);
                }
            }
            updateBrett();
            Zug letzterZug;
            if(dran(selbst)){
                letzterZug = getLast(gegner.züge);
            }
            else{
                letzterZug = getLast(selbst.züge);
            }
            get(felder, letzterZug.neu).setStatus(Feld.Status.ZUG());
            get(felder, letzterZug.alt).setStatus(Feld.Status.ZUG());
        }
        updateBrett();
    }


    /**
     * gibt aus ob Spieler Weiß dran ist
     */
    public boolean WeißDran() {
        return weiß().züge.size() == schwarz().züge.size();
    }

    public boolean selbstDran() {
        return selbst.getGegner().getFarbe().equals(FarbeDran());
    }

    /**
     * gibt an welche Farbe gerade dran ist
     */
    public String FarbeDran() {
        return WeißDran() ? WHITE : BLACK;
    }

    public Spieler weiß(){
        if(selbst.farbe.equals(WHITE)){
            return selbst;
        }
        else{
            return gegner;
        }
    }

    public Spieler schwarz(){
        if(selbst.farbe.equals(BLACK)){
            return selbst;
        }
        else{
            return gegner;
        }
    }

    public boolean dran(Spieler g){
        if(!g.equals(selbst) && !g.equals(gegner)){
            return false;
        }
        return FarbeDran().equals(g.farbe);
    }

    /**
     * gibt, falls möglich, die Koordinaten des Feldes mit dem Status BAUERNUMWANDLUNG aus
     */
    public Point indexOfBauernUmwandlung() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (felder[x][y].getStatus() != null &&
                        felder[x][y].getStatus().equals(Feld.Status.BAUERNUMWANDLUNG())) {
                    return new Point(x, y);
                }
            }
        }
        return new Point(-1, -1);
    }

    private static boolean ZugWiederholungGreift(List<Zug> weißZüge, List<Zug> schwarzZüge){
        if(schwarzZüge.size() < 6){
            return false;
        }
        List<Zug> last6BLACK = getLast(schwarzZüge, 6);
        List<Zug> last6WHITE = getLast(weißZüge, 6);

        if(last6BLACK.get(0).equals(last6BLACK.get(2))
                && last6BLACK.get(2).equals(last6BLACK.get(4))
                && last6BLACK.get(1).equals(last6BLACK.get(3))
                && last6BLACK.get(3).equals(last6BLACK.get(5))){
            return true;
        }
        return last6WHITE.get(0).equals(last6WHITE.get(2))
                && last6WHITE.get(2).equals(last6WHITE.get(4))
                && last6WHITE.get(1).equals(last6WHITE.get(3))
                && last6WHITE.get(3).equals(last6WHITE.get(5));
    }

    /**
     * gibt das Ende nach dem Spielstand des angegebenen Spiels aus
     * (Standard-Ausgabe null)
     *
     */
    public static Ende ende(Figur[][] figuren, Spieler weiß, Spieler schwarz) {
        if(weiß.züge.size() == 0 || schwarz.züge.size() == 0){
            return null;
        }
        if (
                weiß.züge.size() >= 50 && schwarz.züge.size() >= 50 &&
                        !getLast(weiß.züge, 50).stream().map(Zug::FigurGeschlagen).toList().contains(true) &&
                        !getLast(schwarz.züge, 50).stream().map(Zug::FigurGeschlagen).toList().contains(true)
        ) {
            return new Ende.Remis();
        }
        String farbeDran = weiß.züge.size()==schwarz.züge.size()?WHITE:BLACK;

        if(ZugWiederholungGreift(weiß.züge, schwarz.züge)){
            return new Ende.Remis();
        }
        if (ToteStellung(figuren)) {
            return new Ende.Remis();
        }
        if (farbeDran.equals(BLACK) && ImPatt(figuren, weiß.züge, schwarz.züge, BLACK)) {
            return new Ende.Patt(schwarz.name);
        }
        if (farbeDran.equals(WHITE) && ImPatt(figuren, weiß.züge, schwarz.züge, WHITE)) {
            return new Ende.Patt(weiß.name);
        }
        if (ImMatt(figuren, weiß.züge, schwarz.züge, BLACK)) {
            return new Ende.Matt(weiß.name);
        }
        if (ImMatt(figuren, weiß.züge, schwarz.züge, WHITE)) {
            return new Ende.Matt(schwarz.name);
        }
        return null;
    }

}