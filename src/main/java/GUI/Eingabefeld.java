package GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import static util.Delay.delay;

/**
 * Das Eingabefeld sorgt für die Eingabefläche der Spielernamen
 */
public class Eingabefeld {
    private JFrame frame;
    private JPanel panel;
    private GroupLayout groupLayout;
    private JLabel aufforderung;
    private JLabel weiß;
    private JTextField weißeingabe;
    private JLabel schwarz;
    private JTextField schwarzeingabe;
    private JButton button;
    private boolean pressedbutton;

    private String Spielername_weiß;
    private String Spielername_schwarz;

    public Eingabefeld(){
        erstellen();
        while(!pressedbutton){
            delay(1);
            //System.out.println(pressedbutton);
        }
    }

    /**
     * zeigt das Eingabefeld an
     */
    public void erstellen(){
        frame = new JFrame("Schach");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        aufforderung = new JLabel("Bitte Spielernamen eingeben!", SwingConstants.CENTER);

        weiß = new JLabel();
        weiß.setText("Weiß: ");
        schwarz = new JLabel();
        schwarz.setText("Schwarz: ");

        button = new JButton();
        button.setText("Finish");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                finish();
            }
        });
        pressedbutton = false;

        weißeingabe = new JTextField();
        schwarzeingabe = new JTextField();


        groupLayout = new GroupLayout(panel);
        panel.setLayout(groupLayout);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(weiß)
                                        .addComponent(schwarz))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(aufforderung)
                                        .addComponent(weißeingabe)
                                        .addComponent(schwarzeingabe, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                                        )
                                .addGap(18, 18, 18)
                                .addComponent(button)
                                .addGap(0, 60, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(aufforderung))
                                .addGap(20)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(weiß)
                                        .addComponent(weißeingabe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(schwarz)
                                        .addComponent(schwarzeingabe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        //.addComponent(button)
                                )
                                .addComponent(button)
                                .addContainerGap(15, Short.MAX_VALUE))
        );

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
        frame.pack();

        frame.setVisible(true);


    }

    /**
     * dieser Code wird ausgeführt, wenn der Button geklickt wird
     */
    private void finish (){
        Spielername_weiß = weißeingabe.getText();
        Spielername_schwarz = schwarzeingabe.getText();

        if(!Spielername_weiß.equals("") && !Spielername_schwarz.equals("") &&
                Spielername_weiß.length() < 10 && Spielername_schwarz.length() < 10 &&
                !Spielername_schwarz.equals(Spielername_weiß)){


            CleanAndFillFrame();

            pressedbutton = true;

        }
        else{

            System.out.println("FEHLER BEI DER EINGABE");

            System.out.println("Spielername_weiß: " + Spielername_weiß);
            System.out.println("Spielername_schwarz: " + Spielername_schwarz);


        }

    }

    /**
     * macht das Eingabefeld nicht mehr sichtbar
     */
    private void CleanAndFillFrame (){
        weiß.setVisible(false);
        weißeingabe.setVisible(false);
        schwarz.setVisible(false);
        schwarzeingabe.setVisible(false);
        button.setVisible(false);
        aufforderung.setVisible(false);


    }

    /**
     * gibt den Spielernamen von schwarz aus
     */
    public String getSpielername_schwarz() {
        return Spielername_schwarz;
    }

    /**
     * gibt den Spielernamen von weiß aus
     */
    public String getSpielername_weiß() {
        return Spielername_weiß;
    }

    /**
     * gibt den zugehörigen JFrame (also das Fenster an sich) aus -> siehe MainGameAnzeige Konstruktor
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * gibt das zugehörige GroupLayout (also eine Vorlage zur Formatierung) aus -> siehe MainGameAnzeige Konstruktor
     */
    public GroupLayout getGroupLayout() {
        return groupLayout;
    }

    /**
     * gibt das zugehörige JPanel aus (ein Teil des JFrames) aus -> siehe MainGameAnzeige Konstruktor
     */
    public JPanel getPanel() {
        return panel;
    }


}
