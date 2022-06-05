package Spiel.TeilvonSpiel.Figuren;

import Spiel.TeilvonSpiel.Figur;

import java.awt.image.BufferedImage;
public class Bauer extends Figur {


    public Bauer (String farbe)
    {
        this.farbe = farbe;
        name = "P";
    }
    public boolean möglicherZug (int feldx, int feldy)
    {
        if (farbe == "w "){
             if((feldx == positionX && feldy == positionY -1)||(feldx == positionX && positionY ==6 && feldx ==positionY -2)) {
                 return true;
             }
        }
        if  ((feldx == positionX && feldy == positionY -1)||(feldx == positionX && positionY ==1 && feldx ==positionY +2)){
             return true;
         }
        else {
            return false;
        }


    }
}
