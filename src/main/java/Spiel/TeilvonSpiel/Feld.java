package Spiel.TeilvonSpiel;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

import static GUI.Spielfeld.BREITE;
import static GUI.Spielfeld.BreiteEinFeld;
import static util.Circle.*;
import static util.ShowInJFrame.show;

/**
 * Ein Spiel.Spiel hat ein Array 8 x 8 aus Feldern
 */
public class Feld {
    private Figur figur;
    private Status status;
    /**
     * Mögliche Stati:
     * @return
     */
    public Feld(){
        figur = null;
        status = null;
    }
    public Feld(Figur figur){
        this.figur = figur;
        status = null;
    }

    public Figur getFigur() {
        return figur;
    }
    public Status getStatus() {
        return status;
    }

    public void setFigur(Figur figur) {
        this.figur = figur;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public static Figur[][] getFiguren(Feld[][] felder){
        return  Arrays.stream(felder).map(
                i->Arrays.stream(i).map(Feld::getFigur).toArray(Figur[]::new)
        ).toArray(Figur[][]::new);
    }
    public static void setFiguren(Feld[][] felder, Figur[][] figuren){
        for (int x = 0; x < felder.length; x++) {
            for (int y = 0; y < felder[x].length; y++) {
                felder[x][y].setFigur(figuren[x][y]);
            }
        }
    }

    /**
     * gibt den Status des Feldes an, also ob auf diesem Feld ein Schach, ein Zug oder ähnliches ist
     */
    public static class Status{
        private static final Color MÖGLICH = new Color(22, 96, 14, 118);
        private BufferedImage bild;
        private String name;
        public Status(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public BufferedImage getBild() {
            return bild;
        }
        public static Status SCHACH (){
            Status ausgabe = new Status("SCHACH");
            Color FARBE_SCHACH = new Color(250, 57, 57, 128);
            ausgabe.bild = circle(
                    BreiteEinFeld(),
                    FARBE_SCHACH,
                    true,
                    2,
                    0.75);
            return ausgabe;
        }
        public static Status ZUG () {
            Status ausgabe = new Status("MOVE");
            Color FARBE_MOVE = new Color(138, 255, 138, 132);
            ausgabe.bild = circle(
                    BreiteEinFeld(),
                    FARBE_MOVE);
            return ausgabe;
        }
        public static Status AUSGEWÄHLT(){
            BufferedImage bi = new BufferedImage(BreiteEinFeld(), BreiteEinFeld(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bi.createGraphics();
            g2.setColor(MÖGLICH);
            g2.fillRect(0, 0, BreiteEinFeld(), BreiteEinFeld());
            g2.dispose();

            Status ausgabe = new Status("AUSGEWÄHLT");
            ausgabe.bild = bi;
            return ausgabe;
        }
        public static Status MÖGLICH_ZUG (){
            Status ausgabe = new Status("MÖGLICH_ZUG");
            BufferedImage bi = new BufferedImage(BreiteEinFeld(), BreiteEinFeld(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bi.createGraphics();
            double radius = 8;
            BufferedImage punkt = kreis(radius, MÖGLICH);
            g2.drawImage(punkt, (int) (BreiteEinFeld()/2 - radius), (int) (BreiteEinFeld()/2 - radius), null);
            g2.dispose();
            ausgabe.bild = bi;
            return ausgabe;
        }
        public static Status MÖGLICH_SCHLAGEN(){
            Status ausgabe = new Status("MÖGLICH_SCHLAGEN");
            ausgabe.bild = rundeEcken(BreiteEinFeld(), MÖGLICH, 35);
            return ausgabe;
        }
        public static Status BAUERNUMWANDLUNG(){
            Status ausgabe = new Status("BAUERNUMWANDLUNG");
            BufferedImage bi = new BufferedImage(BreiteEinFeld(), BreiteEinFeld(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bi.createGraphics();
            Color c = new Color(0, 110, 255, 150);
            g2.setColor(c);
            g2.fillRect(0, 0, BreiteEinFeld(), BreiteEinFeld());
            g2.dispose();
            ausgabe.bild = bi;
            return ausgabe;
        }

        @Override
        public String toString() {
            return "Status{" + name + "}";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Status status = (Status) o;
            return Objects.equals(name, status.name);
        }

    }
}
