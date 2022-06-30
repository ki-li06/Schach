package util;

import java.util.Arrays;

/**
 * Eine eigene Exception, um auf nicht initialisierte Methode hinzuweisen
 */
public class NonInitializedMethodException extends Exception{

    public NonInitializedMethodException(){
        super("Eine Methode ist an folgendem Ort nicht initialisiert");
    }

    /**
     * gibt eine Methode aus
     */
    public static void throwException(){
        NonInitializedMethodException nimex = new NonInitializedMethodException();
        nimex.setStackTrace(Arrays.copyOfRange(nimex.getStackTrace(), 1, nimex.getStackTrace().length));
        nimex.printStackTrace();
    }

}
