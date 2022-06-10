package Spiel.TeilvonSpiel.Figuren;

import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Dame extends Figur {
    public Dame(String farbe) {
        this.farbe = farbe;
        name = "Q";
    }

    @Override
    protected List<Point> möglicheZüge_ohneSchach(Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge, int xfeld, int yfeld) {
        List<Point> ausgabe = new ArrayList<>();
        String farbe = figuren[xfeld][yfeld].getFarbe();

        //Turm
        //alle rechts davon
        for (int i = xfeld + 1; i < 8; i++) {
            if (figuren[i][yfeld] == null) {
                ausgabe.add(new Point(i, yfeld));
            } else if (figuren[i][yfeld].getFarbe().equals(farbe)) {
                break;
            } else if (figuren[i][yfeld].getFarbe().equals(andereFarbe(farbe))) {
                ausgabe.add(new Point(i, yfeld));
                break;
            }
        }
        //alle links davon
        for (int i = xfeld - 1; i >= 0; i--) {
            if (figuren[i][yfeld] == null) {
                ausgabe.add(new Point(i, yfeld));
            } else if (figuren[i][yfeld].getFarbe().equals(farbe)) {
                break;
            } else if (figuren[i][yfeld].getFarbe().equals(andereFarbe(farbe))) {
                ausgabe.add(new Point(i, yfeld));
                break;
            }
        }
        //alle unterhalb
        for (int i = yfeld + 1; i < 8; i++) {
            if (figuren[xfeld][i] == null) {
                ausgabe.add(new Point(xfeld, i));
            } else if (figuren[xfeld][i].getFarbe().equals(farbe)) {
                break;
            } else if (figuren[xfeld][i].getFarbe().equals(andereFarbe(farbe))) {
                ausgabe.add(new Point(xfeld, i));
                break;
            }
        }
        //alle oberhalb
        for (int i = yfeld - 1; i >= 0; i--) {
            if (figuren[xfeld][i] == null) {
                ausgabe.add(new Point(xfeld, i));
            } else if (figuren[xfeld][i].getFarbe().equals(farbe)) {
                break;
            } else if (figuren[xfeld][i].getFarbe().equals(andereFarbe(farbe))) {
                ausgabe.add(new Point(xfeld, i));
                break;
            }
        }

        //Läufer
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
