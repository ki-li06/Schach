package GUI.BauernAuswahl;

import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Figuren.Dame;
import Spiel.TeilvonSpiel.Figuren.Läufer;
import Spiel.TeilvonSpiel.Figuren.Springer;
import Spiel.TeilvonSpiel.Figuren.Turm;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static GUI.Spielfeld.BreiteEinFeld;
import static util.ShowInJFrame.show;

public class BauernAuswahl {
    static final int BreiteStreifen = 2;
    static final int Breite = BreiteEinFeld() * 4 + 3 * BreiteStreifen;

    public static BufferedImage getBildLeer(){
        int höhe = new Dame("w").getBild().getHeight();
        BufferedImage bi = new BufferedImage(Breite, höhe, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bi.createGraphics();
        g2.setColor(Color.lightGray);
        g2.fillRect(0, 0, Breite, höhe);
        g2.setColor(Color.gray);
        for (int i = BreiteEinFeld(); i < Breite; i += BreiteEinFeld()) {
            g2.fillRect(i, 0, BreiteStreifen, höhe);
            i += BreiteStreifen;
        }
        g2.dispose();

        return bi;
    }
    public static BufferedImage getBild(String farbe){
        BufferedImage ausgabe = getBildLeer();
        Graphics2D g2 = ausgabe.createGraphics();
        Figur[] figuren = new Figur[]{
                new Dame(farbe),
                new Springer(farbe),
                new Turm(farbe),
                new Läufer(farbe)};
        int i = 0;
        for (int x = 0; x < Breite; x+= (BreiteEinFeld() + BreiteStreifen)) {
            g2.drawImage(figuren[i].getBild(), x, 0, null);
            i++;
        }
        g2.dispose();
        return ausgabe;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bauern Auswahl");
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(getBild("w")));
        label.addMouseListener(new BauernAuswahlMouseListener(null));

        frame.add(label);
        frame.pack();
        frame.setVisible(true);
    }
}
