package Spiel.TeilvonSpiel;

import Spiel.TeilvonSpiel.Figuren.Turm;
import com.sun.security.jgss.GSSUtil;
import util.ColPrint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static util.FormatPoint.format;
import static util.ArrayPoint.get;
import static util.StringFormat.getFormat;


public abstract class Figur {
    public static final String[] figurennamen = new String[]{"P", "R", "B", "N", "Q", "K"};
    public static final String BLACK = "b";
    public static final String WHITE = "w";
    public static final String[] farben = new String[]{WHITE, BLACK};

    protected String name;
    protected String farbe;


    public String getName() {
        return name;
    }
    public String getFarbe() {
        return farbe;
    }
    public BufferedImage getBild(){
        File file = new File("Figuren\\" + farbe + name + ".png");
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setName(String name) {
        if(List.of(figurennamen).contains(name)){
            this.name = name;
        }
        else {
            System.out.println("FEHLER - UNGÜLTIGE NAMEN-EINGABE: " +  name);
        }
    }
    public void setFarbe(String farbe) {
        if(List.of(farben).contains(farbe)) {
            this.farbe = farbe;
        }
        else{
            System.out.println("FEHLER - UNGÜLTIGE FARBEN-EINGABE: " + farbe);
        }
    }


    /**
     * gibt alle Koordinaten von Felder aus, auf die die angegebene Figur rein theoretisch ziehen kann
     * Dabei wird ignoriert ob der Spieler im Schach steht oder die Figur vor dem König gefesselt ist
     * @param figuren das Spiefeld an Figuren (ein Feld[][] kann mit getFiguren(Feld[][]) umgewandelt werden)
     * @param WeißZüge alle Züge, die Weiß bisher gespielt hat
     * @param SchwarzZüge alle Züge, die Schwarz bisher gespielt hat  (beide nur für En Passant und Rochade relevant)
     * @param xfeld die x-Koordinate der Figur auf dem Brett
     * @param yfeld die y-Koordinate der Figur auf dem Brett
     * @return eine Liste an allen Möglichen Feldern
     */
    protected List<Point> möglicheZüge_ohneSchach(Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge, int xfeld, int yfeld){
        Figur f = figuren[xfeld][yfeld];
        /*
        System.out.println("FEHLER - MÖGLICHEZÜGE_OHNESCHACH");
        System.out.println("IN DER KLASSE " + f.getClass().getSimpleName() + " NICHT DEKLARIERT");
        */
        return new ArrayList<>();
    }

    /**
     * fast genau das gleiche wie möglicheZüge_ohneSchach, Unterschied:
     * hierbei werden alle Züge nach denen der Gegner den König schlagen kann gestrichen
     * @return diese Liste kann auf dem Spielbrett angezeigt werden
     */
    public List<Point> möglicheZüge (Figur[][] figuren, List<Zug> weißZüge, List<Zug> schwarzZüge, int xfeld, int yfeld){
        //System.out.println("ausgangssituation: ");
        String farbe = figuren[xfeld][yfeld].getFarbe();
        List<Point> möglich_ohneSchach = new ArrayList<>(möglicheZüge_ohneSchach(figuren, weißZüge, schwarzZüge, xfeld, yfeld));
        //System.out.println("mögliche_ohneSchach: " + format(möglich_ohneSchach));

        List<Point> GegnerKoordinaten = new ArrayList<>();
        for (int x = 0; x < figuren.length; x++) {
            for (int y = 0; y < figuren[x].length; y++) {
                if(figuren[x][y] != null && figuren[x][y].getFarbe().equals(andereFarbe(farbe))){
                    GegnerKoordinaten.add(new Point(x, y));
                }
            }
        }
        //System.out.println("Gegner Koordinaten: \n" + format(GegnerKoordinaten));
        //System.out.println("königskordinaten: " + format(königkoordinaten));
        //System.out.println("\n\n");
        for (int i = 0; i < möglich_ohneSchach.size(); i++) {
            List<Zug> WeißZüge = new ArrayList<>(weißZüge);
            List<Zug> SchwarzZüge = new ArrayList<>(schwarzZüge);
            //System.out.println("möglicher zug: " + format(möglich_ohneSchach.get(i)));
            List<Point> GegnerMöglicheZüge = new ArrayList<>();
            Zug zug = new Zug(
                    new Point(xfeld, yfeld),
                    new Point(möglich_ohneSchach.get(i).x, möglich_ohneSchach.get(i).y)
            );
            Figur[][] neuefiguren = zug.ziehe(figuren);
            Point königkoordinaten = indexOf(neuefiguren, "K", farbe);
            //System.out.println("zug: " + zug);
            //System.out.println(" -> ");
            //System.out.println("neuefiguren: ");
            //ausgeben(neuefiguren);
            if(farbe.equals(WHITE)){
                WeißZüge.add(zug);
            }
            else {
                SchwarzZüge.add(zug);
            }
            List<Point> alleMöglich = new ArrayList<>(
                    AlleMöglicheZügeEinerFarbe(neuefiguren, WeißZüge, SchwarzZüge, andereFarbe(farbe))
            );
            //System.out.println("alle möglich: " + format(alleMöglich));
            if(alleMöglich.contains(königkoordinaten)){
                möglich_ohneSchach.set(i, new Point(-1, -1));
            }
            //System.out.println("--> " + format(möglich_ohneSchach.get(i)));
        }
        möglich_ohneSchach.removeIf(i->i.x == -1 || i.y == -1);

        //NOCH FALSCHE AUSGABE
        return möglich_ohneSchach;
    }

    /**
     * Gibt die jeweils andere Farbe aus
     * @return w -> b; b -> w
     */
    public final String andereFarbe (String farbe){
        if(farbe.equals(WHITE)){
            return BLACK;
        }
        return WHITE;

    }

    /**
     * Gibt alle Punkte aus, auf die eine Farbe ziehen kann (MöglicheZüge_ohneSchach)
     * (relevant für MöglicheZüge(...) und Rochade)
     */
    protected static List<Point> AlleMöglicheZügeEinerFarbe(Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge, String farbe){
        List<Point> koordinaten = new ArrayList<>();
        for (int x = 0; x < figuren.length; x++) {
            for (int y = 0; y < figuren[x].length; y++) {
                if(figuren[x][y] != null && figuren[x][y].getFarbe().equals(farbe)){
                    koordinaten.add(new Point(x, y));
                }
            }
        }
        List<Point> ausgabe = new ArrayList<>();
        for (Point p : koordinaten) {
            ausgabe.addAll(MöglicheZüge_OhneSchach(figuren, WeißZüge, SchwarzZüge, p.x, p.y));
        }
        return ausgabe;
    }

    /**
     * macht das gleiche wie die gleichnamige nicht static Methode.
     * Diese Methode ist aber static, weil sie ja an sich nicht von einer Figur abhängig ist
     */
    public static List<Point> MöglicheZüge (Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge, int xfeld, int yfeld){
        if(figuren[xfeld][yfeld] == null){
            ColPrint.red.println("FEHLER - Figur.MöglicheZüge: figuren[" + xfeld + "][" + yfeld + "] ist null");
            return new ArrayList<>();
        }
        return figuren[xfeld][yfeld].möglicheZüge(figuren, WeißZüge, SchwarzZüge, xfeld, yfeld);
    }
    static List<Point> MöglicheZüge_OhneSchach (Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge, int xfeld, int yfeld){
        if(figuren[xfeld][yfeld] == null){
            ColPrint.red.println("FEHLER - Figur.MöglicheZüge_OhneSchach: figuren[" + xfeld + "][" + yfeld + "] ist null");
            return new ArrayList<>();
        }
        return figuren[xfeld][yfeld].möglicheZüge_ohneSchach(figuren, WeißZüge, SchwarzZüge, xfeld, yfeld);
    }


    /**
     * gibt den Index der gesuchten Figur
     */
    public static Point indexOf(Figur[][] figuren, String name, String farbe){
        for (int x = 0; x < figuren.length; x++) {
            for (int y = 0; y < figuren[x].length; y++) {
                if(figuren[x][y] != null && figuren[x][y].getName().equals(name) && figuren[x][y].getFarbe().equals(farbe)){
                    return new Point(x, y);
                }
            }
        }
        return new Point(-1, -1);
    }


    public static void ausgeben(Figur[][] figuren){
        String format = getFormat(30, true);
        System.out.print(" ".repeat(11));
        for (int x = 0; x < figuren[0].length; x++) {
            System.out.printf(format, "x - " + x);
        }
        System.out.println();
        for (int y = 0; y < figuren.length; y++) {
            System.out.print("y - " + y + "   ");
            for (int x = 0; x < figuren[y].length; x++) {
                System.out.printf(format, figuren[x][y]);
            }
            System.out.println();
        }
    }
    public static Figur[][] kopieren (Figur[][] figuren){
        Figur[][] ausgabe = new Figur[figuren.length][figuren[0].length];
        for (int i = 0; i < figuren.length; i++) {
            System.arraycopy(figuren[i], 0, ausgabe[i], 0, figuren[i].length);
        }
        return ausgabe;
    }

    @Override
    public String toString() {
        return "Figur{" +
                "name='" + name + '\'' +
                ", farbe='" + farbe + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figur figur = (Figur) o;
        return Objects.equals(name, figur.name) && Objects.equals(farbe, figur.farbe);
    }
}
