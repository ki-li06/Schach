package GUI;

import Spiel.TeilvonSpiel.Feld;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Spielfeld {
    public static int BREITE = breite(); //520
    public static final Color FARBE_DUNKEL = new Color(159, 101, 19);
    public static final Color FARBE_HELL = new Color(253, 204, 132);

    private final BufferedImage feld;
    public Spielfeld(){
        final int breitefeld = BREITE/8;
        feld = new BufferedImage(BREITE, BREITE, BufferedImage.TYPE_INT_ARGB);
        for (int horizontal = 0; horizontal < 8; horizontal++) {
            for (int vertikal = 0; vertikal < 8; vertikal++) {
                for (int x = vertikal * breitefeld; x < (vertikal+1) * breitefeld; x++) {
                    for (int y = horizontal * breitefeld; y < (horizontal + 1) * breitefeld ; y++) {
                        Color c = FARBE_DUNKEL;
                        if((horizontal + vertikal)%2 == 0){
                            c = FARBE_HELL;
                        }
                        feld.setRGB(x, y, c.getRGB());
                    }
                }
            }
        }

    }
    public BufferedImage getFeld(){
        return feld;
    }
    public static int BreiteEinFeld(){
        return BREITE/8;
    }
    public static BufferedImage bild(Feld[][] felder, boolean gedreht){
        int einfeldbreite = BREITE/8;
        Spielfeld sf = new Spielfeld();
        BufferedImage bi = sf.getFeld();
        Graphics2D g2D = bi.createGraphics();
        for (int i1 = 0; i1 < felder.length; i1++) {
            for (int i2 = 0; i2 < felder[i1].length; i2++) {
                int x;
                int y;
                if(gedreht){
                    x = BREITE - einfeldbreite * (i1 + 1);
                    y = BREITE - einfeldbreite * (i2 + 1);
                }
                else{
                    x = einfeldbreite * i1;
                    y = einfeldbreite * i2;
                }
                if(felder[i1][i2].getStatus() != null){
                    g2D.drawImage(
                            felder[i1][i2].getStatus().getBild(),
                            x,
                            y,
                            null
                    );
                }
                if(felder[i1][i2].getFigur() != null) {
                    g2D.drawImage(felder[i1][i2].getFigur().getBild(),
                            x,
                            y,
                            null);
                }
            }
        }
        g2D.dispose();
        return bi;
    }

    private static int breite(){
        int sizeplayerbox = 62;
        int sizescreen = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int ergebnis = sizescreen - 35 - 30 - sizeplayerbox * 2;
        ergebnis = ergebnis - ergebnis%8;
        return ergebnis;
    }
}
