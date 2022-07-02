package Gegner.Arten.Lokal;

import GUI.BauernAuswahl.BauernAuswahl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static GUI.BauernAuswahl.BauernAuswahl.indexOfClickedField;

public class LokalBauernAuswahlMouseListener implements MouseListener {
    private Lokal lokal;

    public LokalBauernAuswahlMouseListener(Lokal l){
        lokal = l;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(lokal.BauernAuswahl()){
            int finalausgabe = indexOfClickedField(e.getPoint());
            Thread t = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            lokal.aufBauernAuswahlGeklickt(finalausgabe);
                        }
                    }
                    , "Class: LokalBauernAuswahlMouseListener - Method: mouseClicked"
            );
            t.start();
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
