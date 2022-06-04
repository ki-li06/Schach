import GUI.Spielfeld;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static GUI.Spielfeld.BREITE;
import static util.Circle.circle;
import static util.Random.random;
import static util.StringFormat.getFormat;

public class Test {
    public static void main(String[] args) {
        int breite = BREITE/8;
        BufferedImage bi = new BufferedImage(breite, breite, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setColor(Spielfeld.FARBE_HELL);
        g2d.fillRect(0, 0, breite, breite);
        g2d.dispose();
        try{
            ImageIO.write(bi, "png", new File("bild.png"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
