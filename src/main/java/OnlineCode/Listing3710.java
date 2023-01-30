package OnlineCode;
/* Listing3710.java */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * nicht selbst programmiert
 */

public class Listing3710
        extends JFrame
        implements ActionListener {
    static final String[] DATA = {
            "Hund", "Katze", "Meerschweinchen", "Tiger", "Maus",
            "Fisch", "Leopard", "Schimpanse", "Kuh", "Pferd",
            "Reh", "Huhn", "Marder", "Adler", "Nilpferd"
    };

    private JList list;

    public Listing3710() {
        super("JList");
        //addWindowListener(new WindowClosingAdapter(true));
        Container cp = getContentPane();
        //Liste
        list = new JList(DATA);
        list.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
        );
        list.setSelectedIndex(2);
        cp.add(new JScrollPane(list), BorderLayout.CENTER);
        //Ausgabe-Button
        JButton button = new JButton("Ausgabe");
        button.addActionListener(this);
        cp.add(button, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent event) {
        String cmd = event.getActionCommand();
        if (cmd.equals("Ausgabe")) {
            System.out.println("---");
            ListModel lm = list.getModel();
            int[] sel = list.getSelectedIndices();
            for (int i = 0; i < sel.length; ++i) {
                String value = (String) lm.getElementAt(sel[i]);
                System.out.println("  " + value);
            }
        }
    }

    public static void main(String[] args) {
        Listing3710 frame = new Listing3710();
        frame.setLocation(100, 100);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}