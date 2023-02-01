package Gegner.Arten.OwnBot;

import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Zug;
import util.Tree;

import java.util.List;

import static Spiel.TeilvonSpiel.Feld.getFiguren;
import static Spiel.TeilvonSpiel.Figur.AlleMöglichenZügeEinerFarbe;

public class Berechnung extends Tree<Berechnung> {
    private String farbe;
    private Figur[][] figuren;
    private List<Zug> weißZüge;
    private List<Zug> schwarzZüge;

    public Berechnung(String farbe, Figur[][] figuren, List<Zug> weißZüge, List<Zug> schwarzZüge) {
        this.farbe = farbe;
        this.figuren = figuren;
        this.weißZüge = weißZüge;
        this.schwarzZüge = schwarzZüge;
    }

    public Zug getBesterZug(){
        List<Zug> alleMöglichenZüge = AlleMöglichenZügeEinerFarbe(figuren, weißZüge, schwarzZüge, farbe);
        return alleMöglichenZüge.get(0);
    }
}
