package Blatt1;

import java.util.ArrayList;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class YCbCrPicture
{
    ArrayList<ArrayList<YCbCr>> picture;

    public YCbCrPicture(ArrayList<ArrayList<YCbCr>> picture)
    {
        this.picture = picture;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<YCbCr> line : picture)
        {
            for (YCbCr pixel : line)
            {
                sb.append(pixel.toString() + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
