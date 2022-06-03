import GUI.Spielfeld;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class Test {
    static JFrame frame;
    static JPanel panel;
    static JButton button;
    static JLabel label;

    static int score1 = 0;
    static int score2 = 0;
    public static void main(String[] args) {
        frame = new JFrame("uberschrift");

        panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 5;
        gbc.gridy = 1;
        label = new JLabel();
        gbc.anchor = GridBagConstraints.BASELINE;
        label.setText("erster | zweiter");
        panel.add(label, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        button = new JButton();
        button.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pressed-button");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        pressedButton();
                    }
                }).start();
            }
        });
        button.setText("button");
        panel.add(button, gbc);

        frame.add(panel);
        frame.setSize(panel.getWidth(), panel.getHeight());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        System.out.println("erstellt");
    }

    public static void pressedButton(){
        button.setVisible(false);
        label.setVisible(false);

        JLabel blabla = new JLabel();
        blabla.setText("Blabla");
        panel.add(blabla);

        panel.repaint();

        System.out.println("finished");
    }
}
