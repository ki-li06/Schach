package GUI;

import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Figuren.Dame;
import Spiel.TeilvonSpiel.Figuren.Läufer;
import Spiel.TeilvonSpiel.Figuren.Springer;
import Spiel.TeilvonSpiel.Figuren.Turm;

import java.awt.*;
import java.awt.image.BufferedImage;

import static GUI.Spielfeld.BreiteEinFeld;
import static Spiel.TeilvonSpiel.Figur.WHITE;

public class BauernAuswahl {
    static final int BreiteStreifen = 2;
    static final int Breite = BreiteEinFeld() * 4 + 3 * BreiteStreifen;

    public static BufferedImage getBildLeer(){
        int höhe = new Dame("w").getBild().getHeight();
        BufferedImage bi = new BufferedImage(Breite, höhe, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        g2.setColor(Color.lightGray);
        g2.fillRect(0, 0, Breite, höhe);
        g2.setColor(Color.gray);
        for (int i = BreiteEinFeld(); i < Breite; i += BreiteEinFeld()) {
            g2.fillRect(i, 0, BreiteStreifen, höhe);
            i += BreiteStreifen;
        }
        g2.dispose();

        return bi;
    }
    public static BufferedImage getBild(String farbe){
        BufferedImage ausgabe = getBildLeer();
        Graphics2D g2 = ausgabe.createGraphics();
        Figur[] figuren = new Figur[]{
                new Dame(farbe),
                new Springer(farbe),
                new Turm(farbe),
                new Läufer(farbe)};
        int i = 0;
        for (int x = 0; x < Breite; x+= (BreiteEinFeld() + BreiteStreifen)) {
            g2.drawImage(figuren[i].getBild(), x, 0, null);
            i++;
        }
        g2.dispose();
        return ausgabe;
    }

    public static int indexOfClickedField (Point p){
        int ausgabe = -1;
        int x = p.x;
        int[] lower = new int[4];
        int[] higher = new int[4];
        for (int i = 0; i < 4; i++) {
            lower[i] = i * (BreiteStreifen + BreiteEinFeld());
            higher[i] = lower[i] + BreiteEinFeld();
        }
        for (int i = 0; i < 4; i++) {
            if (x > lower[i] && x < higher[i]) {
                ausgabe = i;
            }
        }
        return ausgabe;
    }

    /**
     * gibt die Nummer aus, die dem Index entspricht, in den der Bauer sich dann verwandelt
     */
    public static int getNummer (int index, String farbe){
        if (farbe.equals(WHITE)) {
            return -1 - index;
        }
        else return 8 + index;
    }



}
