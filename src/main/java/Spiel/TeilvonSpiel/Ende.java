package Spiel.TeilvonSpiel;

public abstract class Ende {
    public String Typ(){
        return this.getClass().getSimpleName();
    }

    public abstract String Text();

    /**
     * gibt einen Sieger aus, falls einen gibt - als int
     * dabei steht
     *  ~ 1 für Sieg
     *  ~ 0 für Unentschieden
     *  ~ -1 für Niederlage
     */
    public abstract int Punkte(String farbe);


    @Override
    public String toString() {
        return "Ende{" + this.getClass().getSimpleName() + ": " + Text().substring(0, 13) + "}";
    }

    public static class Matt extends Ende{
        private final Spieler sieger;
        public Matt(Spieler sieger){
             this.sieger = sieger;
        }

        @Override
        public String Typ() {
            return "Schach Matt";
        }

        public String Text() {
            return sieger.getGegner().getName() + " hat gewonnen!";
        }
        public int Punkte(String farbe){
            if(farbe.equals(sieger.getGegner().getFarbe())){
                return 1;
            }
            return -1;
        }
    }
    public static class Patt extends Ende{
        private final String imPatt;
        /**
         * @param betroffen Wer steht im Patt?
         */
        public Patt(Spieler betroffen){
            imPatt = betroffen.getGegner().getName();
        }
        public String Text() {
            return imPatt + " steht im Patt";
        }
        public int Punkte(String farbe){
            return 0;
        }
    }
    public static class Remis extends Ende{
        @Override
        public String Text() {
            return "Unentschieden";
        }
        public int Punkte(String farbe){
            return 0;
        }
    }
}
