package Spiel.TeilvonSpiel;

import java.awt.*;

public class Zug {
    public Point alt;
    public Point neu;

    /**
     * @param alt Die alten Koordinaten der Figur auf dem Brett
     * @param neu Die neuen Koordinaten der Figur auf dem Brett
     */
    public Zug(Point alt, Point neu) {
        this.alt = alt;
        this.neu = neu;
    }
}
