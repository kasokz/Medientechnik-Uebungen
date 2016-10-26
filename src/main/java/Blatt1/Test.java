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
            RGBPicture testPicture = new RGBPicture(new FileInputStream(new File("test2.ppm")));
            System.out.println(ColorChannels.RGBToYCbCr(testPicture).toString());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
