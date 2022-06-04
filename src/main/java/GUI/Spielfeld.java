package GUI;

import Spiel.TeilvonSpiel.Feld;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class Spielfeld {
    public static final int BREITE = 500;
    private BufferedImage feld;
    public Spielfeld(){
        final int breitefeld = BREITE/8;
        feld = new BufferedImage(BREITE, BREITE, BufferedImage.TYPE_INT_ARGB);
        for (int horizontal = 0; horizontal < 8; horizontal++) {
            for (int vertikal = 0; vertikal < 8; vertikal++) {
                for (int x = vertikal * breitefeld; x < (vertikal+1) * breitefeld; x++) {
                    for (int y = horizontal * breitefeld; y < (horizontal + 1) * breitefeld ; y++) {
                        Color c = new Color(93, 59, 11);
                        if((horizontal + vertikal)%2 == 0){
                            c = new Color(250, 186, 88);
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
    public static BufferedImage bild(Feld[][] felder){
        BufferedImage bi = new BufferedImage(BREITE, BREITE, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                bi.setRGB(x, y, Color.red.getRGB());
            }
        }
        return bi;
    }
}
