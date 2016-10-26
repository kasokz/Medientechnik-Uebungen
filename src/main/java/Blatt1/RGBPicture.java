package Blatt1;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */

// Ganze Klasse: Aufgabe 1a)
public class RGBPicture
{
    ArrayList<ArrayList<RGB>> picture;
    int strideWidth;
    int strideHeight;

    public RGBPicture()
    {
        this.strideHeight = 16;
        this.strideWidth = 16;
    }

    // Konstruktor mit InputStream parsing: Aufgabe 1b)
    public RGBPicture(InputStream is)
    {
        this();
        Scanner sc = new Scanner(is);
        skipLine(sc);
        skipLine(sc);
        String metaInformation = sc.nextLine();
        int width = Integer.parseInt(metaInformation.split(" ")[0]);
        int height = Integer.parseInt(metaInformation.split(" ")[1]);
        skipLine(sc);
        picture = new ArrayList<ArrayList<RGB>>(height);
        for (int i = 0; i < height; i++)
        {
            picture.add(new ArrayList<RGB>(width));
        }
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                int r = Integer.parseInt(sc.next());
                int g = Integer.parseInt(sc.next());
                int b = Integer.parseInt(sc.next());
                picture.get(i).add(new RGB(r, g, b));
            }
        }
    }

    private void skipLine(Scanner sc)
    {
        if (sc.hasNextLine())
        {
            sc.nextLine();
        }
    }

    public RGB getRGBAt(int x, int y)
    {
        RGB result;
        if (x > this.getWidth() && y > this.getHeight())
        {
            result = picture.get(this.getHeight() - 1).get(this.getWidth() - 1);
        }
        else if (x > this.getWidth())
        {
            result = picture.get(y).get(this.getWidth() - 1);
        }
        else if (y > this.getHeight())
        {
            result = picture.get(this.getHeight() - 1).get(x);
        }
        else
        {
            result = picture.get(y).get(x);
        }
        return result;
    }

    public int getHeight()
    {
        return picture.size();
    }

    public int getWidth()
    {
        return picture.get(0).size();
    }

    public void setStrideWidth(int stride)
    {
        this.strideWidth = stride;
    }

    public void setStrideHeight(int stride)
    {
        this.strideHeight = stride;
    }

    public int getHeightSpan()
    {
        int result = this.getHeight();
        if (this.getHeight() % strideHeight != 0)
        {
            result = ((this.getHeight() / strideHeight) + 1) * strideHeight;
        }
        return result;
    }

    public int getWidthSpan()
    {
        int result = this.getWidth();
        if (this.getWidth() % strideWidth != 0)
        {
            result = ((this.getWidth() / strideWidth) + 1) * strideWidth;
        }
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (ArrayList<RGB> line : picture)
        {
            for (RGB pixel : line)
            {
                sb.append(pixel.toString() + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
