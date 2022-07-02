package Gegner.Arten.Lokal;

import GUI.BauernAuswahl.BauernAuswahlMouseListener;
import GUI.GUI_Package;
import Gegner.Gegner;
import Spiel.TeilvonSpiel.Feld;
import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Figuren.Bauer;
import Spiel.TeilvonSpiel.Zug;
import util.ColPrint;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.attribute.AclEntry;
import java.util.ArrayList;
import java.util.List;

import static GUI.BauernAuswahl.BauernAuswahl.getNummer;
import static Spiel.TeilvonSpiel.Feld.getFiguren;
import static Spiel.TeilvonSpiel.Figur.*;
import static util.ArrayPoint.get;
import static util.Delay.delay;
import static util.FormatPoint.format;

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
                while (spiel.mga == null) {
                    delay(1);
                }
                spiel.mga.fügeMouseListenerHinzu(new LokalMouseListener(Lokal.this));
                spiel.mga.fügeMouseListenerBauernAuswahlHinzu(new LokalBauernAuswahlMouseListener(Lokal.this));
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
    public Zug ziehe() {
        delay(100L);
        spiel.dreheBrett();
        System.out.println("wait - ");
        while (ziel == null) {
            delay(1);
        }
        spiel.dreheBrett();

        Zug ausgabe = ziel;
        ziel = null;

        System.out.println("ausgabe: " + ausgabe);
        Figur[][] figuren = getFiguren(spiel.felder);
        List<Zug> weißZüge = spiel.weiß.züge;
        List<Zug> schwarzZüge = spiel.schwarz.züge;
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
