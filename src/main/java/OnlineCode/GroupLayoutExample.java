package OnlineCode;

import java.awt.*;
import javax.swing.*;

import static javax.swing.GroupLayout.Alignment.*;
import static javax.swing.GroupLayout.DEFAULT_SIZE;
import static javax.swing.GroupLayout.PREFERRED_SIZE;

/**
 * nicht selbst programmiert
 */


public class GroupLayoutExample {
    public static void main(String[] args) {
        JLabel lblCardHolder = new JLabel("Card holder");
        JTextField tfCardHolder = new JTextField();
        JLabel lblCardNumber = new JLabel("Card number");
        JTextField tfCardNumber = new JTextField();
        JLabel lblExpirationDate = new JLabel("Expiration date");
        JTextField tfExpirationDate = new JTextField();
        JComboBox combo = new JComboBox(new String[]{"Visa", "mastercard"});

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(lblCardHolder)
                        .addComponent(lblCardNumber)
                        .addComponent(lblExpirationDate))
                .addGroup(layout.createParallelGroup()
                        .addComponent(tfCardHolder)
                        .addComponent(tfCardNumber)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(tfExpirationDate)
                                .addComponent(combo, DEFAULT_SIZE, 100, PREFERRED_SIZE))));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(lblCardHolder)
                        .addComponent(tfCardHolder))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(lblCardNumber)
                        .addComponent(tfCardNumber))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(lblExpirationDate)
                        .addComponent(tfExpirationDate)
                        .addComponent(combo)));

        JFrame f = new JFrame();
        f.setContentPane(panel);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
 