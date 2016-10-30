package Blatt1.colors.rgb;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */

// Ganze Klasse: Aufgabe 1a)
public class RGBPicture
{
    private ArrayList<ArrayList<RGB>> picture;
    private int width;
    private int height;
    private int strideWidth;
    private int strideHeight;

    private RGBPicture()
    {
        this.strideHeight = 16;
        this.strideWidth = 16;
    }

    // Konstruktor mit InputStream parsing: Aufgabe 1b)
    public RGBPicture(InputStream is)
    {
        this();
        long start = System.currentTimeMillis();
        Scanner sc = new Scanner(is);
        extractMetaInformation(sc);
        initPicture();
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
        System.out.println("Finished reading PPM in "
                                   + ((System.currentTimeMillis() - start) / 1000d)
                                   + " seconds");
    }

    // Aufgabe 1d)
    public RGBPicture(InputStream is, int factorRed, int factorGreen, int factorBlue)
    {
        this();
        long start = System.currentTimeMillis();
        Scanner sc = new Scanner(is);
        extractMetaInformation(sc);
        initPicture();
        int factorRedPower = (int) Math.pow(2, factorRed);
        int factorGreenPower = (int) Math.pow(2, factorGreen);
        int factorBluePower = (int) Math.pow(2, factorBlue);
        int red = 0;
        int green = 0;
        int blue = 0;
        int counterRed = 0;
        int counterGreen = 0;
        int counterBlue = 0;
        for (int i = 0; i < this.height; i++)
        {
            for (int j = 0; j < this.width; j++)
            {
                if (counterRed++ % factorRedPower == 0)
                {
                    red = Integer.parseInt(sc.next());
                }
                else
                {
                    sc.next();
                }
                if (counterGreen++ % factorGreenPower == 0)
                {
                    green = Integer.parseInt(sc.next());
                }
                else
                {
                    sc.next();
                }
                if (counterBlue++ % factorBluePower == 0)
                {
                    blue = Integer.parseInt(sc.next());
                }
                else
                {
                    sc.next();
                }
                picture.get(i).add(new RGB(red, green, blue));
            }
        }
        System.out.println("Finished reading PPM in "
                                   + ((System.currentTimeMillis() - start) / 1000d)
                                   + " seconds");
    }

    private void initPicture()
    {
        picture = new ArrayList<ArrayList<RGB>>(height);
        for (int i = 0; i < height; i++)
        {
            picture.add(new ArrayList<RGB>(width));
        }
    }

    private void extractMetaInformation(Scanner sc)
    {
        skipLine(sc);
        String metaInformation = sc.nextLine();
        String[] splitMeta = metaInformation.split(" ");
        this.width = Integer.parseInt(splitMeta[0]);
        this.height = Integer.parseInt(splitMeta[1]);
        skipLine(sc);
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

    public int getStrideHeight()
    {
        int result = this.getHeight();
        if (this.getHeight() % strideHeight != 0)
        {
            result = ((this.getHeight() / strideHeight) + 1) * strideHeight;
        }
        return result;
    }

    public int getStrideWidth()
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
                sb.append(pixel.toString()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
