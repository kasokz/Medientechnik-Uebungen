package Blatt1;

import Blatt1.colors.ColorChannels;
import Blatt1.colors.rgb.RGBPicture;
import Blatt1.colors.ycbcr.YCbCrPicture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class Test
{
    public static void main(String[] args)
    {
        try
        {
            RGBPicture testPicture = new RGBPicture(new FileInputStream(new File("test-pic.ppm")));
            YCbCrPicture yCbCrPicture = ColorChannels.RGBToYCbCr(testPicture);
            BufferedImage img = new BufferedImage(yCbCrPicture.getWidth(),
                                                  yCbCrPicture.getHeight(),
                                                  BufferedImage.TYPE_BYTE_GRAY);
            byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
            for (int i = 0; i < yCbCrPicture.getHeight(); i++)
            {
                for (int j = 0; j < yCbCrPicture.getWidth(); j++)
                {
                    pixels[j + i * yCbCrPicture.getWidth()] = (byte) yCbCrPicture.getPixelAt(j, i).getLuminanceChannel();
                }
            }
            File outputfile = new File("saved.png");
            ImageIO.write(img, "png", outputfile);

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
