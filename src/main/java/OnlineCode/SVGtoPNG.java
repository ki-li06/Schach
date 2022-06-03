package OnlineCode;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

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

        BufferedImage bi = new BufferedImage(800, 800, ColorSpace.TYPE_RGB);
        Graphics2D g2 = bi.createGraphics();
        g2.setColor(new Color(119, 78, 19));
        g2.fillRect(0, 0, bi.getWidth(), bi.getHeight());

        g2.setColor(Color.white);
        g2.fillRect(100, 100, 100, 100);

        BufferedImage dame = null;
        try{
            File f = new File("Figuren\\bQ.png");
            dame = ImageIO.read(f);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        g2.drawImage(dame, 100, 100, null);

        try
        {
            ImageIO.write(bi, "png", new File("bild.png"));
        }
        catch(IOException ioe)
        {
            System.out.println("write: " + ioe.getMessage());
        }
        System.out.println("done");

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
