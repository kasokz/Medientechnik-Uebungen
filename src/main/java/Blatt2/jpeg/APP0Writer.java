package Blatt2.jpeg;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Long Bui on 14.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class APP0Writer extends SegmentWriter
{
    public static int APP0MARKER = 224;

    private int length = 16;
    private int major = 1;
    private int minor = 1;
    private int pixelSize = 0;
    private int xDensity;
    private int yDensity;
    private int xPreview = 0;
    private int yPreview = 0;


    public APP0Writer(OutputStream os)
    {
        super(os);
    }

    public void setMajor(int major)
    {
        if (major <= 255 && major >= 1)
        {
            this.major = major;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void setMinor(int minor)
    {
        if (minor <= 255 && minor >= 0)
        {
            this.minor = minor;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void setXDensity(int xDensity)
    {
        if (xDensity > 0 && xDensity < 65536)
        {
            this.xDensity = xDensity;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void setyDensity(int yDensity)
    {
        if (yDensity > 0 && yDensity < 65536)
        {
            this.yDensity = yDensity;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void setPreview(int xPreview, int yPreview)
    {
        if (xPreview >= 0 && xPreview <= 255 && yPreview >= 0 && yPreview <= 255)
        {
            this.yPreview = yPreview;
            this.xPreview = xPreview;
            this.length = 16 + (xPreview * yPreview * 3);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void writeSegment() throws IOException
    {
        
    }
}
