package Spiel.TeilvonSpiel;

import java.awt.*;

import static Spiel.TeilvonSpiel.Figur.WHITE;
import static Spiel.TeilvonSpiel.Figuren.Bauer.BauernUmwandlung;
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

    public Figur[][] ziehe(Figur[][] eingabe){
        Figur[][] ausgabe = Figur.kopieren(eingabe);
        if(get(eingabe, alt).getName().equals("K")){
            //kurze Rochade
            if(alt.x - neu.x == -2){
                if (get(eingabe, alt).getFarbe().equals(WHITE)) {
                    ausgabe[5][7] = ausgabe[7][7];
                    ausgabe[7][7] = null;
                }
                else{
                    ausgabe[5][0] = ausgabe[7][0];
                    ausgabe[7][0] = null;
                }
            }
            //lange Rochade
            else if(alt.x - neu.x == 2){
                if (get(eingabe, alt).getFarbe().equals(WHITE)) {
                    ausgabe[3][7] = ausgabe[0][7];
                    ausgabe[0][7] = null;
                }
                else{
                    ausgabe[3][0] = ausgabe[0][0];
                    ausgabe[0][0] = null;
                }
            }
        }
        else if(get(eingabe, alt).getName().equals("P")){
            if(neu.y < 0 || neu.y > 7){
                ausgabe[alt.x][alt.y] = BauernUmwandlung(neu.y);
                if (get(ausgabe, alt).getFarbe().equals(WHITE)) {
                    neu.y = 0;
                }
                else{
                    neu.y = 7;
                }
            }
        }
        ausgabe[neu.x][neu.y] = ausgabe[alt.x][alt.y];
        ausgabe[alt.x][alt.y] = null;
        return ausgabe;
    }

    @Override
    public String toString() {
        return "{" + alt.x + "|" + alt.y + " - > " + neu.x + "|" + neu.y + "}";
    }
}
