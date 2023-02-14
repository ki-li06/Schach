package Spiel;

import GUI.GUI_Package;
import GUI.Teile.MainGameAnzeige;
import Gegner.Arten.Lokal.Lokal;
import Gegner.Spielertyp;
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
import static util.FormatPoint.format;
import static util.Listen.getLast;

public class Spiel {
    public final Spieler host;
    public final Spieler gegner;
    public Feld[][] felder; //x - y (siehe überblick.txt)
    private Ende ende;


    public final MainGameAnzeige mga;


    public Spiel(String spielername, Spielertyp geg, GUI_Package gui) {
        ColPrint.red.println("Erstelle Spiel");

        geg.setSpiel(this);
        
        Lokal lokal = new Lokal(andereFarbe(geg.getFarbe()));
        lokal.setName(spielername);
        lokal.setSpiel(this);
        lokal.addMouseListeners();
        /*
            Dies ist das Spielertyp-Objekt des Hosts.
            Da dieser nicht direkt in der Klasse Spiel programmiert wird, wird ein LokalerGegner benutzt.
            Dies ermöglicht die Eingabe von Zügen per Klicken auf das Spielfeld.
            Jedoch darf hier nicht die Methode .start(...) eines jeden Gegners ausgeführt werden;
             dies macht einerseits keinen Sinn, weil hier der Spielername des Hosts schon im StartInterface eingegeben wurde
             und andererseits würde ein solcher Aufruf zu Abrufen von noch nicht initialisierten Objekten führen
             (-> NullPointerException)
         */

        host = new Spieler(lokal);

        gegner = new Spieler(geg);

        gegner.getGegner().start(gui);
        /*
            Hier muss die Methode start(...) des Gegners ausgeführt werden
         */


        System.out.println("Weiß: '" + SpielerWeiß().getGegner().getName() + "'");
        System.out.println("Schwarz: '" + SpielerSchwarz().getGegner().getName() + "'");


        mga = new MainGameAnzeige(
                SpielerWeiß().getGegner().getName(),
                SpielerSchwarz().getGegner().getName(),
                gui
        );

        erstelleFelder();

        if(host.getGegner().getFarbe().equals(BLACK)
            && !(gegner.getGegner() instanceof Lokal)){
            dreheBrett();
        }
        {
            updateBrett();
        }


        while(ende == null){
            /*
             * spiele solange bis ein Ende existiert -> EndeCheck() -> bspw. hat jemand gewonnen
             * dabei wird der Spieler der dran ist, immer abgewechselt, weil der letzte Zug
             * entweder zu den Zügen des Hosts oder des Gegners hinzugefügt wird
             */
            System.out.println("FarbeDran: " + FarbeAusgeschrieben(FarbeDran()));
            Zug zug;
            if(HostDran()) {
                ColPrint.purple.println("selbst dran");
                zug = host.getGegner().ziehe();
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
     * -> Die Figuren werden auf ihre Position gesetzt
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
            String farbe = i == 7 ? WHITE : BLACK;
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
            ColPrint.blue.println("ziehe den zug : " + zug);
            setFiguren(felder, zug.ziehe(getFiguren(felder)));
            if (HostDran()) {
                host.züge.add(zug);
            } else {
                gegner.züge.add(zug);
            }
            System.out.println("Zug gezogen");
            cleanStatusFelder();
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

    public void updateBrett() {

        mga.updateBrett(felder);
    }

    /**
     * überprüft ob ein Schach (Königsangriff) vorliegt und markiert dieses auf dem Brett
     *
     * @param farbe die Farbe, bei der das Schach überprüft werden soll (angegriffene Farbe)
     */
    public void SchachCheck(String farbe) {
        Point KönigPosition = indexOf(getFiguren(felder), new König(farbe));
        if (SchachAuf(getFiguren(felder), SpielerWeiß().züge, SpielerSchwarz().züge, farbe)) {
            get(felder, KönigPosition).setStatus(Feld.Status.SCHACH());
            updateBrett();
        }
    }

    /**
     * überprüft ob das Ende des Spiels vorliegt,
     *  setzt das Attribut Ende auf das passende Ende ein und
     *  stellt es in der GUI dar
     * Mögliche Enden sind:
     * - Matt
     * - Remis
     */
    private void EndeCheck() {
        Ende e = EndeBerechnen(getFiguren(felder), SpielerWeiß(), SpielerSchwarz());
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
        ausgabe.addAll(MöglicheZüge(getFiguren(felder), SpielerWeiß().züge, SpielerSchwarz().züge, ausgewählt.x, ausgewählt.y));
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
        if (host.züge.size() > 0) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    if (felder[x][y].getStatus() != null &&
                            felder[x][y].getStatus().equals(Feld.Status.ZUG()))
                    felder[x][y].setStatus(null);
                }
            }
            updateBrett();
            Zug letzterZug;
            if(dran(host)){
                letzterZug = getLast(gegner.züge);
            }
            else{
                letzterZug = getLast(host.züge);
            }
            get(felder, letzterZug.neu).setStatus(Feld.Status.ZUG());
            get(felder, letzterZug.alt).setStatus(Feld.Status.ZUG());
        }
        updateBrett();
    }

    /**
     * nimmt allen Feldern einen Status
     */
    private void cleanStatusFelder(){
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                felder[x][y].setStatus(null);
            }
        }
    }


    /**
     * gibt aus ob Spieler Weiß dran ist
     * hierbei wird überprüft,
     * ob der Spieler weiß mehr Züge als schwarz hat (-> true)
     * oder beide gleich viele Züge haben (-> false) hat
     */
    public boolean WeißDran() {
        return SpielerWeiß().züge.size() == SpielerSchwarz().züge.size();
    }

    /**
     * gibt an ob der Spieler host dran ist
     */
    public boolean HostDran() {
        return host.getGegner().getFarbe().equals(FarbeDran());
    }

    /**
     * gibt an welche Farbe gerade dran ist
     */
    public String FarbeDran() {
        return WeißDran() ? WHITE : BLACK;
    }

    public Spieler SpielerWeiß(){
        if(host.getGegner().getFarbe().equals(WHITE)){
            return host;
        }
        else{
            return gegner;
        }
    }

    public Spieler SpielerSchwarz(){
        if(host.getGegner().getFarbe().equals(BLACK)){
            return host;
        }
        else{
            return gegner;
        }
    }

    public boolean dran(Spieler g){
        if(!g.equals(host) && !g.equals(gegner)){
            return false;
        }
        return FarbeDran().equals(g.getGegner().getFarbe());
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
     * liegt kein Ende vor: null
     * Standard-Ausgabe: null
     *
     */
    public static Ende EndeBerechnen(Figur[][] figuren, Spieler weiß, Spieler schwarz) {
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
            return new Ende.Patt(schwarz);
        }
        if (farbeDran.equals(WHITE) && ImPatt(figuren, weiß.züge, schwarz.züge, WHITE)) {
            return new Ende.Patt(weiß);
        }
        if (ImMatt(figuren, weiß.züge, schwarz.züge, BLACK)) {
            return new Ende.Matt(weiß);
        }
        if (ImMatt(figuren, weiß.züge, schwarz.züge, WHITE)) {
            return new Ende.Matt(schwarz);
        }
        return null;
    }

}