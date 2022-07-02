import GUI.Spielfeld;
import util.NonInitializedMethodException;

import javax.swing.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static util.Listen.getLast;

public class Test {
    public static void main(String[] args){
        long dauer = 1000L;
        System.out.println("wait " + dauer + " milliseconds");
        System.out.println("aktuell: " + millis());
        delay(dauer);
        System.out.println("ende   : " + millis());
    }

    public static void delay (long millis){
        long davor = System.currentTimeMillis();
        while ((System.currentTimeMillis() - davor) < millis){

        }

    }

    public static long millis(){
        return System.currentTimeMillis();
    }
}
