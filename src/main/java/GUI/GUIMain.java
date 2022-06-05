package GUI;

import Spiel.Spiel;
import Spiel.TeilvonSpiel.Feld;
import Spiel.TeilvonSpiel.Figuren.*;

import static util.Delay.delay;

public class GUIMain {
    public static void main(String[] args) {
        /*
        Eingabefeld e = new Eingabefeld();
        System.out.println("Weiß: " + e.getSpielername_weiß());
        System.out.println("Schwarz: " + e.getSpielername_schwarz());
        */
        MainGameAnzeige mga;
        /*mga = new MainGameAnzeige(
                e.getSpielername_weiß(),
                e.getSpielername_schwarz(),
                e.getFrame(),
                e.getPanel(),
                e.getGroupLayout());
         */
        mga = new MainGameAnzeige("s1", "s2");



        //test
        delay(1000L);
        Feld[][] felder = new Feld[8][8];
        for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
                felder[i][j] = new Feld();
            }
        }

        felder[0][0].setFigur(new Dame("w"), 0 ,0);
        felder[0][1].setFigur(new Turm("b"),0 ,1 );
        felder[0][2].setFigur(new Turm("w"), 0, 2);
        felder[1][0].setFigur(new Dame("b"), 1,0);
        felder[2][0].setFigur(new Dame("w"), 2, 0);
        felder[2][1].setFigur(new Springer("b"), 2, 1);
        felder[2][2].setFigur(new Läufer("b"), 2, 2);
        felder[3][0].setFigur(new Dame("w"), 3, 0);
        felder[4][0].setFigur(new Dame("w"), 4 , 0);
        felder[4][1].setFigur(new Bauer("b"),4 ,1);
        felder[4][2].setFigur(new König("b"),4,2 );

        felder[1][1].setStatus(Feld.Status.MÖGLICH_SCHLAGEN());
        felder[3][2].setStatus(Feld.Status.SCHACH());
        felder[4][4].setStatus(Feld.Status.ZUG());

        //e.getPanel().addMouseListener(new MausListener());
        mga.fügeMouseListenerhinzu(new MausListener(new Spiel()));
        mga.updateBrett(felder);

        System.out.println("ENDE GUI-MAIN");
        while (true){

        }
    }
}
