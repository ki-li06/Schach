import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Spiel.TeilvonSpiel.Feld;
import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Figuren.*;

import static util.StringFormat.getFormat;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Test {
    public static void main(String[] args) {
        String format = getFormat(30, true);

        Feld[][] felder = new Feld[3][3];
        System.out.println("Arrays: ");
        for (int i = 0; i < felder.length; i++) {
            for (int j = 0; j < felder[i].length; j++) {
                felder[i][j] = new Feld();
                Figur figur = switch ((int) (Math.pow(i, 2) + Math.pow(j, 2))) {
                    default -> null;
                    case 8 -> new Dame("w");
                    case 0 -> new Turm("b");
                    case 5 -> new König("w");
                };
                felder[i][j].setFigur(figur);
                System.out.printf(format, (felder[i][j].getFigur() + "    "));
            }
            System.out.println();
        }
        System.out.println("Figuren: ");
        Figur[][] figuren = Arrays.stream(felder).map(
                i->Arrays.stream(i).map(Feld::getFigur).toArray(Figur[]::new)
        ).toArray(Figur[][]::new);
        for (Figur[] figurs : figuren) {
            for (Figur figur : figurs) {
                System.out.printf(format, figur);
            }
            System.out.println();
        }
    }
}
