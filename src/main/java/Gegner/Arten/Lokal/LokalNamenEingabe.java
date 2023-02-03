package Gegner.Arten.Lokal;

import GUI.GUI_Package;
import Spiel.Spiel;
import Spiel.TeilvonSpiel.Spieler;
import util.ColPrint;

import javax.swing.*;

import java.util.Objects;

import static Spiel.TeilvonSpiel.Figur.WHITE;
import static Spiel.TeilvonSpiel.Figur.andereFarbe;
import static util.Delay.delay;
import static util.StringFormat.clearDoubleSpaces;

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

        System.out.println("Spieler Gegeben - " + gegeben.getGegner().getFarbe() + ": "+ gegeben.getGegner().getName());
        System.out.println("Spieler Eingabe - " + andereFarbe(gegeben.getGegner().getFarbe()) + ": " + eingabe);
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
                            .addContainerGap(15, Short.MAX_VALUE)
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
                                .addContainerGap(15, Short.MAX_VALUE)
                        )
        );

        frame.pack();
        frame.setLocation(0,5);

    }

    private void finish(){
        ColPrint.white.println("LokalNameEingabe: pressed button 'Bestätigen'");

        String string = eingabefeld.getText();
        string = clearDoubleSpaces(string.trim());

        if(!string.equals("") &&
                !string.equals(gegeben.getText())
            ){
            System.out.println("Zweiten Namen Bestätigt");

            eingabe = string;

            aufforderung.setVisible(false);
            weiß.setVisible(false);
            schwarz.setVisible(false);
            eingabefeld.setVisible(false);
            gegeben.setVisible(false);
            button.setVisible(false);

            pressedbutton = true;
        }
    }

    public String getEingabe(){
        return eingabe;
    }
}
