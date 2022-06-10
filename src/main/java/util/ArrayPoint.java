package util;

import java.awt.*;

public class ArrayPoint {
    public static <R> R get (R[][] array, Point index){
        return array[index.x][index.y];
    }
    public static <R> Point indexOf (R[][] array, R object){
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[x].length; y++) {
                Point p = new Point(x, y);
                if(get(array, p).equals(object)){
                    return p;
                }
            }
        }
        return null;
    }
}
