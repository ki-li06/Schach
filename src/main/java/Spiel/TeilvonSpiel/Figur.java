package Spiel.TeilvonSpiel;

import Spiel.TeilvonSpiel.Figuren.Turm;
import com.sun.security.jgss.GSSUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     * @param letzterZug der letzte Zug, den der Gegner gemacht hat (nur für 'En Passant' relevant)
     * @param xfeld die x-Koordinate der Figur auf dem Brett
     * @param yfeld die y-Koordinate der Figur auf dem Brett
     * @return eine Liste an allen Möglichen Feldern
     */
    protected List<Point> möglicheZüge_ohneSchach(Figur[][] figuren, Zug letzterZug, int xfeld, int yfeld){
        Figur f = figuren[xfeld][yfeld];
        System.out.println("FEHLER - MÖGLICHEZÜGE_OHNESCHACH");
        System.out.println("IN DER KLASSE " + f.getClass().getSimpleName() + " NICHT DEKLARIERT");
        return null;
    }

    /**
     * fast genau das gleiche wie möglicheZüge_ohneSchach, Unterschied:
     * hierbei werden alle Züge nach denen der Gegner den König schlagen kann gestrichen
     * @return diese Liste kann auf dem Spielbrett angezeigt werden
     */
    public List<Point> möglicheZüge (Figur[][] figuren, Zug letzerZug, int xfeld, int yfeld){
        //System.out.println("ausgangssituation: ");
        String farbe = figuren[xfeld][yfeld].getFarbe();
        List<Point> möglich_ohneSchach = new ArrayList<>(möglicheZüge_ohneSchach(figuren, letzerZug, xfeld, yfeld));
        System.out.println("mögliche_ohneSchach: " + format(möglich_ohneSchach));

        List<Point> GegnerKoordinaten = new ArrayList<>();
        for (int x = 0; x < figuren.length; x++) {
            for (int y = 0; y < figuren[x].length; y++) {
                if(figuren[x][y] != null && figuren[x][y].getFarbe().equals(andereFarbe(farbe))){
                    GegnerKoordinaten.add(new Point(x, y));
                }
            }
        }
        //System.out.println("Gegner Koordinaten: \n" + format(GegnerKoordinaten));
        Point königkoordinaten = indexOf(figuren, "K", farbe);
        //System.out.println("königskordinaten: " + format(königkoordinaten));
        //System.out.println("\n\n");
        for (int i = 0; i < möglich_ohneSchach.size(); i++) {
            //System.out.println("möglicher zug: " + format(möglich_ohneSchach.get(i)));
            List<Point> GegnerMöglicheZüge = new ArrayList<>();
            Zug zug = new Zug(
                    new Point(xfeld, yfeld),
                    new Point(möglich_ohneSchach.get(i).x, möglich_ohneSchach.get(i).y)
            );
            Figur[][] neuefiguren = kopieren(figuren);
            neuefiguren[zug.neu.x][zug.neu.y] = neuefiguren[zug.alt.x][zug.alt.y];
            neuefiguren[zug.alt.x][zug.alt.y] = null;
            //System.out.println("zug: " + zug);
            //System.out.println(" -> ");
            //ausgeben(neuefiguren);
            for (int j = 0; j < GegnerKoordinaten.size(); j++) {
                if(!GegnerKoordinaten.get(j).equals(möglich_ohneSchach.get(i))) {
                    //System.out.println("gegnerkoordinaten: " + format(GegnerKoordinaten.get(j)) + " -> " + get(neuefiguren, GegnerKoordinaten.get(j)));
                    List<Point> GegnerKoordinateMöglicheZüge = new ArrayList<>(
                            MöglicheZüge_OhneSchach(neuefiguren, zug, GegnerKoordinaten.get(j).x, GegnerKoordinaten.get(j).y)
                    );
                    //System.out.println("  mögliche züge: " + format(GegnerKoordinateMöglicheZüge));
                    GegnerMöglicheZüge.addAll(
                            GegnerKoordinateMöglicheZüge
                    );
                }
                else{
                    //System.out.println("gegner geschlagen");
                }
            }
            if(GegnerMöglicheZüge.contains(königkoordinaten)){
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
     * macht das gleiche wie die gleichnamige nicht static Methode.
     * Diese Methode ist aber static, weil sie ja an sich nicht von einer Figur abhängig ist
     */
    public static List<Point> MöglicheZüge (Figur[][] figuren, Zug letzterZug, int xfeld, int yfeld){
        return figuren[xfeld][yfeld].möglicheZüge(figuren, letzterZug, xfeld, yfeld);
    }
    private static List<Point> MöglicheZüge_OhneSchach (Figur[][] figuren, Zug letzterZug, int xfeld, int yfeld){
        return figuren[xfeld][yfeld].möglicheZüge_ohneSchach(figuren, letzterZug, xfeld, yfeld);
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

}
