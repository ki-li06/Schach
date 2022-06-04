package util;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static util.ShowInJFrame.show;
import static util.StringFormat.getFormat;

/**
 * erzeugt verbleichende Kreise (für die Stati von Feldern)
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
     * ein Beispiel für ein Circle
     */
    public static void main(String[] args) {
        int breite = 300;
        BufferedImage circle = circle(breite, Color.red, false, 2, 1.1);
        show(circle);
    }
}
