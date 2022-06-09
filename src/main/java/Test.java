import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Spiel.TeilvonSpiel.Feld;
import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Figuren.*;

import static Spiel.TeilvonSpiel.Figur.MöglicheZüge;
import static Spiel.TeilvonSpiel.Figur.ausgeben;
import static util.FormatPoint.format;
import static util.StringFormat.getFormat;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame("frame");
        JPanel panel = new JPanel();

        JLabel label1 = new JLabel("erstes");
        panel.add(label1);

        JButton button = new JButton("zweites");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("visibility label1");
                System.out.println(label1.isVisible());
                label1.setVisible(!label1.isVisible());
            }
        });
        panel.add(button);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }
}
