package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;

public class MainGameAnzeige {
    private JFrame frame;
    private JPanel panel;
    private GroupLayout groupLayout;
    private JLabel wspieler;
    private JLabel sspieler;
    private JLabel feld;

    private String spieler_schwarz;
    private String spieler_weiß;
    private Spielfeld spielfeld;

    public MainGameAnzeige (String weiß, String schwarz, JFrame jFrame, JPanel panel, GroupLayout groupLayout){
        spieler_schwarz = schwarz;
        spieler_weiß = weiß;
        frame = jFrame;
        this.panel = panel;
        this.groupLayout = groupLayout;

        int xposition = 350 - (weiß + schwarz).length() * 15;
        panel.setBorder(BorderFactory.createEmptyBorder(
                2, xposition, 2, 2
        ));

        wspieler = new JLabel();
        wspieler.setText(weiß);
        wspieler.setFont(new Font("Dialog", Font.PLAIN, 50));
        wspieler.setBackground(Color.black);
        wspieler.setForeground(Color.white);

        sspieler = new JLabel();
        sspieler.setText(schwarz);
        sspieler.setFont(new Font("Dialog", Font.PLAIN, 50));
        sspieler.setBackground(Color.white);
        sspieler.setForeground(Color.black);


        feld = new JLabel();
        feld.setIcon(new ImageIcon(new Spielfeld().getFeld()));

        erstellen();
    }
    public void erstellen(){
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(wspieler, Spielfeld.BREITE, Spielfeld.BREITE, Spielfeld.BREITE)
                        .addComponent(feld)
                        .addComponent(sspieler, Spielfeld.BREITE, Spielfeld.BREITE, Spielfeld.BREITE)
        );
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(wspieler)
                                .addGap(20)
                                .addComponent(feld)
                                .addGap(20)
                                .addComponent(sspieler)
                        )
        );


        System.out.println("Graphik erstellt");
    }

    public String SpielerText (){
        String spieler1 = spieler_weiß;
        String spieler2 = spieler_schwarz;
        if(spieler1.length() < spieler2.length()){
            spieler1 += " ".repeat(spieler2.length() - spieler1.length());
        }
        else if(spieler2.length() < spieler1.length()){
            spieler2 += " ".repeat(spieler1.length() - spieler2.length());
        }
        return spieler1 + " - " + spieler2;
    }
}
