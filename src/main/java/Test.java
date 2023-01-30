
public class Test {
    public static final String FEN =
            "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
    private static final String PATH = "stockfish";


    public static void main(String[] args) {
        String a = " a b ";
        String b = a.replace(" ", "");
        System.out.println("a: '" + a + "'");
        System.out.println("b: '" + b + "'");
    }


    public static void delay(long millis) {
        long davor = System.currentTimeMillis();
        while ((System.currentTimeMillis() - davor) < millis) {

        }

    }

    public static long millis() {
        return System.currentTimeMillis();
    }
}
