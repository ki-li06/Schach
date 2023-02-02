package Gegner.Arten.Lokal;

import GUI.GUI_Package;
import Spiel.Spiel;
import Spiel.TeilvonSpiel.Spieler;

import javax.swing.*;

import static Spiel.TeilvonSpiel.Figur.WHITE;
import static util.Delay.delay;

public class LokalNamenEingabe {
    private JFrame frame;
    private GroupLayout layout;

    private JLabel aufforderung;
    private JLabel weiß;
    private JLabel schwarz;
    private JTextField eingabefeld;
    private JLabel gegeben;
    private JButton button;

    private boolean pressedbutton;
    private String eingabe;

    public LokalNamenEingabe (GUI_Package gui, Spieler gegeben){
        frame = gui.frame;
        layout = gui.layout;

        erstellen(gegeben);
        while (!pressedbutton) {
            delay(1);
        }
    }
    public void erstellen(Spieler gegebenSpieler){
        aufforderung = new JLabel("Bitte zweiten Spielernamen eingeben!");

        weiß = new JLabel();
        weiß.setText("Weiß: ");
        schwarz = new JLabel();
        schwarz.setText("Schwarz: ");

        button = new JButton();
        button.setText("Bestätigen");
        button.addActionListener(evt -> finish());
        pressedbutton = false;

        gegeben = new JLabel(gegebenSpieler.getGegner().getName());

        eingabefeld = new JTextField();

        GroupLayout.ParallelGroup pg_left =
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(weiß)
                        .addComponent(schwarz);
        GroupLayout.ParallelGroup pg_middle_horizontal =
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(aufforderung);
        if(gegebenSpieler.getGegner().getFarbe().equals(WHITE)){
            pg_middle_horizontal = pg_middle_horizontal
                    .addComponent(gegeben)
                    .addComponent(eingabefeld);
        }
        else{
            pg_middle_horizontal = pg_middle_horizontal
                    .addComponent(eingabefeld)
                    .addComponent(gegeben);
        }
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(pg_left)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pg_middle_horizontal)
                            .addGap(18, 18, 18)
                            .addComponent(button)
                            .addGap(0, 60, Short.MAX_VALUE)
                    )
        );

        GroupLayout.ParallelGroup pg_oben =
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(weiß);
        GroupLayout.ParallelGroup pg_unten =
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(schwarz);
        if(gegebenSpieler.getGegner().getFarbe().equals(WHITE)){
            pg_oben = pg_oben
                    .addComponent(gegeben);
            pg_unten = pg_unten
                    .addComponent(eingabefeld);
        }
        else{
            pg_oben = pg_oben
                    .addComponent(eingabefeld);
            pg_unten = pg_unten
                    .addComponent(gegeben);
        }
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(aufforderung))
                                .addGap(20)
                                .addGroup(pg_oben)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pg_unten)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button)
                                .addContainerGap(15, Short.MAX_VALUE))
        );

        frame.pack();
        frame.setLocation(0,5);


    }

    private void finish(){

    }

    public String getEingabe(){
        return null;
    }
}
