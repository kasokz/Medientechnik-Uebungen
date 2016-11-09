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
    private ArrayList<RGB> picture;
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
        this(is, 0, 0, 0);
    }

    public RGBPicture(InputStream is, int factorRed, int factorGreen, int factorBlue)
    {
        this();
        long start = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try
        {
            extractMetaInformation(br);
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
            String currentLine;
            while (br.ready())
            {
                currentLine = br.readLine().replaceAll(" +", " ");
                String[] splitLine = currentLine.split(" ");
                if(splitLine.length % 3 != 0)
                {
                    continue;
                }
                for (int i = 0; i < splitLine.length; i = i + 3)
                {
                    if (counterRed++ % factorRedPower == 0)
                    {
                        red = Short.parseShort(splitLine[i]);
                    }
                    if (counterGreen++ % factorGreenPower == 0)
                    {
                        green = Short.parseShort(splitLine[i + 1]);
                    }
                    if (counterBlue++ % factorBluePower == 0)
                    {
                        blue = Short.parseShort(splitLine[i + 2]);
                    }
                    picture.add(new RGB(red, green, blue));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Finished reading PPM in "
                                   + ((System.currentTimeMillis() - start) / 1000d)
                                   + " seconds");
    }

    private void initPicture()
    {
        picture = new ArrayList<RGB>(height * width);
    }

    private void extractMetaInformation(BufferedReader br) throws IOException
    {
        skipLine(br);
        String metaInformation = br.readLine();
        String[] splitMeta = metaInformation.split(" ");
        this.width = Integer.parseInt(splitMeta[0]);
        this.height = Integer.parseInt(splitMeta[1]);
        skipLine(br);
    }

    private void skipLine(BufferedReader br) throws IOException
    {
        if (br.ready())
        {
            br.readLine();
        }
    }

    public RGB getRGBAt(int x, int y)
    {
        RGB result;
        if (x > this.getStrideWidth() || y > this.getStrideHeight())
        {
            throw new IllegalArgumentException();
        }
        if (x > this.getWidth() && y > this.getHeight())
        {
            result = picture.get((width - 1) + (height - 1) * width);
        }
        else if (x > this.getWidth())
        {
            result = picture.get((width - 1) + y * width);
        }
        else if (y > this.getHeight())
        {
            result = picture.get(x + (height - 1) * width);
        }
        else
        {
            result = picture.get(x + y * width);
        }
        return result;
    }

    public int getHeight()
    {
        return this.height;
    }

    public int getWidth()
    {
        return this.width;
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
        long pixelCount = 0;
        for (RGB rgb : picture)
        {
            sb.append(rgb.toString()).append(" ");
            if ((pixelCount + 1) % width == 0)
            {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
