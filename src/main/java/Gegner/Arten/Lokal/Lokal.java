package Gegner.Arten.Lokal;

import GUI.GUI_Package;
import Gegner.Gegner;
import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Figuren.Bauer;
import Spiel.TeilvonSpiel.Zug;
import util.ColPrint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Spiel.TeilvonSpiel.Feld.getFiguren;
import static Spiel.TeilvonSpiel.Figur.*;
import static util.ArrayPoint.get;
import static util.Delay.delay;

public class Lokal extends Gegner {
    public static final String LOKAL = "Lokal";

    private Point ausgewählt;
    private Zug ziel;

    private boolean pressedfield;

    public Lokal(String farbe) {
        super(farbe);
    }

    @Override
    public void start(GUI_Package gui) {
        ColPrint.cyan.println("lokal start");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(spiel.mga == null){
                    delay(1);
                }
                spiel.mga.fügeMouseListenerHinzu(new LokalMouseListener(Lokal.this));
            }
        });
        t.start();
        //super.start(gui);
    }

    @Override
    public String returnArt() {
        return "Lokal";
    }

    @Override
    public Zug ziehe(Figur[][] figuren, List<Zug> WeißZüge, List<Zug> SchwarzZüge) {
        /*System.out.println("Lokal eingeben");
        final Zug[] ausgabe = new Zug[1];
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("start thread");
                spiel.dreheBrett();
                updateBrett();
                delay(2000);
                while (ziel == null){
                    delay(1);
                }
                ausgabe[0] = new Zug(ziel.alt, ziel.neu);
                ziel = null;
                spiel.dreheBrett();
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("ausgabe: " + ziel);
        //delay(1000);
        */
        Thread t = new Thread(() -> {
            spiel.dreheBrett();
            ColPrint.red.print("wait on pressedfield");
            while (pressedfield == false){

            }
            ColPrint.red.println(" - end");
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return AlleMöglichenZügeEinerFarbe(figuren, WeißZüge, SchwarzZüge, farbe).get(0);
    }

    public void aufBrettGeklickt(int x, int y){
        pressedfield = true;
        /*
        Point point = new Point(x, y);
        if(!BauernAuswahl()) {
            if (ausgewählt == null && get(spiel.felder, point).getFigur().getFarbe().equals(farbe)) {
                ausgewählt = point;
            }
            else if (ausgewählt == point){
                spiel.clearMöglicheZüge();
                ausgewählt = null;
                spiel.SchachCheck(farbe);
            }
            else if(spiel.möglicheZüge(ausgewählt).contains(point)){
                if(get(spiel.felder, ausgewählt).getFigur().equals(new Bauer("w"))
                    && ausgewählt.y == 1){

                }
                else if(get(spiel.felder, ausgewählt).getFigur().equals(new Bauer("b"))
                    && ausgewählt.y == 6){

                }
                else{
                    ziel = new Zug(ausgewählt, point);
                    ausgewählt = null;
                    spiel.clearMöglicheZüge();
                }
            }
        }
        */
    }

    boolean BauernAuswahl(){
        return isDran() && spiel.mga.BauernAuswahlSichtbar();
    }

    void updateBrett(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                spiel.mga.updateBrett(spiel.felder);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("updatedBrett");
    }

}
