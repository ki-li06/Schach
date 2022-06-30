package GUI;

import GUI.Teile.MainGameAnzeige;

import javax.swing.*;


/**
 * Alle Objekte, die für die GUI übergeben werden müssen, in einem Objekt gespeichert
 */
public class GUI_Package {
    public JFrame frame;
    public GroupLayout layout;
    public GUI_Package(JFrame frame, GroupLayout layout) {
        this.frame = frame;
        this.layout = layout;
    }


    public static void main(String[] args) {
        new MainGameAnzeige("s1", "s2");
    }
}
