package Spielertypen.Arten.Lokal;

import util.ColPrint;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static GUI.Spielfeld.BreiteEinFeld;
import static Spiel.TeilvonSpiel.Figur.BLACK;

public class LokalMouseListener implements MouseListener {
    private Lokal lokal;

    public LokalMouseListener(Lokal l){
        this.lokal = l;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(lokal.isDran()) {
            ColPrint.white.println("LokalMouseListener mouseClicked");
            int x = e.getX();
            int y = e.getY();
            x = (x - x % BreiteEinFeld()) / BreiteEinFeld();
            y = (y - y % BreiteEinFeld()) / BreiteEinFeld();
            if(lokal.getFarbe().equals(BLACK)) {
                x = 7 - x;
                y = 7 - y;
            }
            if (lokal.isDran()) {
                int finalx = x;
                int finaly = y;
                Thread t = new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                lokal.aufBrettGeklickt(finalx, finaly);
                            }
                        }
                        , "Class: LokalMouseListener - Method: mouseClicked"
                );
                t.start();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
