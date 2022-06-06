package Spiel.TeilvonSpiel.Figuren;

import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Turm extends Figur {
    public Turm (String farbe)
    {
        this.farbe = farbe;
        name = "R";
    }

    @Override
    protected List<Point> möglicheZüge_ohneSchach(Figur[][] figuren, Zug letzterZug, int xfeld, int yfeld) {
        List<Point> ausgabe = new ArrayList<>();
        String farbe = figuren[xfeld][yfeld].getFarbe();

        for (int i = xfeld; i < 8; i++) {
            if(figuren[i][yfeld] == null){
                ausgabe.add(new Point(i, yfeld));
            }
            else if(figuren[i][yfeld].getFarbe().equals(farbe)){
                break;
            }
            else if(figuren[i][yfeld].getFarbe().equals(andereFarbe(farbe))){
                ausgabe.add(new Point(i, yfeld));
                break;
            }
        }
        return ausgabe;
    }
}
