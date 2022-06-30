package GUI.BauernAuswahl;

import Spiel.Spiel_Backup;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static GUI.BauernAuswahl.BauernAuswahl.BreiteStreifen;
import static GUI.Spielfeld.BreiteEinFeld;

public class BauernAuswahlMouseListener implements MouseListener {
    private final Spiel_Backup spielBackup;
    public BauernAuswahlMouseListener(Spiel_Backup s){
        spielBackup = s;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int ausgabe = -1;
        int x = e.getX();
        int[] lower = new int[4];
        int[] higher = new int[4];
        for (int i = 0; i < 4; i++) {
            lower[i] = i * (BreiteStreifen + BreiteEinFeld());
            higher[i] = lower[i] + BreiteEinFeld();
        }
        for (int i = 0; i < 4; i++) {
            if(x > lower[i] && x < higher[i]){
                ausgabe = i;
            }
        }
        if(spielBackup.BauernAuswahl()) {
            spielBackup.aufBauernAuswahlGeklickt(ausgabe);
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
