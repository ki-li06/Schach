package Spiel.TeilvonSpiel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class Figur {
    public static final String[] figurennamen = new String[]{"P", "R", "B", "N", "Q", "K"};
    public static final String[] farben = new String[]{"w", "b"};

    protected String name;
    protected String farbe;


    public String getName() {
        return name;
    }
    public String getFarbe() {
        return farbe;
    }
    public BufferedImage getBild(){
        File file = new File("Figuren\\" + farbe + name + ".png");
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void setName(String name) {
        if(List.of(figurennamen).contains(name)){
            this.name = name;
        }
        else {
            System.out.println("FEHLER - UNGÜLTIGE NAMEN-EINGABE: " +  name);
        }
    }
    public void setFarbe(String farbe) {
        if(List.of(farben).contains(farbe)) {
            this.farbe = farbe;
        }
        else{
            System.out.println("FEHLER - UNGÜLTIGE FARBEN-EINGABE: " + farbe);
        }
    }

   // public Feld möglicherZug (Feld [][] feld , int positionX, int positionY){

    //}

    @Override
    public String toString() {
        return "Figur{" +
                "name='" + name + '\'' +
                ", farbe='" + farbe + '\'' +
                '}';
    }
}
