package OnlineCode;

import GUI.Spielfeld;
import org.apache.bcel.generic.IF_ACMPEQ;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.Buffer;
import java.nio.file.Paths;

public class SVGtoPNG {
    public static String[] farben = new String[]{"b", "w"};
    public static String[] namen = new String[]{"B", "K", "N", "Q", "P", "R"};
    public static void main(String[] args) {
        for (String f : farben) {
            for (String n : namen) {
                String path = "Figuren\\" + f + n + ".png";
                System.out.println(path);
                File file = new File(path);
                BufferedImage bi = null;
                try{
                    bi = ImageIO.read(file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                System.out.println("height: " + bi.getHeight());
                System.out.println("width: " + bi.getWidth());
                double factor = (double) 65/45;
                bi = scale(bi, factor);
                try{
                    ImageIO.write(bi, "png", file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

    }
    public static BufferedImage scale (BufferedImage eingabe, double factor){
        BufferedImage before = new BufferedImage(eingabe.getWidth(), eingabe.getHeight(), eingabe.getType());
        Graphics2D g2 = before.createGraphics();
        g2.drawImage(eingabe, 0, 0, null);
        int w = (int) (before.getWidth() * factor);
        int h = (int) (before.getHeight() * factor);
        BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(factor, factor);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return scaleOp.filter(before, after);
    }
}
