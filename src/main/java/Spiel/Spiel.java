package Spiel;

import GUI.BauernAuswahl.BauernAuswahlMouseListener;
import GUI.GUI_Package;
import GUI.MausListener;
import GUI.Teile.Eingabefeld;
import GUI.Teile.MainGameAnzeige;
import Gegner.Arten.Lokal.Lokal;
import Gegner.Gegner;
import Spiel.TeilvonSpiel.*;
import Spiel.TeilvonSpiel.Figuren.*;
import util.ColPrint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static GUI.BauernAuswahl.BauernAuswahl.getNummer;
import static Spiel.TeilvonSpiel.Feld.getFiguren;
import static Spiel.TeilvonSpiel.Feld.setFiguren;
import static Spiel.TeilvonSpiel.Figur.*;
import static util.ArrayPoint.get;
import static util.FormatPoint.format;
import static util.Listen.getLast;

public class Spiel {
    public final Spieler weiß;
    public final Spieler schwarz;
    private Gegner gegner;
    public Feld[][] felder; //x - y (siehe überblick.txt)
    private Ende ende;

    public final MainGameAnzeige mga;
    private Point ausgewählt;


    public Spiel(String spielername, Gegner g, GUI_Package gui) {
        gegner = g;
        gegner.setSpiel(this);
        gegner.start(gui);

        weiß = new Spieler(spielername);
        schwarz = new Spieler(g.getName());

        System.out.println("Weiß: '" + weiß.name + "'");
        System.out.println("Schwarz: '" + schwarz.name + "'");


        mga = new MainGameAnzeige(
                weiß.name,
                schwarz.name,
                gui
        );

        erstelleFelder();

        updateBrett();

        mga.fügeMouseListenerHinzu(new MausListener(this));
        mga.fügeMouseListenerBauernAuswahlHinzu(new BauernAuswahlMouseListener(this));
        //mouseListener = new MausListener(this);
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
     * diese Methode wird vom MouseListener ausgeführt, wenn auf das Spielfeld geklickt wird
     */
    public void aufBrettGeklickt(int xfeld, int yfeld) {
        if (ende == null && selbstDran()) {
            ColPrint.blue.println("Es wurde auf das Feld " + xfeld + " | " + yfeld + " geklickt");
            Point point = new Point(xfeld, yfeld);
            if (!mga.BauernAuswahlSichtbar()) {
                if (get(felder, point).getFigur() != null && (ausgewählt == null ||
                        (get(felder, point).getFigur().getFarbe().equals(FarbeDran()) && !ausgewählt.equals(point)))) {
                    if (get(felder, point).getFigur().getFarbe().equals(FarbeDran())) {
                        clearMöglicheZüge();
                        ausgewählt = point;
                        zeigeMöglicheZüge();
                    }
                } else if (möglicheZüge().contains(point)) {
                    Zug zug = new Zug(ausgewählt, point);
                    get(felder, ausgewählt).setStatus(null);
                    clearMöglicheZüge();
                    if (get(felder, ausgewählt).getFigur().equals(new Bauer("w")) &&
                            zug.neu.y == 0) {
                        get(felder, ausgewählt).setStatus(Feld.Status.AUSGEWÄHLT());
                        get(felder, point).setStatus(Feld.Status.BAUERNUMWANDLUNG());
                        mga.zeigeBauernAuswahl("w");
                        updateBrett();
                    } else if (get(felder, ausgewählt).getFigur().equals(new Bauer("b")) &&
                            zug.neu.y == 7) {
                        get(felder, ausgewählt).setStatus(Feld.Status.AUSGEWÄHLT());
                        get(felder, point).setStatus(Feld.Status.BAUERNUMWANDLUNG());
                        mga.zeigeBauernAuswahl("b");
                        updateBrett();
                    } else {
                        clearMöglicheZüge();
                        ausgewählt = null;
                        ziehe(zug);
                        if (ende != null) {
                            return;
                        }
                        //mga.dreheBrett(felder);
                        ausgewählt = null;
                    }
                } else if (ausgewählt.equals(point)) {
                    clearMöglicheZüge();
                    ausgewählt = null;
                    SchachCheck(FarbeDran());
                }
            } else {
                Point bu = indexOfBauernUmwandlung();
                System.out.println("BauernUmwandlung: " + format(bu));
                if (point.equals(ausgewählt) || get(felder, point).getFigur() == null) {
                    get(felder, bu).setStatus(null);
                    mga.macheBauernAuswahlUnsichtbar();
                    zeigeMöglicheZüge();
                } else if (!point.equals(bu) && get(felder, point).getFigur() != null &&
                        get(felder, point).getFigur().getFarbe().equals(FarbeDran())) {
                    System.out.println("wahl geändert");
                    mga.macheBauernAuswahlUnsichtbar();
                    get(felder, bu).setStatus(null);
                    get(felder, ausgewählt).setStatus(null);
                    updateBrett();
                    ausgewählt = point;
                    zeigeMöglicheZüge();
                }
            }

            System.out.println("ausgewählt: " + format(ausgewählt));
            System.out.println("möglicheZüge: " + format(möglicheZüge()));
            System.out.println("figur: " + felder[xfeld][yfeld].getFigur());
            if (gegner.isDran()) {
                ziehe(gegner.ziehe());
            }
        }
    }

    /**
     * diese Methode wird vom BauernAuswahlMouseListener ausgeführt, wenn auf ihn geklickt wird
     * und die BauernAuswahl angezeigt wird
     */
    public void aufBauernAuswahlGeklickt(int nummer) {
        ColPrint.blue.println("Spiel - BauernAuswahl geklickt: " + nummer);
        int xindex = indexOfBauernUmwandlung().x;
        int yindex = getNummer(nummer, FarbeDran());
        System.out.println("xindex: " + xindex);
        System.out.println("yindex: " + yindex);
        Point neu = new Point(xindex, yindex);
        Zug zug = new Zug(ausgewählt, neu);
        ziehe(zug);
        mga.macheBauernAuswahlUnsichtbar();
        if (ende == null) {
            ziehe(gegner.ziehe());
        }
    }

    /**
     * zieht den angegeben Zug, löscht die Markierungen alter Züge und markiert die neuen Felder (und Schach)
     */
    public void ziehe(Zug zug) {
        if (get(felder, zug.alt) != null) {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    if (felder[x][y].getStatus() != null) {
                        felder[x][y].setStatus(null);
                    }
                }
            }
            System.out.println("ziehe den zug : " + zug);
            setFiguren(felder, zug.ziehe(getFiguren(felder)));
            if (WeißDran()) {
                weiß.züge.add(zug);
            } else {
                schwarz.züge.add(zug);
            }
            SchachCheck(FarbeDran());
            markiereLetztenZug();
            EndeCheck();
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
        Point KönigPosition = indexOf(getFiguren(felder), "K", farbe);
        if (SchachAuf(getFiguren(felder), weiß.züge, schwarz.züge, farbe)) {
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
        Ende e = ende(getFiguren(felder), weiß, schwarz);
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
        ausgabe.addAll(MöglicheZüge(getFiguren(felder), weiß.züge, schwarz.züge, ausgewählt.x, ausgewählt.y));
        return ausgabe;
    }

    /**
     * eigener ausgewählter Punkt
     *
     * @return
     */
    private List<Point> möglicheZüge() {
        return möglicheZüge(ausgewählt);
    }

    /**
     * zeigt alle Möglichen Züge an (MÖGLICH_ZUG oder MÖGLICH_SCHLAGEN)
     */
    private void zeigeMöglicheZüge() {
        zeigeMöglicheZüge(ausgewählt);
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
                if (felder[i][j].getStatus() != null && (
                        felder[i][j].getStatus().equals(Feld.Status.MÖGLICH_ZUG())
                                || felder[i][j].getStatus().equals(Feld.Status.MÖGLICH_SCHLAGEN())
                                || felder[i][j].getStatus().equals(Feld.Status.AUSGEWÄHLT()))
                ) {
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
        if (weiß.züge.size() > 0) {
            Zug letzterZug;
            if (WeißDran()) {
                letzterZug = schwarz.züge.get(schwarz.züge.size() - 1);
            } else {
                letzterZug = weiß.züge.get(weiß.züge.size() - 1);
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
        return weiß.züge.size() == schwarz.züge.size();
    }

    public boolean selbstDran() {
        return andereFarbe(gegner.getFarbe()).equals(FarbeDran());
    }

    /**
     * gibt an welche Farbe gerade dran ist
     */
    public String FarbeDran() {
        return WeißDran() ? WHITE : BLACK;
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