package Spiel.TeilvonSpiel;

import java.awt.*;

import static Spiel.TeilvonSpiel.Figur.WHITE;
import static util.FormatPoint.format;
import static util.ArrayPoint.get;

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

    public static Figur[][] ziehe(Figur[][] eingabe, Zug zug){
        Figur[][] ausgabe = Figur.kopieren(eingabe);
        if(get(eingabe, zug.alt).getName().equals("K")){
            //kurze Rochade
            if(zug.alt.x - zug.neu.x == -2){
                if (get(eingabe, zug.alt).getFarbe().equals(WHITE)) {
                    ausgabe[5][7] = ausgabe[7][7];
                    ausgabe[7][7] = null;
                }
                else{
                    ausgabe[5][0] = ausgabe[7][0];
                    ausgabe[7][0] = null;
                }
            }
            //lange Rochade
            else if(zug.alt.x - zug.neu.x == 2){
                if (get(eingabe, zug.alt).getFarbe().equals(WHITE)) {
                    ausgabe[3][7] = ausgabe[0][7];
                    ausgabe[0][7] = null;
                }
                else{
                    ausgabe[3][0] = ausgabe[0][0];
                    ausgabe[0][0] = null;
                }
            }
            ausgabe[zug.neu.x][zug.neu.y] = ausgabe[zug.alt.x][zug.alt.y];
            ausgabe[zug.alt.x][zug.alt.y] = null;
        }
        else{
            ausgabe[zug.neu.x][zug.neu.y] = ausgabe[zug.alt.x][zug.alt.y];
            ausgabe[zug.alt.x][zug.alt.y] = null;
        }
        return ausgabe;
    }

    @Override
    public String toString() {
        return "[" + format(alt) + " -> " + format(neu) + "]";
    }
}
