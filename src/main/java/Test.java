import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Spiel.TeilvonSpiel.Feld;
import Spiel.TeilvonSpiel.Figur;
import Spiel.TeilvonSpiel.Figuren.*;

import static Spiel.TeilvonSpiel.Figur.MöglicheZüge;
import static Spiel.TeilvonSpiel.Figur.ausgeben;
import static util.FormatPoint.format;
import static util.StringFormat.getFormat;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Test {
    public static void main(String[] args) {
        Point referenz = new Point(1, 5);
        Figur[][] figuren = new Figur[8][8];
        figuren[1][2] = new König("w");
        figuren[referenz.x][referenz.y] = new Turm("w");
        figuren[1][7] = new Turm("b");
        ausgeben(figuren);
        List<Point> points = MöglicheZüge(figuren, null, referenz.x, referenz.y);
        System.out.println("points: " + format(points));

    }
}
