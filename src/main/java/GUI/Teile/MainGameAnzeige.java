package GUI.Teile;

import GUI.BauernAuswahl;
import GUI.GUI_Package;
import GUI.Spielfeld;
import Spiel.TeilvonSpiel.Ende;
import Spiel.TeilvonSpiel.Feld;
import util.ColPrint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import static GUI.Spielfeld.BREITE;

public class MainGameAnzeige {
    private final static Color PlayerNames_BackgroundColour = Color.blue;
    private final static int PlayerNames_Height = 40;
    private JFrame frame;
    private GroupLayout layout;
    private JLabel ende_oben;
    private JLabel ende_unten;
    private JLabel spielerunten;
    private JLabel spieleroben;
    private JLabel brett;
    private JLabel bauernAuswahl;
    private int brettx;
    private boolean gedreht;

    private String spieler_schwarz;
    private String spieler_weiß;

    /**
     * der Teil der GUI, der das eigentliche Spiel anzeigt
     *
     * @param weiß    Spielername weiß
     * @param schwarz Spielername schwarz
     * @param gui     Das GUI-Package, das übergeben werden muss
     */
    public MainGameAnzeige(String weiß, String schwarz, GUI_Package gui) {
        Konstruktor(weiß, schwarz, gui.frame, gui.layout);
    }

    /**
     * Standard Konstruktor (für einen direkten Spielstart ohne Eingabefeld)
     */
    public MainGameAnzeige(String weiß, String schwarz) {
        JFrame frame = new JFrame("Schach");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        GroupLayout groupLayout = new GroupLayout(panel);
        panel.setLayout(groupLayout);

        Konstruktor(weiß, schwarz, frame, groupLayout);

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
        //Vollbild
        //this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setVisible(true);

    }

    /**
     * siehe Konstruktor 1
     */
    private void Konstruktor(String weiß, String schwarz, JFrame jFrame, GroupLayout layout) {
        spieler_schwarz = schwarz;
        spieler_weiß = weiß;
        frame = jFrame;
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.layout = layout;

        ende_oben = new JLabel("text");
        ende_oben.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
        ende_oben.setHorizontalAlignment(SwingConstants.CENTER);
        ende_oben.setForeground(frame.getBackground());
        ende_oben.setVerticalAlignment(SwingConstants.BOTTOM);
        ende_oben.setVisible(true);

        ende_unten = new JLabel("endeunten");
        ende_unten.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        ende_unten.setHorizontalAlignment(SwingConstants.CENTER);
        ende_unten.setVerticalAlignment(SwingConstants.TOP);
        ende_unten.setVisible(false);

        spielerunten = new JLabel();
        spielerunten.setText("- " + weiß + " -");
        spielerunten.setFont(new Font(Font.DIALOG, Font.PLAIN, PlayerNames_Height));
        spielerunten.setHorizontalAlignment(SwingConstants.CENTER);
        spielerunten.setBackground(PlayerNames_BackgroundColour);
        spielerunten.setForeground(Color.white);
        spielerunten.setOpaque(true);

        spieleroben = new JLabel();
        spieleroben.setText(schwarz);
        spieleroben.setFont(new Font(Font.DIALOG, Font.PLAIN, PlayerNames_Height));
        spieleroben.setBackground(PlayerNames_BackgroundColour);
        spieleroben.setForeground(Color.black);
        spieleroben.setHorizontalAlignment(SwingConstants.CENTER);
        spieleroben.setOpaque(true);

        brett = new JLabel();
        brett.setIcon(new ImageIcon(new Spielfeld().getFeld()));

        bauernAuswahl = new JLabel();
        bauernAuswahl.setIcon(new ImageIcon(BauernAuswahl.getBildLeer()));
        bauernAuswahl.setVerticalAlignment(SwingConstants.CENTER);
        bauernAuswahl.setVisible(false);

        brettx = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - BREITE / 2;

        gedreht = false;

        erstellen();
    }


    /**
     * zeigt das Spielfeld erstmals für den Konstruktor an
     */
    private void erstellen() {
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                //.addGap(brettx)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(ende_oben, brettx, brettx, brettx)
                                        .addComponent(ende_unten, brettx, brettx, brettx)
                                )
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(spieleroben, BREITE, BREITE, BREITE)
                                        .addComponent(brett)
                                        .addComponent(spielerunten, BREITE, BREITE, BREITE)
                                )
                                .addGap(10)
                                .addComponent(bauernAuswahl)
                        )
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(spieleroben)
                        .addGap(10)
                        //.addComponent(spielerunten, Spielfeld.BREITE, Spielfeld.BREITE, Spielfeld.BREITE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(ende_oben, BREITE / 2, BREITE / 2, BREITE / 2)
                                        .addComponent(ende_unten, BREITE / 2, BREITE / 2, BREITE / 2)
                                )
                                .addComponent(brett)
                                .addComponent(bauernAuswahl, BREITE, BREITE, BREITE)
                        )
                        //.addComponent(brett)
                        .addGap(10)
                        //.addComponent(spieleroben, Spielfeld.BREITE, Spielfeld.BREITE, Spielfeld.BREITE)
                        .addComponent(spielerunten)
        );

        ColPrint.green.println("Graphik erstellt");
    }

    /**
     * zeigt das neue Spielfeld an
     *
     * @param felder das Array an Felder (das Attribut aus Spiel)
     */
    public void updateBrett(Feld[][] felder) {
        if (felder.length != 8 || felder[0].length != 8) {
            ColPrint.red.println("FEHLER - MainGameAnzeige");
            ColPrint.red.println("Falsche Array Länge: " + felder.length + " | " + felder[0].length);
            return;
        }
        BufferedImage bi = Spielfeld.bild(felder, gedreht);
        brett.setIcon(new ImageIcon(bi));
    }

    /**
     * Dreht das Brett und die Beschriftungen
     */
    public void dreheBrett(Feld[][] felder) {
        gedreht = !gedreht;
        if (gedreht) {
            spielerunten.setText("- " + spieler_schwarz + " -");
            spielerunten.setForeground(Color.black);
            spieleroben.setText(spieler_weiß);
            spieleroben.setForeground(Color.white);
        } else {
            spielerunten.setText("- " + spieler_weiß + " -");
            spielerunten.setForeground(Color.white);
            spieleroben.setText(spieler_schwarz);
            spieleroben.setForeground(Color.black);
        }
        updateBrett(felder);

    }


    /**
     * zeigt das Ende auf der linken Seite des Bretts an
     */
    public void setzeEnde(Ende ende) {
        ende_oben.setText(ende.Typ());
        ende_oben.setForeground(Color.red);
        ende_oben.setVisible(true);
        ende_unten.setText(ende.Text());
        ende_unten.setVisible(true);
    }

    /**
     * fügt einen MouseListener für das Spielbrett hinzu
     */
    public void fügeMouseListenerHinzu(MouseListener ml) {
        brett.addMouseListener(ml);
        ColPrint.green.println("MouseListener hinzugefügt");
    }

    /**
     * fügt einen MouseListener für die BauernAuswahl hinzu
     */
    public void fügeMouseListenerBauernAuswahlHinzu(MouseListener ml) {
        bauernAuswahl.addMouseListener(ml);
        ColPrint.green.println("BauernAuswahlMouseListener hinzugefügt");
    }

    public void zeigeBauernAuswahl(String farbe) {
        bauernAuswahl.setIcon(new ImageIcon(BauernAuswahl.getBild(farbe)));
        bauernAuswahl.setVisible(true);
        System.out.println("zeigeBauernAuswahl - Ende");
    }

    public void macheBauernAuswahlUnsichtbar() {
        bauernAuswahl.setVisible(false);
    }

    public boolean BauernAuswahlSichtbar() {

        return bauernAuswahl.isVisible();
    }

}
