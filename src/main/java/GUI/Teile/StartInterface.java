package GUI.Teile;

import GUI.GUI_Package;
import Gegner.Gegner;
import util.ColPrint;

import javax.swing.*;

import static util.Delay.delay;

/**
 * Hier muss der Spielername eingeben und die Art des Gegners auswählen
 */
public class StartInterface {
    private JFrame frame;
    private GroupLayout layout;
    private JLabel aufforderung;
    private JLabel labelName;
    private JTextField name;
    private JLabel gegner;
    private JList<String> gegnerListe;
    private JScrollPane scrollPane;
    private JButton finish;

    private boolean pressedButton;
    private String name_spieler;
    private String gegner_art;



    public StartInterface(){
        JFrame frame = new JFrame("Schach");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        layout = new GroupLayout(panel);
        panel.setLayout(layout);

        erstellen(frame);

        this.frame.pack();
        //Vollbild
        this.frame.setVisible(true);

        while (!pressedButton){
            delay(1);
        }

    }

    public void erstellen(JFrame Frame){
        frame = Frame;
        
        aufforderung = new JLabel("Bitte gebe deinen Namen ein und\n wähle die Art deines Gegners aus!");

        labelName = new JLabel("Name");

        name = new JTextField();

        gegner = new JLabel("Gegner-Art");

        gegnerListe = new JList<>(Gegner.alleGegnerArten());
        gegnerListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gegnerListe.setLayoutOrientation(JList.VERTICAL);
        gegnerListe.setVisibleRowCount(3);

        scrollPane = new JScrollPane(gegnerListe);
        //listScroller.setPreferredSize(new Dimension(600, 250));

        finish = new JButton("Fertig");
        finish.addActionListener(e -> pressedButton());

        JPanel panel = new JPanel();
        layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(labelName)
                                        .addComponent(gegner))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(aufforderung)
                                        .addComponent(name)
                                        .addComponent(scrollPane)
                                )
                                .addGap(18, 18, 18)
                                .addComponent(finish)
                                .addGap(0, 60, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(aufforderung))
                                .addGap(20)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelName)
                                        .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(gegner)
                                                .addComponent(scrollPane)
                                        //.addComponent(button)
                                )
                                .addComponent(finish)
                                .addContainerGap(15, Short.MAX_VALUE))
        );

        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(groupLayout);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
        frame.pack();
        frame.setVisible(true);

    }

    private void pressedButton(){
        System.out.println("pressed button");
        if(gegnerListe.getSelectedIndices().length == 1 && !name.getText().replace(" ", "").equals("")){
            name_spieler = name.getText();
            gegner_art = gegnerListe.getSelectedValue();

            pressedButton = true;
            aufforderung.setVisible(false);
            finish.setVisible(false);
            gegner.setVisible(false);
            gegnerListe.setVisible(false);
            name.setVisible(false);
            labelName.setVisible(false);
            scrollPane.setVisible(false);
        }
    }


    public String getSpielerName(){
        return name_spieler;
    }
    public String getGegnerArt(){
        return gegner_art;
    }

    public GUI_Package getGUI_Package(){
        return new GUI_Package(frame, layout);
    }


    public static void main(String[] args) {
        StartInterface si = new StartInterface();
        ColPrint.red.println("Fertig");
        System.out.println("Spielername: " + si.getSpielerName());
        System.out.println("GegnerArt: " + si.getGegnerArt());
    }
}
