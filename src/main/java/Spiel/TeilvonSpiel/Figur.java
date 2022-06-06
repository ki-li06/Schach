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
        String farbe = figuren[xfeld][yfeld].getFarbe();
        List<Point> möglich_ohneSchach = new ArrayList<>(möglicheZüge_ohneSchach(figuren, letzerZug, xfeld, yfeld));
        List<Point> GegnerKoordinaten = new ArrayList<>();
        for (int x = 0; x < figuren.length; x++) {
            for (int y = 0; y < figuren[x].length; y++) {
                if(figuren[x][y] != null && figuren[x][y].equals(andereFarbe(farbe))){
                    GegnerKoordinaten.add(new Point(x, y));
                }
            }
        }
        //NOCH FALSCHE AUSGABE
        return möglich_ohneSchach;
    }

    /**
     * Gibt die jeweils andere Farbe aus
     * @return w -> b; b -> w
     */
    public final String andereFarbe (String farbe){
        return farben[1-List.of(farbe).indexOf(farbe)];

    }

    /**
     * macht das gleiche wie die gleichnamige nicht static Methode.
     * Diese Methode ist aber static, weil sie ja an sich nicht von einer Figur abhängig ist
     */
    public static List<Point> MöglicheZüge (Figur[][] figuren, Zug letzerZug, int xfeld, int yfeld){
        return figuren[xfeld][yfeld].möglicheZüge(figuren, letzerZug, xfeld, yfeld);
    }
    @Override
    public String toString() {
        return "Figur{" +
                "name='" + name + '\'' +
                ", farbe='" + farbe + '\'' +
                '}';
    }

}
