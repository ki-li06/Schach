package util;

import java.awt.*;

public class ArrayPoint {
    public static <R> R get (R[][] array, Point index){
        return array[index.x][index.y];
    }
}
