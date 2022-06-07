package Spiel.TeilvonSpiel.Figuren;

import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
public class Läufer extends Figur {
    public Läufer (String farbe)
    {
        this.farbe = farbe;
        name = "B";
    }

    @Override
    protected List<Point> möglicheZüge_ohneSchach(Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge, int xfeld, int yfeld) {
        List<Point> ausgabe = new ArrayList<>();
        String farbe = figuren[xfeld][yfeld].getFarbe();

        int xi = 0;         // xindex
        int yi = 0;         // yindex

        //links oben
        xi = xfeld - 1;
        yi = yfeld - 1;
        while ((xi < 8) && (xi >= 0) && (yi < 8) && (yi >= 0)){
            if(figuren[xi][yi]== null){
                ausgabe.add(new Point(xi,yi));
            }
            else if (figuren[xi][yi].getFarbe().equals(andereFarbe(farbe))){
                ausgabe.add(new Point(xi,yi));
                break;
            }
            else if (figuren[xi][yi].getFarbe().equals(farbe)){
                break;
            }
            xi--;
            yi--;
        }

        //links unten
        xi = xfeld - 1;
        yi = yfeld + 1;
        while ((xi < 8) && (xi >= 0) && (yi < 8) && (yi >= 0)){
            if(figuren[xi][yi]== null){
                ausgabe.add(new Point(xi,yi));
            }
            else if (figuren[xi][yi].getFarbe().equals(andereFarbe(farbe))){
                ausgabe.add(new Point(xi,yi));
                break;
            }
            else if (figuren[xi][yi].getFarbe().equals(farbe)){
                break;
            }
            xi--;
            yi++;
        }

        //rechts oben
        xi = xfeld + 1;
        yi = yfeld - 1;
        while ((xi < 8) && (xi >= 0) && (yi < 8) && (yi >= 0)){
            if(figuren[xi][yi]== null){
                ausgabe.add(new Point(xi,yi));
            }
            else if (figuren[xi][yi].getFarbe().equals(andereFarbe(farbe))){
                ausgabe.add(new Point(xi,yi));
                break;
            }
            else if (figuren[xi][yi].getFarbe().equals(farbe)){
                break;
            }
            xi++;
            yi--;
        }

        //rechts unten
        xi = xfeld + 1;
        yi = yfeld + 1;
        while ((xi < 8) && (xi >= 0) && (yi < 8) && (yi >= 0)){
            if(figuren[xi][yi]== null){
                ausgabe.add(new Point(xi,yi));
            }
            else if (figuren[xi][yi].getFarbe().equals(andereFarbe(farbe))){
                ausgabe.add(new Point(xi,yi));
                break;
            }
            else if (figuren[xi][yi].getFarbe().equals(farbe)){
                break;
            }
            xi++;
            yi++;
        }

        return ausgabe;
    }

}
