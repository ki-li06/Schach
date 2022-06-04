package GUI;

import GUI.Teile.Eingabefeld;
import GUI.Teile.MainGameAnzeige;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static util.Delay.delay;

public class GUIMain {
    public static void main(String[] args) {
        Eingabefeld e = new Eingabefeld();
        System.out.println("Weiß: " + e.getSpielername_weiß());
        System.out.println("Schwarz: " + e.getSpielername_schwarz());
        MainGameAnzeige mga = new MainGameAnzeige(
                e.getSpielername_weiß(),
                e.getSpielername_schwarz(),
                e.getFrame(),
                e.getPanel(),
                e.getGroupLayout());
        //test
        System.out.println(mga.BrettKoordinaten());
        e.getPanel().addMouseListener(new MausListener());
        System.out.println("width:  " + Toolkit.getDefaultToolkit().getScreenSize().getWidth());
        System.out.println("height: " + Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        delay(5000L);

    }
}
