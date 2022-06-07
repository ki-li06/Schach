package Spiel.TeilvonSpiel.Figuren;


import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static util.FormatPoint.format;

public class König extends Figur {
    public König (String farbe)
    {
        this.farbe = farbe;
        name = "K";
    }

    @Override
    protected List<Point> möglicheZüge_ohneSchach(Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge, int xfeld, int yfeld) {
        System.out.println("springer möglich");
        String farbe = figuren[xfeld][yfeld].getFarbe();

        List<Point> relativ = new ArrayList<>();
        //alle Punkte relativ gesehen
        relativ.add(new Point(-1, -1));
        relativ.add(new Point(-1, 0));
        relativ.add(new Point(-1, -1));
        relativ.add(new Point(1, -1));
        relativ.add(new Point(1, 0));
        relativ.add(new Point(1, 1));
        relativ.add(new Point(0, -1));
        relativ.add(new Point(0, 1));

        System.out.println("relativ: " + format(relativ));
        List<Point> möglich = new ArrayList<>();
        for (Point p : relativ) {
            if((xfeld + p.x) >= 0 && (xfeld + p.x) < 8 &&
                    (yfeld + p.y) >= 0 && (yfeld + p.y) < 8 &&
                    (figuren[xfeld + p.x][yfeld + p.y] == null ||
                            figuren[xfeld + p.x][yfeld + p.y].getFarbe().equals(andereFarbe(farbe)))){
                möglich.add(new Point(xfeld + p.x, yfeld + p.y));
            }

        }
        //En Passant
        if(farbe.equals(WHITE)){
            boolean königbewegt = WeißZüge.stream().map(
                    i-> i.alt.x == 4 && i.alt.y == 0
            ).toList().contains(true);
            if(!königbewegt){
                System.out.println("könig nicht bewegt");
            }
        }
        else{

        }

        return möglich;
    }
}
