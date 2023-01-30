package Spiel.TeilvonSpiel.Figuren;

import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.abs;
import static util.Listen.getLast;
import static util.ArrayPoint.get;

public class Bauer extends Figur {

    private final static String bauer = "P";

    public Bauer (String farbe)
    {
        this.farbe = farbe;
        name = bauer;
        wert = 1;
    }

    @Override
    protected List<Point> möglicheZüge_ohneSchach(Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge, int xfeld, int yfeld) {
        List<Point> ausgabe = new ArrayList<>();
        String farbe = figuren[xfeld][yfeld].getFarbe();

        //BauernUmwandlung
        if(farbe.equals(WHITE) ){
            if(yfeld > 1) {
                if (figuren[xfeld][yfeld - 1] == null) {
                    ausgabe.add(new Point(xfeld, yfeld - 1));
                }
                if (xfeld > 0 && figuren[xfeld - 1][yfeld - 1] != null &&
                        figuren[xfeld - 1][yfeld - 1].getFarbe().equals(BLACK)) {
                    ausgabe.add(new Point(xfeld - 1, yfeld - 1));
                }
                if (xfeld < 7 && figuren[xfeld + 1][yfeld - 1] != null &&
                        figuren[xfeld + 1][yfeld - 1].getFarbe().equals(BLACK)) {
                    ausgabe.add(new Point(xfeld + 1, yfeld - 1));
                }
                if (yfeld == 6 && figuren[xfeld][5] == null && figuren[xfeld][4] == null) {
                    ausgabe.add(new Point(xfeld, 4));
                }
            }
            else{
                if (figuren[xfeld][0] == null) {
                    ausgabe.addAll(
                            IntStream.rangeClosed(-4, -1).boxed().map(i->
                                    new Point(xfeld, i)
                            ).toList()
                    );
                }
                if (xfeld > 0 && figuren[xfeld - 1][0] != null &&
                        figuren[xfeld - 1][0].getFarbe().equals(BLACK)) {
                    ausgabe.addAll(
                            IntStream.rangeClosed(-4, -1).boxed().map(i->
                                    new Point(xfeld - 1, i)
                            ).toList()
                    );
                }
                if (xfeld < 7 && figuren[xfeld + 1][0] != null &&
                        figuren[xfeld + 1][0].getFarbe().equals(BLACK)) {
                    ausgabe.addAll(
                            IntStream.rangeClosed(-4, -1).boxed().map(i->
                                    new Point(xfeld + 1, i)
                            ).toList()
                    );
                }
            }
        }
        else{
            if(yfeld < 6) {
                if (figuren[xfeld][yfeld + 1] == null) {
                    ausgabe.add(new Point(xfeld, yfeld + 1));
                }
                if (xfeld > 0 && figuren[xfeld - 1][yfeld + 1] != null &&
                        figuren[xfeld - 1][yfeld + 1].getFarbe().equals(WHITE)) {
                    ausgabe.add(new Point(xfeld - 1, yfeld + 1));
                }
                if (xfeld < 7 && figuren[xfeld + 1][yfeld + 1] != null &&
                        figuren[xfeld + 1][yfeld + 1].getFarbe().equals(WHITE)) {
                    ausgabe.add(new Point(xfeld + 1, yfeld + 1));
                }
                if (yfeld == 1 && figuren[xfeld][2] == null && figuren[xfeld][3] == null) {
                    ausgabe.add(new Point(xfeld, 3));
                }
            }
            else{
                if(figuren[xfeld][0] == null){
                    ausgabe.addAll(
                            IntStream.rangeClosed(8, 11).boxed().map(i->new Point(xfeld, i)).toList()
                    );
                }
                if(xfeld > 0 && figuren[xfeld - 1][0] != null &&
                        figuren[xfeld - 1][0].getFarbe().equals(WHITE)){
                    ausgabe.addAll(
                            IntStream.rangeClosed(8, 11).boxed().map(i->new Point(xfeld - 1, i)).toList()
                    );
                }
                if(xfeld < 7 && figuren[xfeld + 1][0] != null &&
                        figuren[xfeld + 1][0].getFarbe().equals(WHITE)){
                    ausgabe.addAll(
                            IntStream.rangeClosed(8, 11).boxed().map(i->new Point(xfeld + 1, i)).toList()
                    );
                }
            }
        }

        //En Passant
        if(farbe.equals(WHITE) && yfeld == 3){
            if(xfeld > 0){
                if(getLast(SchwarzZüge).alt.equals(new Point(xfeld -1, 1))
                        && getLast(SchwarzZüge).neu.equals(new Point(xfeld - 1, 3))
                        && get(figuren, getLast(SchwarzZüge).neu).getName().equals(bauer)){
                    ausgabe.add(new Point(xfeld - 1, 2));
                }
            }
            if(xfeld < 7){
                if(getLast(SchwarzZüge).alt.equals(new Point(xfeld +1, 1))
                        && getLast(SchwarzZüge).neu.equals(new Point(xfeld + 1, 3))
                        && get(figuren, getLast(SchwarzZüge).neu).getName().equals(bauer)){
                    ausgabe.add(new Point(xfeld + 1, 2));
                }
            }
        }

        return ausgabe;
    }

    /**
     * gibt eine Figur bei einer BauernUmwandlung aus
     * @param nummer die Nummer der Neuen Figur
     *               Weiß / Schwarz -> Figur
     *               -1 / 8         -> Dame
     *               -2 / 9         -> Springer
     *               -3 / 10        -> Turm
     *               -4 / 11        -> Läufer
     */
    public static Figur BauernUmwandlung (int nummer){
        String farbe = nummer>0?BLACK:WHITE;
        Figur[] figuren = new Figur[]{
                new Dame(farbe),
                new Springer(farbe),
                new Turm(farbe),
                new Läufer(farbe)
        };
        int index = (int) abs(nummer - ((nummer/abs(nummer)) * 4.5) - 3.5);
        return figuren[index];
    }
}
