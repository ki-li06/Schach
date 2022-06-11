package Spiel.TeilvonSpiel;

public abstract class Ende {
    public String Typ(){
        return this.getClass().getSimpleName();
    }
    public String Text(){
        return null;
    }

    @Override
    public String toString() {
        return "Ende{" + Typ() + ": " + Text().substring(0, 13) + "}";
    }

    public static class Matt extends Ende{
        private final String sieger;
        public Matt(String sieger){
             this.sieger = sieger;
        }
        @Override
        public String Text() {
            return sieger + " hat gewonnen!";
        }
    }
    public static class Patt extends Ende{
        private String imPatt;
        /**
         * @param betroffen Wer steht im Patt?
         */
        public Patt(String betroffen){
            imPatt = betroffen;
        }
        @Override
        public String Text() {
            return imPatt + " steht im Patt";
        }
    }
    public static class Remis extends Ende{
        @Override
        public String Text() {
            return "Unentschieden";
        }
    }
}
