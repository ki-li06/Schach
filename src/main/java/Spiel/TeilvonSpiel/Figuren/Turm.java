package Spiel.TeilvonSpiel.Figuren;

import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Turm extends Figur {
    public Turm(String farbe) {
        this.farbe = farbe;
        name = "R";
    }

    @Override
    protected List<Point> möglicheZüge_ohneSchach(Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge, int xfeld, int yfeld) {
        List<Point> ausgabe = new ArrayList<>();
        String farbe = figuren[xfeld][yfeld].getFarbe();
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

        return ausgabe;
    }
}
