package util;

public class Delay {
    /**
     * wartete eine bestimmte Zeit
     * @param millis die Zeit in Millisekunden
     */
    public static void delay(long millis) {
        try{
            Thread.sleep(millis);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
