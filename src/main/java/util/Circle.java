package util;

import Spiel.TeilvonSpiel.Feld;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

import static util.ShowInJFrame.show;
import static util.StringFormat.getFormat;

/**
 * erzeugt verschiedene Bilder mit Kreisen (für die Stati von Feldern)
 */
public class Circle {
    /**
     * gibt ein Bild eines verbleichten Kreises in einem Quadrat aus
     * @param breite Breite des Quadrats
     * @param color Farbe des Kreises
     * @param inverse Verlaufsrichtung der Farbe (true = außen nach innen)
     * @param pow Verlaufs-Geschwindigkeit der Farbe
     * @param afactor Verlaufs-Stärke der Farbe
     * @return ein BufferedImage
     */
    public static BufferedImage circle (int breite, Color color, boolean inverse, int pow, double afactor){
        BufferedImage bi = new BufferedImage(breite, breite, BufferedImage.TYPE_INT_ARGB);
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        double mitte = (double) (breite - 1)/2;
        double max = Math.sqrt(2 * Math.pow(mitte, 2));

        for (int y = 0; y < bi.getHeight(); y++) {
            for (int x = 0; x < bi.getWidth(); x++) {
                double zahl = Math.sqrt(Math.pow(x-mitte, 2) + Math.pow(y-mitte, 2));
                zahl = zahl/max;
                zahl = Math.pow(zahl, pow);
                if(zahl > 1){
                    zahl = 1;
                }
                if(inverse){
                    zahl = 1 - zahl;
                }
                int a = (int) (zahl * 255);
                a = (int) (a * afactor);
                if(a > 255){
                    a = 255;
                }
                Color c = new Color(r, g, b, a);
                bi.setRGB(x,y, c.getRGB());
            }
        }
        return bi;
    }

    /**
     * ruft circle(...) mit Standardwerten für inverse (false), pow(2) und afactor(1) auf
     */
    public static BufferedImage circle (int breite, Color color){
        return circle(breite, color, false, 2, 1);
    }

    /**
     * ruft circle(...) mit Standard-Werten für pow(2) und afactor(1) auf
     */
    public static BufferedImage circle (int breite, Color color, boolean inverse){
        return circle(breite, color, inverse, 2, 1);
    }

    /**
     * Gibt ein Viereck aus, bei dem nur runde Ecken gefärbt sind. Also so als würde man einen transparenten Kreis mitten auf dieses gefärbte Viereck legen.
     * @param breite Breite des Vierecks
     * @param color Farbe der Ecken
     * @param radius Radius des Kreises
     */
    public static BufferedImage rundeEcken (int breite, Color color, int radius){
        String format = getFormat(4, true);
        int rgb = color.getRGB();

        double mitte = (double) (breite-1)/2;
        BufferedImage bi = new BufferedImage(breite, breite, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                int distance = (int) Math.sqrt(Math.pow(y - mitte, 2) + Math.pow(x - mitte, 2));
                if(distance > radius){
                    bi.setRGB(x, y, rgb);
                }
            }
        }
        return bi;
    }

    /**
     * gibt eine Kreis in einem genau so großen Bild
     * @param radius der Radius des Kreises
     */
    public static BufferedImage kreis (double radius, Color color){
        int breite = (int) (radius * 2);
        System.out.println("breite: " + breite);
        BufferedImage bi = new BufferedImage(breite, breite, BufferedImage.TYPE_INT_ARGB);
        Ellipse2D ellipse = new Ellipse2D.Double(0, 0, breite, breite);
        Graphics2D g2 = bi.createGraphics();
        g2.setColor(color);
        g2.fill(ellipse);
        g2.dispose();
        return bi;
    }

    /**
     * ein Beispiel für ein Circle
     */
    public static void main(String[] args) {
        int breite = 65;
        Color color = new Color(201, 29, 49, 255);
        BufferedImage bi = kreis(breite, color);
        show(Feld.Status.MÖGLICH_SCHLAGEN().getBild());
    }
}
