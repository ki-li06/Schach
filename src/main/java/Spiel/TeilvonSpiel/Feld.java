package Spiel.TeilvonSpiel;

import java.util.List;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static GUI.Spielfeld.BreiteEinFeld;
import static util.Circle.circle;

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

    /**
     * gibt den Status des Feldes an, also ob auf diesem Feld ein Schach, ein Zug oder ähnliches ist
     */
    public static class Status{
        private static final Color FARBE_SCHACH = new Color(250, 57, 57, 128);
        private static final Color FARBE_MOVE = new Color(138, 255, 138, 132);
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
        public static Status MOVE () {
            Status ausgabe = new Status("MOVE");
            Color FARBE_MOVE = new Color(138, 255, 138, 132);
            ausgabe.bild = circle(
                    BreiteEinFeld(),
                    FARBE_MOVE);
            return ausgabe;
        }

    }
}
