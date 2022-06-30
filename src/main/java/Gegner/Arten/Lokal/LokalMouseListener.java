package Gegner.Arten.Lokal;

import util.ColPrint;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static GUI.Spielfeld.BreiteEinFeld;

public class LokalMouseListener implements MouseListener {
    private Lokal lokal;

    public LokalMouseListener(Lokal l){
        this.lokal = l;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ColPrint.white.println("LokalMouseListener mouseClicked");
        int x = e.getX();
        int y = e.getY();
        x = (x-x%BreiteEinFeld())/BreiteEinFeld();
        y = (y-y%BreiteEinFeld())/BreiteEinFeld();
        System.out.println("lokal.isDran() " + lokal.isDran());
        if(lokal.isDran()) {
            lokal.aufBrettGeklickt(x, y);
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
