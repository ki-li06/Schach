package Spiel.TeilvonSpiel;

import java.awt.*;

import static util.FormatPoint.format;

public class Zug {
    public Point alt;
    public Point neu;

    /**
     * @param alt Die alten Koordinaten der Figur auf dem Brett
     * @param neu Die neuen Koordinaten der Figur auf dem Brett
     *
     * Sonderfall Rochade:
     * nur der Zug des Königs wird gewertet
     */
    public Zug(Point alt, Point neu) {
        this.alt = alt;
        this.neu = neu;
    }

    @Override
    public String toString() {
        return "[" + format(alt) + " -> " + format(neu) + "]";
    }
}
