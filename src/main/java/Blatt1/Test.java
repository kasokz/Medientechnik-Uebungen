package Blatt1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
            System.out.println(testPicture.toString());
            System.out.println(testPicture.getWidth());
            System.out.println(testPicture.getHeight());
            System.out.println(testPicture.getHeightSpan());
            System.out.println(testPicture.getWidthSpan());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
