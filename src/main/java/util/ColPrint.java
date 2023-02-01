package util;

/**
 * ColorPrint:
 * ermöglicht es, Texte in verschiedenen Farben auszugeben
 */
public abstract class ColPrint {
    private static final String RESET = "\u001B[0m";
    protected String farbe(){
        return null;
    }
    public void print(String s){
        System.out.print(farbe() + s + RESET);
    }
    public void println(String s){
        System.out.println(farbe() + s + RESET);
    }
    public void printf(String format, String s){
        System.out.printf(format, farbe() + s + RESET);
    }

    public static ColPrint black = new BLACK();
    private static class BLACK extends ColPrint{
        @Override
        protected String farbe() {
            return "\u001B[30m";
        }
    }

    public static ColPrint blue = new BLUE();
    private static class BLUE extends ColPrint{
        @Override
        protected String farbe() {
            return "\u001B[34m";
        }
    }

    public static ColPrint cyan = new CYAN();
    private static class CYAN extends ColPrint{
        @Override
        protected String farbe() {
            return "\u001B[36m";
        }
    }

    public static ColPrint green = new GREEN();
    private static class GREEN extends ColPrint{
        @Override
        protected String farbe() {
            return "\u001B[32m";
        }
    }

    public static ColPrint purple = new PURPLE();
    private static class PURPLE extends ColPrint{
        @Override
        protected String farbe() {
            return "\u001B[35m";
        }
    }

    public static ColPrint red = new RED();
    private static class RED extends ColPrint{
        @Override
        protected String farbe() {
            return "\u001B[31m";
        }
    }

    public static ColPrint white = new WHITE();
    private static class WHITE extends ColPrint{
        @Override
        protected String farbe() {
            return "\u001B[37m";
        }
    }

    public static ColPrint yellow = new YELLOW();
    private static class YELLOW extends ColPrint{
        @Override
        protected String farbe() {
            return "\u001B[33m";
        }
    }
}
