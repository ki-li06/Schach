package util;

import java.awt.Point;
import java.util.List;

public class FormatPoint {
    public static String format(Point i){
        if(i == null){
            return "null";
        }
        return "{x: " + i.x + "; y: " + i.y + "}";
    }
    public static String format(List<Point> list){
        return list.stream().map(FormatPoint::format).toList().toString();
    }
}
