package Gegner.Arten.Lokal;

import GUI.GUI_Package;
import Gegner.Gegner;
import Spiel.TeilvonSpiel.Feld;
import Spiel.TeilvonSpiel.Figuren.Bauer;
import Spiel.TeilvonSpiel.Zug;
import util.ColPrint;

import java.awt.*;

import static GUI.BauernAuswahl.getNummer;
import static Spiel.TeilvonSpiel.Figur.*;
import static util.ArrayPoint.get;
import static util.Delay.delay;
import static util.FormatPoint.format;

public class Lokal extends Gegner {
    public static final String LOKAL = "Lokal";

    private Point ausgewählt;
    private Zug ziel;

    private boolean NameEingegeben = false;


    public Lokal(String farbe) {
        super(farbe);
    }

    public Lokal(String name, String farbe){
        super(farbe);
        this.name = name;
        NameEingegeben = true;
    }

    @Override
    public void start(GUI_Package gui) {
        ColPrint.cyan.println("lokal start");
        addMouseListeners();
    }

    public void addMouseListeners(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (spiel.mga == null) {
                    delay(1);
                }

                spiel.mga.fügeMouseListenerHinzu(new LokalMouseListener(Lokal.this));
                spiel.mga.fügeMouseListenerBauernAuswahlHinzu(new LokalBauernAuswahlMouseListener(Lokal.this));
            }
        });
        t.start();
    }

    @Override
    public String returnArt() {
        return "Lokal";
    }

    public boolean GegnerIsLokal(){
        Gegner gegner = spiel.selbst.gegner;
        if(spiel.selbst.getGegner().equals(this)){
            gegner = spiel.gegner.gegner;
        }
        return gegner instanceof Lokal;
    }

    @Override
    public Zug ziehe() {
        delay(100L);
        if(GegnerIsLokal() && farbe.equals(BLACK) && spiel.weiß().züge.size() != 0) {
            spiel.dreheBrett();
        }
        else if(!GegnerIsLokal() && farbe.equals(BLACK) && spiel.weiß().züge.size() == 1){
            spiel.dreheBrett();
        }
        while (ziel == null) {
            delay(1);
        }
        if(!farbe.equals(WHITE) && spiel.weiß().züge.size() != 0) {
            spiel.dreheBrett();
        }

        Zug ausgabe = ziel;
        ziel = null;

        System.out.println("ausgabe: " + ausgabe);
        return ausgabe;
    }

    public void aufBrettGeklickt(int x, int y) {
        Point point = new Point(x, y);
        if (!BauernAuswahl()) {
            if (ausgewählt != null && ausgewählt.equals(point)) {
                spiel.clearMöglicheZüge();
                ausgewählt = null;
                spiel.SchachCheck(farbe);
            } else if (get(spiel.felder, point).getFigur() != null && get(spiel.felder, point).getFigur().getFarbe().equals(farbe)) {
                if (ausgewählt != null) {
                    spiel.clearMöglicheZüge();
                }
                ausgewählt = point;
                get(spiel.felder, ausgewählt).setStatus(Feld.Status.AUSGEWÄHLT());
                spiel.updateBrett();
                spiel.zeigeMöglicheZüge(ausgewählt);
                System.out.println("ausgewählt: " + format(ausgewählt));
            } else if (spiel.möglicheZüge(ausgewählt).contains(point)) {
                spiel.clearMöglicheZüge();
                get(spiel.felder, ausgewählt).setStatus(null);
                if ((get(spiel.felder, ausgewählt).getFigur().equals(new Bauer("w"))
                        && point.y == 0)
                        ||
                        (get(spiel.felder, ausgewählt).getFigur().equals(new Bauer("b"))
                                && point.y == 7)
                ) {
                    get(spiel.felder, point).setStatus(Feld.Status.BAUERNUMWANDLUNG());
                    get(spiel.felder, ausgewählt).setStatus(Feld.Status.AUSGEWÄHLT());
                    spiel.updateBrett();
                    spiel.mga.zeigeBauernAuswahl(farbe);
                } else {
                    ziel = new Zug(ausgewählt, point);
                    ausgewählt = null;
                    spiel.clearMöglicheZüge();
                }
            }
        } else {
            Point bU = spiel.indexOfBauernUmwandlung();
            if (point.equals(ausgewählt) || get(spiel.felder, point).getFigur() == null) {
                get(spiel.felder, bU).setStatus(null);
                spiel.mga.macheBauernAuswahlUnsichtbar();
                spiel.zeigeMöglicheZüge(ausgewählt);
            } else if (!point.equals(ausgewählt) && get(spiel.felder, point).getFigur() != null &&
                    get(spiel.felder, point).getFigur().getFarbe().equals(farbe)
            ) {
                spiel.mga.macheBauernAuswahlUnsichtbar();
                get(spiel.felder, bU).setStatus(null);
                get(spiel.felder, ausgewählt).setStatus(null);
                spiel.updateBrett();
                ausgewählt = point;
                spiel.zeigeMöglicheZüge(ausgewählt);
            }
        }
    }

    public void aufBauernAuswahlGeklickt(int index) {
        Point bU = spiel.indexOfBauernUmwandlung();
        int xindex = bU.x;
        int yindex = getNummer(index, farbe);
        ziel = new Zug(ausgewählt, new Point(xindex, yindex));
        get(spiel.felder, bU).setStatus(null);
        spiel.mga.macheBauernAuswahlUnsichtbar();

    }

    boolean BauernAuswahl() {
        return spiel.mga.BauernAuswahlSichtbar() && isDran();
    }


}
