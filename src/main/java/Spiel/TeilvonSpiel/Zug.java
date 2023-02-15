package Spiel.TeilvonSpiel;

import java.awt.*;
import java.util.Objects;

import static Spiel.TeilvonSpiel.Figur.BLACK;
import static Spiel.TeilvonSpiel.Figur.WHITE;
import static Spiel.TeilvonSpiel.Figuren.Bauer.BauernUmwandlung;
import static util.ArrayPoint.get;

public class Zug {
    public Point alt;
    public Point neu;

    /**
     * gibt an ob bei diesem Zug eine Figur geschlagen wurde.
     * Das ist nur für das Ende eines Spiels wichtig,
     * denn nach 50 bzw 100 Zügen in denen keine Figur geschlagen wurde,
     * endet eine Partie Unentschieden/Patt.
     * ACHTUNG: dieses Attribut wird nicht beim Vergleichen von zwei Zug Objekten verglichen
     *
     */
    private boolean FigurGeschlagen;


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

    /**
     * wendet den Zug an
     * @param eingabe das alte Spielfeld
     * @return das neue Spielfeld, jedoch mit dem Zug angewandt
     */
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
            }                                         //Bauernumwandlung
            else if(alt.x != neu.x && get(eingabe, neu) == null) {                  //En Passant Check
                if(get(eingabe, alt).getFarbe().equals(WHITE)
                        && neu.y == 2 && alt.y == 3){                                      //Weiß
                    ausgabe[neu.x][neu.y] = get(eingabe, alt);
                    ausgabe[neu.x][3] = null;
                }
                else if(get(eingabe, alt).getFarbe().equals(BLACK)
                        && neu.y == 5 && alt.y == 4){                                      //Schwarz
                    ausgabe[neu.x][neu.y] = get(eingabe,alt);
                    ausgabe[neu.x][4] = null;
                }
            }
        }
        FigurGeschlagen = get(ausgabe, neu) != null;
        ausgabe[neu.x][neu.y] = get(ausgabe, alt);
        ausgabe[alt.x][alt.y] = null;
        return ausgabe;
    }

    /**
     * nur für das Berechnen des Endes eines Spiels wichtig, siehe Attribut
     * @return das Attribut FigurGeschlagen
     */
    public boolean FigurGeschlagen() {
        return FigurGeschlagen;
    }

    @Override
    public String toString() {
        if(FigurGeschlagen){
            return "{" + alt.x + "|" + alt.y + " - X > " + neu.x + "|" + neu.y + "}";
        }
        return "{" + alt.x + "|" + alt.y + " - > " + neu.x + "|" + neu.y + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zug zug = (Zug) o;
        return Objects.equals(alt, zug.alt) && Objects.equals(neu, zug.neu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alt, neu, FigurGeschlagen);
    }
}
