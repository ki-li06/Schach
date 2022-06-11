import Spiel.TeilvonSpiel.Ende;
import util.ColPrint;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static util.Listen.getLast;

public class Test {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("text");
        JLabel label = new JLabel();
        label.setText("oben\nunten");

        jFrame.add(label);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
