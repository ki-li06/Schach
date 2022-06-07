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
    protected List<Point> möglicheZüge_ohneSchach(Figur [][] figuren, Zug letzterZug, int xfeld, int yfeld){
        List<Point> ausgabe = new ArrayList<>();
        String farbe = figuren[xfeld][yfeld].getFarbe();
        //links oben
        for (int i = xfeld +1; i >= 0 ; i--) {
            yfeld--;
            if(figuren[i][yfeld]== null){
                ausgabe.add(new Point(i,yfeld));
            }
            else if (figuren[i][yfeld].getFarbe().equals(andereFarbe(farbe))){
                ausgabe.add(new Point(i,yfeld));
                break;
            }
            else if (figuren[i][yfeld].getFarbe().equals(farbe)){
                break;
            }
        }

        //rechts oben
        for (int i = xfeld + 1; i < 8; i++) {
            yfeld--;
            if(figuren[i][yfeld]== null){
                ausgabe.add(new Point(i,yfeld));
            }
            else if (figuren[i][yfeld].getFarbe().equals(andereFarbe(farbe))){
                ausgabe.add(new Point(i,yfeld));
                break;
            }
            else if (figuren[i][yfeld].getFarbe().equals(farbe)){
                break;
            }
        }
        //links unten
        for (int i = xfeld +1; i >= 0 ; i--) {
            yfeld++;
            if(figuren[i][yfeld]== null){
                ausgabe.add(new Point(i,yfeld));
            }
            else if (figuren[i][yfeld].getFarbe().equals(andereFarbe(farbe))){
                ausgabe.add(new Point(i,yfeld));
                break;
            }
            else if (figuren[i][yfeld].getFarbe().equals(farbe)){
                break;
            }
        }
        //rechts unten
        for (int i = xfeld +1; i < 8; i++) {
            yfeld++;
            if(figuren[i][yfeld]== null){
                ausgabe.add(new Point(i,yfeld));
            }
            else if (figuren[i][yfeld].getFarbe().equals(andereFarbe(farbe))){
                ausgabe.add(new Point(i,yfeld));
                break;
            }
            else if (figuren[i][yfeld].getFarbe().equals(farbe)){
                break;
            }
        }
        return ausgabe;

    }

}
