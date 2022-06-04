package GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MausListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse clicked");
        System.out.println("X: " + e.getX());
        System.out.println("y: " + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}