package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Spielfeld {
    public static final int BREITE = 600;
    private BufferedImage feld;
    public Spielfeld(){
        final int breitefeld = BREITE/8;
        feld = new BufferedImage(BREITE, BREITE, BufferedImage.TYPE_INT_RGB);
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
    public JPanel asJPanel(){
        class ImagePanel extends JPanel{

            private BufferedImage image;

            public ImagePanel(BufferedImage bi) {
                image = bi;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
            }

        }
        ImagePanel ip = new ImagePanel(feld);
        return (JPanel) ip;
    }
}
