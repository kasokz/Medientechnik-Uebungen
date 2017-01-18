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
            JpegEncoder.withImageFromFile("test-image.ppm").convertToJpeg().writeImageToDisk();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
