package util;

public class StringFormat {
    /**
     * erstellt ein sogenanntes StringFormat für Methoden wie String.format(...) oder System.out.printf(...)
     * @param stellen
     * @param linksgerichtet
     * @return
     */
    public static String getFormat(int stellen, boolean linksgerichtet){
        String ausgabe = "%";
        if(linksgerichtet){
            ausgabe += "-";
        }
        ausgabe += stellen;
        ausgabe += ".";
        ausgabe += stellen;
        ausgabe += "s";
        return ausgabe;
    }
}
