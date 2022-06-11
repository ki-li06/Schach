package util;

import java.util.List;
import java.util.stream.IntStream;

public class Listen {
    /**
     * gibt das letzte Objekt einer Liste aus
     */
    public static <R> R getLast (List<R> liste){
        if(liste == null || liste.size() == 0){
            return null;
        }
        return liste.get(liste.size() - 1);
    }

    /**
     * gibt die letzten x Objekte einer Liste aus
     */
    public static <R> List<R> getLast (List<R> liste, int x){
        if(liste == null || liste.size() == 0 || x <= 0){
            return null;
        }
        if(x > liste.size()){
            x = liste.size();
        }
        return IntStream.range(0, x).boxed().map(i -> liste.get(liste.size() - 1 - i)).toList();
    }
}
