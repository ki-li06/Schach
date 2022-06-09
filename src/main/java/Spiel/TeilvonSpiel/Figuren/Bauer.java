package Spiel.TeilvonSpiel.Figuren;

import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Bauer extends Figur {


    public Bauer (String farbe)
    {
        this.farbe = farbe;
        name = "P";
    }

    @Override
    protected List<Point> möglicheZüge_ohneSchach(Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge, int xfeld, int yfeld) {
        List<Point> ausgabe = new ArrayList<>();
        String farbe = figuren[xfeld][yfeld].getFarbe();

        if(farbe.equals(WHITE)){
            if(figuren[xfeld][yfeld-1] == null){
                ausgabe.add(new Point(xfeld, yfeld- 1));
            }
            if(xfeld > 0 && figuren[xfeld-1][yfeld-1] != null &&
                    figuren[xfeld-1][yfeld-1].getFarbe().equals(BLACK)){
                ausgabe.add(new Point(xfeld - 1, yfeld - 1));
            }
            if(xfeld < 7 && figuren[xfeld + 1][yfeld - 1] != null &&
                    figuren[xfeld + 1][yfeld - 1].getFarbe().equals(BLACK)){
                ausgabe.add(new Point(xfeld + 1, yfeld - 1));
            }
            if(yfeld == 6 && figuren[xfeld][5] == null && figuren[xfeld][4] == null){
                ausgabe.add(new Point(xfeld, 4));
            }
        }
        else{
            if(figuren[xfeld][yfeld+1] == null){
                ausgabe.add(new Point(xfeld, yfeld+1));
            }
            if(xfeld > 0 && figuren[xfeld-1][yfeld+1] != null &&
                    figuren[xfeld-1][yfeld+1].getFarbe().equals(WHITE)){
                ausgabe.add(new Point(xfeld - 1, yfeld + 1));
            }
            if(xfeld < 7 && figuren[xfeld + 1][yfeld + 1] != null &&
                    figuren[xfeld + 1][yfeld + 1].getFarbe().equals(WHITE)){
                ausgabe.add(new Point(xfeld + 1, yfeld + 1));
            }
            if(yfeld == 1 && figuren[xfeld][2] == null && figuren[xfeld][3] == null){
                ausgabe.add(new Point(xfeld, 3));
            }
        }

        return ausgabe;
    }

}
