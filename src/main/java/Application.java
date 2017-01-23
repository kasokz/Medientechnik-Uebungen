import jpegencoder.JpegEncoder;

import java.io.FileNotFoundException;

/**
 * Created by Long Bui on 18/01/2017.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class Application
{
    public static void main(String[] args)
    {
        try
        {
            JpegEncoder.withImageFromFile("/Users/longbui/Downloads/series/76.ppm")
                       .convertToJpeg(1)
                       .writeImageToDisk();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
