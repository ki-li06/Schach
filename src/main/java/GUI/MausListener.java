package GUI;

import Spiel.Spiel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static GUI.Spielfeld.BreiteEinFeld;

public class MausListener implements MouseListener {
    private Spiel spiel;

    public MausListener(Spiel s){
        spiel = s;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        x = (x-x%BreiteEinFeld())/BreiteEinFeld();
        y = (y-y%BreiteEinFeld())/BreiteEinFeld();
        if(!spiel.WeißDran()){
            x = 7 - x;
            y = 7 - y;
        }
        spiel.aufBrettGeklickt(x, y);
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
