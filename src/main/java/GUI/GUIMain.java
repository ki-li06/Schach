package GUI;

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

    }
}
