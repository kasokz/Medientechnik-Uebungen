package Blatt1;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class RGBPicture
{
    ArrayList<ArrayList<RGB>> picture;

    public RGBPicture(InputStream is)
    {

    }

    public RGB getRGBAt(int x, int y)
    {
        return picture.get(x).get(y);
    }

    public int getHeight()
    {
        return picture.size();
    }

    public int getWidth()
    {
        return picture.get(0).size();
    }

}
