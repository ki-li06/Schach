package GUI;

import GUI.Spielfeld;
import Spiel.TeilvonSpiel.Feld;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import static GUI.Spielfeld.BREITE;

public class MainGameAnzeige {
    private static Color PlayerNames_BackgroundColour = Color.blue;
    private static int PlayerNames_Height = 40;
    private JFrame frame;
    private JPanel panel;
    private GroupLayout groupLayout;
    private JLabel spielerunten;
    private JLabel spieleroben;
    private JLabel brett;
    private int brettx;
    private boolean gedreht;

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
        Konstruktor(weiß, schwarz, jFrame, panel, groupLayout);
    }

    /**
     * Standard Konstruktor (für einen direkten Spielstart ohne Eingabefeld)
     */
    public MainGameAnzeige (String weiß, String schwarz){
        JFrame frame = new JFrame("Schach");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        GroupLayout groupLayout = new GroupLayout(panel);
        panel.setLayout(groupLayout);

        Konstruktor(weiß, schwarz, frame, panel, groupLayout);

        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        this.frame.pack();
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setVisible(true);
    }

    /**
     * siehe Konstruktor 1
     */
    private void Konstruktor(String weiß, String schwarz, JFrame jFrame, JPanel panel, GroupLayout groupLayout){
        spieler_schwarz = schwarz;
        spieler_weiß = weiß;
        frame = jFrame;
        this.panel = panel;
        this.groupLayout = groupLayout;

        spielerunten = new JLabel();
        spielerunten.setText("- " + weiß + " -");
        spielerunten.setFont(new Font("Dialog", Font.PLAIN, PlayerNames_Height));
        spielerunten.setHorizontalAlignment(SwingConstants.CENTER);
        spielerunten.setBackground(PlayerNames_BackgroundColour);
        spielerunten.setForeground(Color.white);
        spielerunten.setOpaque(true);

        spieleroben = new JLabel();
        spieleroben.setText(schwarz);
        spieleroben.setFont(new Font("Dialog", Font.PLAIN, PlayerNames_Height));
        spieleroben.setBackground(PlayerNames_BackgroundColour);
        spieleroben.setForeground(Color.black);
        spieleroben.setHorizontalAlignment(SwingConstants.CENTER);
        spieleroben.setOpaque(true);


        brett = new JLabel();
        brett.setIcon(new ImageIcon(new Spielfeld().getFeld()));

        brettx = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2) - BREITE/2;

        gedreht = false;

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
                                        .addComponent(spieleroben, BREITE, BREITE, BREITE)
                                        .addComponent(brett)
                                        .addComponent(spielerunten, BREITE, BREITE, BREITE)
                                )
                        )
        );
        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                        .addComponent(spieleroben)
                        .addGap(10)
                        //.addComponent(spielerunten, Spielfeld.BREITE, Spielfeld.BREITE, Spielfeld.BREITE)
                        .addComponent(brett)
                        .addGap(10)
                        //.addComponent(spieleroben, Spielfeld.BREITE, Spielfeld.BREITE, Spielfeld.BREITE)
                        .addComponent(spielerunten)
        );
        System.out.println("Graphik erstellt");
    }

    /**
     * zeigt das neue Spielfeld an
     * @param felder das Array an Felder (das Attribut aus Spiel)
     */
    public void updateBrett(Feld[][] felder){
        BufferedImage bi = Spielfeld.bild(felder, gedreht);
        brett.setIcon(new ImageIcon(bi));
    }

    /**
     * Dreht das Brett und die Beschriftungen
     */
    public void dreheBrett(Feld[][] felder){
        gedreht = !gedreht;
        if(gedreht){
            spielerunten.setText("- " + spieler_schwarz + " -");
            spielerunten.setForeground(Color.black);
            spieleroben.setText(spieler_weiß);
            spieleroben.setForeground(Color.white);
        }
        else{
            spielerunten.setText("- " + spieler_weiß + " -");
            spielerunten.setForeground(Color.white);
            spieleroben.setText(spieler_schwarz);
            spieleroben.setForeground(Color.black);
        }
        updateBrett(felder);

    }

    /**
     * Beschriftet den Sieger und den Verlierer
     * @param winner der Sieger
     */
    public void setzeSieger(String winner){
        String winnertext = "Gewonnen: ";
        String losertext = "Verloren: ";
        
        if(spieleroben.getText().equals(winner)){
            String textoben = spieleroben.getText();
            spieleroben.setText(winnertext + textoben);
            String textunten = spielerunten.getText();
            spielerunten.setText(losertext + textunten);
        }
        else if(spielerunten.getText().replace("- ", "").replace(" -", "")
                .equals(winner)){
            String textoben = spieleroben.getText();
            spieleroben.setText(losertext + textoben);
            String textunten = spielerunten.getText();
            spielerunten.setText(winnertext + textunten);
        }
        else{
            System.out.println("FEHLER - setSieger");
            System.out.println("winner: " + winner);
            System.out.println("oben: " + spieleroben.getText());
        }
    }

    public void fügeMouseListenerhinzu(MouseListener ml){
        brett.addMouseListener(ml);
        System.out.println("MouseListener hinzugefügt");
    }
    /**
     * gibt den obersten linken Punkt des Spielbretts aus
     * @return ein Point (x, y)
     */
    public Point BrettKoordinaten(){
        return new Point(brettx, 77);
    }

}
