package util;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Diese Klasse ist dafür da, um bestimmte graphische Objekte in einem neuen JFramen anzeigen zu lassen
 */
public class ShowInJFrame {
    /**
     * Hier wird ein BufferedImage angezeigt
     */
    public static void show (BufferedImage bi){
        JFrame jFrame = new JFrame("Show_in_JFrame");
        jFrame.setSize(bi.getWidth(), bi.getHeight());
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(bi));

        jFrame.add(label);

        jFrame.pack();
        jFrame.setVisible(true);

    }
}
