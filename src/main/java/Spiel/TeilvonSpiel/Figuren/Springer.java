package Spiel.TeilvonSpiel.Figuren;

import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static util.FormatPoint.format;

public class Springer extends Figur {
    public Springer (String farbe)
    {
        this.farbe = farbe;
        name = "N";
    }

    @Override
    protected List<Point> möglicheZüge_ohneSchach(Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge, int xfeld, int yfeld) {
        //System.out.println("springer möglich " + xfeld + " | " + yfeld);
        String farbe = figuren[xfeld][yfeld].getFarbe();

        List<Point> relativ = new ArrayList<>();
        //alle Punkte relativ gesehen
        relativ.add(new Point(-1, -2));
        relativ.add(new Point(-2, -1));
        relativ.add(new Point(2, 1));
        relativ.add(new Point(1, 2));
        relativ.add(new Point(-1, 2));
        relativ.add(new Point(2, -1));
        relativ.add(new Point(1, -2));
        relativ.add(new Point(-2, 1));

        //System.out.println("relativ: " + format(relativ));
        List<Point> möglich = new ArrayList<>();
        for (Point p : relativ) {
            if((xfeld + p.x) >= 0 && (xfeld + p.x) < 8 &&
                    (yfeld + p.y) >= 0 && (yfeld + p.y) < 8 &&
                    (figuren[xfeld + p.x][yfeld + p.y] == null ||
                            figuren[xfeld + p.x][yfeld + p.y].getFarbe().equals(andereFarbe(farbe)))){
                möglich.add(new Point(xfeld + p.x, yfeld + p.y));
            }

        }
        return möglich;
    }

}
