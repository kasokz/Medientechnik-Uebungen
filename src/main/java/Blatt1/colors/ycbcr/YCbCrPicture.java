package Blatt1.colors.ycbcr;

import java.util.ArrayList;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class YCbCrPicture
{
    private ArrayList<ArrayList<YCbCr>> picture;

    public YCbCrPicture(ArrayList<ArrayList<YCbCr>> picture)
    {
        this.picture = picture;
    }

    public int getHeight()
    {
        return picture.size();
    }

    public int getWidth()
    {
        return picture.get(0).size();
    }

    public YCbCr getPixelAt(int x, int y)
    {
        return picture.get(y).get(x);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<YCbCr> line : picture)
        {
            for (YCbCr pixel : line)
            {
                sb.append(pixel.toString())
                  .append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
