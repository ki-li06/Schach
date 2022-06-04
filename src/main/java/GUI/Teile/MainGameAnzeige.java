package GUI.Teile;

import GUI.Spielfeld;
import Spiel.TeilvonSpiel.Feld;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.image.BufferedImage;

import static GUI.Spielfeld.BREITE;

public class MainGameAnzeige {
    private static Color PlayerNames_BackgroundColour = Color.blue;
    private static int PlayerNames_Height = 50;
    private JFrame frame;
    private JPanel panel;
    private GroupLayout groupLayout;
    private JLabel wspieler;
    private JLabel sspieler;
    private JLabel brett;
    private int brettx;

    private String spieler_schwarz;
    private String spieler_weiß;
    private Spielfeld spielfeld;

    /**
     * der Teil der GUI, der das eigentliche Spiel anzeigt
     * @param weiß  Spielername weiß
     * @param schwarz Spielername schwarz
     * @param jFrame zugehöriger JFrame, übernehmen aus Eingabefeld
     * @param panel zugehöriges JPanel, übernehmen aus Eingabefeld
     * @param groupLayout zugehöriges GroupLayout, übernehmen aus Eingabefeld
     */
    public MainGameAnzeige (String weiß, String schwarz, JFrame jFrame, JPanel panel, GroupLayout groupLayout){
        spieler_schwarz = schwarz;
        spieler_weiß = weiß;
        frame = jFrame;
        this.panel = panel;
        this.groupLayout = groupLayout;

        int xposition = 350 - (weiß + schwarz).length() * 15;
        /*
        panel.setBorder(BorderFactory.createEmptyBorder(
                2, xposition, 2, 2
        ));
         */

        wspieler = new JLabel();
        wspieler.setText(weiß);
        wspieler.setFont(new Font("Dialog", Font.PLAIN, PlayerNames_Height));
        wspieler.setHorizontalAlignment(SwingConstants.CENTER);
        wspieler.setBackground(PlayerNames_BackgroundColour);
        wspieler.setForeground(Color.white);
        wspieler.setOpaque(true);

        sspieler = new JLabel();
        sspieler.setText(schwarz);
        sspieler.setFont(new Font("Dialog", Font.PLAIN, PlayerNames_Height));
        sspieler.setBackground(PlayerNames_BackgroundColour);
        sspieler.setForeground(Color.black);
        sspieler.setHorizontalAlignment(SwingConstants.CENTER);
        sspieler.setOpaque(true);


        brett = new JLabel();
        brett.setIcon(new ImageIcon(new Spielfeld().getFeld()));

        brettx = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2) - BREITE/2;

        erstellen();
    }

    /**
     * zeigt das Spielfeld erstmals für den Konstruktor an
     */
    private void erstellen(){
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(brettx)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(sspieler, BREITE, BREITE, BREITE)
                                        .addComponent(brett)
                                        .addComponent(wspieler, BREITE, BREITE, BREITE)
                                )
                        )
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(sspieler)
                        .addGap(10)
                        //.addComponent(wspieler, Spielfeld.BREITE, Spielfeld.BREITE, Spielfeld.BREITE)
                        .addComponent(brett)
                        .addGap(10)
                        //.addComponent(sspieler, Spielfeld.BREITE, Spielfeld.BREITE, Spielfeld.BREITE)
                        .addComponent(wspieler)
        );
        System.out.println("Graphik erstellt");
    }

    /**
     * zeigt das neue Spielfeld an
     * @param felder das Array an Felder (das Attribut aus Spiel)
     */
    public void updateFeld(Feld[][] felder){
        BufferedImage bi = Spielfeld.bild(felder);
        brett.setIcon(new ImageIcon(bi));
    }
    public Point BrettKoordinaten(){
        return new Point(brettx, 77);
    }

}
