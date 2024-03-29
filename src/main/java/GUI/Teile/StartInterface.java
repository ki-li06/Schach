package GUI.Teile;

import GUI.GUI_Package;
import Spielertypen.Spielertyp;
import util.ColPrint;

import javax.swing.*;

import static Spiel.TeilvonSpiel.Figur.BLACK;
import static Spiel.TeilvonSpiel.Figur.WHITE;
import static util.Delay.delay;
import static util.StringFormat.clearDoubleSpaces;

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
    private JScrollPane artenScrollPane;
    private JLabel farbe;
    private JList<String> farbenListe;
    private JScrollPane farbenScrollPane;
    private JButton finish;

    private boolean pressedButton;
    private String name_spieler;
    private String gegner_art;
    private String farbeSelected;



    public StartInterface(){
        ColPrint.cyan.println("Erstelle StartInterface");
        JFrame frame = new JFrame("Schach");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        layout = new GroupLayout(panel);
        panel.setLayout(layout);

        erstellen(frame);


        while (!pressedButton){
            delay(1);
        }
        ColPrint.cyan.println("Ende StartInterface");


    }

    public void erstellen(JFrame Frame){
        frame = Frame;
        
        aufforderung = new JLabel(
                "Bitte gebe deinen Namen ein und wähle die Art deines Gegners aus!");

        labelName = new JLabel("Name:");

        name = new JTextField();

        farbe = new JLabel("Eigene Farbe: ");

        farbenListe = new JList<>(new String[]{"Weiß", "Schwarz"});
        farbenListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        farbenListe.setLayoutOrientation(JList.VERTICAL);
        farbenListe.setVisibleRowCount(2);

        farbenScrollPane = new JScrollPane(farbenListe);


        gegner = new JLabel("Gegner-Art: ");

        gegnerListe = new JList<>(Spielertyp.alleGegnerArten());
        gegnerListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        gegnerListe.setLayoutOrientation(JList.VERTICAL);
        gegnerListe.setVisibleRowCount(3);

        artenScrollPane = new JScrollPane(gegnerListe);
        //listScroller.setPreferredSize(new Dimension(600, 250));


        finish = new JButton("Fertig");
        finish.addActionListener(e -> pressedButton());

        final int BREITE = 300;

        JPanel panel = new JPanel();
        layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(labelName)
                                        .addComponent(farbe)
                                        .addComponent(gegner))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(aufforderung)
                                        .addComponent(name)
                                        .addComponent(farbenScrollPane)
                                        .addComponent(artenScrollPane)
                                )
                                .addGap(30)
                                .addComponent(finish)
                                .addGap(30)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(aufforderung))
                                .addGap(20)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(labelName)
                                        .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(farbe)
                                        .addComponent(farbenScrollPane))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(gegner)
                                                .addComponent(artenScrollPane)
                                )
                                .addGap(20)
                                .addComponent(finish)
                                .addContainerGap(15, Short.MAX_VALUE)
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
        frame.setLocation(0,5);
    }

    private void pressedButton(){
        ColPrint.white.println("StartInterface: pressed button 'Fertig'");
        String nameString = name.getText();
        nameString = clearDoubleSpaces(nameString.trim());
        if(gegnerListe.getSelectedIndices().length == 1
                && !nameString.equals("")
                && farbenListe.getSelectedIndices().length == 1){
            name_spieler = nameString;
            gegner_art = gegnerListe.getSelectedValue();
            if(farbenListe.getSelectedValue().equals("Weiß")){
                farbeSelected = WHITE;
            }
            else{
                farbeSelected = BLACK;
            }

            pressedButton = true;
            aufforderung.setVisible(false);
            finish.setVisible(false);
            farbe.setVisible(false);
            farbenScrollPane.setVisible(false);
            gegner.setVisible(false);
            gegnerListe.setVisible(false);
            name.setVisible(false);
            labelName.setVisible(false);
            artenScrollPane.setVisible(false);
        }
    }


    public String getSpielerName(){
        return name_spieler;
    }
    public String getGegnerArt(){
        return gegner_art;
    }

    public String getFarbe(){
        return farbeSelected;
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
