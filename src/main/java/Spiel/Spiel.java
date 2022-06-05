package Spiel;

import Spiel.TeilvonSpiel.Feld;

public class Spiel {
    private Feld[][] felder; //x - y (siehe überblick.txt)

    public Spiel(){
        felder = new Feld[8][8];
    }

    public void aufFeldGeklickt(int x, int y){
        System.out.println("Es wurde auf das Feld " + x + " | " + y + " geklickt");
    }
}
