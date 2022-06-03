package util;

public class Random {
    private static java.util.Random r = new java.util.Random();

    /**
     *
     * @param min kleinste Zahl (inkl.)
     * @param max größte Zahl (exkl.)
     * @return eine zufällige natürliche Zahl zwischen min und max
     */
    public static int random(int min, int max){
        return r.nextInt(max - min + 1) + min;
    }
}
