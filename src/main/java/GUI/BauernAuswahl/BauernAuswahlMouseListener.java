package GUI.BauernAuswahl;

import Spiel.Spiel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static GUI.BauernAuswahl.BauernAuswahl.*;

public class BauernAuswahlMouseListener implements MouseListener {
    private final Spiel spiel;

    public BauernAuswahlMouseListener(Spiel s) {
        spiel = s;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (spiel.mga.BauernAuswahlSichtbar() && spiel.selbstDran()) {
            int finalAusgabe = indexOfClickedField(e.getPoint());
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    spiel.aufBauernAuswahlGeklickt(finalAusgabe);
                }
                },
                "Class BauernAuswahlMouseListener - Method: mouseClicked");
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
