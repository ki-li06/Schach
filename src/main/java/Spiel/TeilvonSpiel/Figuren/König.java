package Spiel.TeilvonSpiel.Figuren;


import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static util.FormatPoint.format;

public class König extends Figur {
    public König (String farbe)
    {
        this.farbe = farbe;
        name = "K";
    }

    @Override
    protected List<Point> möglicheZüge_ohneSchach(Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge, int xfeld, int yfeld) {
        System.out.println("könig möglich");
        String farbe = figuren[xfeld][yfeld].getFarbe();

        List<Point> relativ = new ArrayList<>();
        //alle Punkte relativ gesehen
        IntStream.rangeClosed(-1, 1).forEach(x ->
                IntStream.rangeClosed(-1, 1).forEach(y ->
                        relativ.add(new Point(x, y))
                )
        );
        relativ.remove(new Point(0, 0));

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

        //En Passant
        if(farbe.equals(WHITE)){
            boolean königbewegt = WeißZüge.stream()
                    .map(i-> i.alt.x == 4 && i.alt.y == 0).toList().contains(true);
            List<Point> SchwarzMöglicheZüge = AlleMöglicheZügeEinerFarbe(figuren, WeißZüge, SchwarzZüge, BLACK);
            boolean schach = SchwarzMöglicheZüge.contains(new Point(4, 0));
            if(!königbewegt && !schach){
                System.out.println("könig nicht bewegt und nicht im schach");
                System.out.println("lange rochade");
                boolean Turmbewegt = WeißZüge.stream()
                        .map(i-> i.alt.x == 0 && i.alt.y == 7).toList().contains(true);
                boolean Turmgeschlagen = SchwarzZüge.stream()
                        .map(i-> i.neu.x == 0 && i.neu.y == 7).toList().contains(true);
                boolean WegLangMöglich =
                        !IntStream.rangeClosed(1, 3).mapToObj(
                                i->figuren[i][7] != null).toList().contains(true);
                boolean SchachAufDemWeg =
                        SchwarzMöglicheZüge.contains(new Point(2, 7)) ||
                                SchwarzMöglicheZüge.contains(new Point(3, 7));
                System.out.println("aturmbewegt: " + Turmbewegt);
                System.out.println("aturmgeschlagen: " + Turmgeschlagen);
                System.out.println("WegLangMöglich: " + WegLangMöglich );
                System.out.println("SchachAufDemWeg: " + SchachAufDemWeg);
            }
        }
        else{

        }

        return möglich;
    }
}
