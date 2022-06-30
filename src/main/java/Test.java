import GUI.Spielfeld;
import util.NonInitializedMethodException;

import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static util.Listen.getLast;

public class Test {
    public static void main(String[] args){
        JFrame frame = new JFrame("myframe");

        JLabel label = new JLabel();
        label.setIcon(new ImageIcon((new Spielfeld()).getFeld()));
        label.addMouseListener(new ML1());
        label.addMouseListener(new ML2());

        frame.add(label);

        frame.pack();
        frame.setVisible(true);
    }

    public static long getTime(){
        return System.currentTimeMillis();
    }

    public static class ML1 implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("ML1: " + getTime());
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

    public static class ML2 implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("ML2: " + getTime());
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
}
