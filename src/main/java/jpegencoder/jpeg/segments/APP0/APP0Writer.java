package jpegencoder.jpeg.segments.APP0;

import jpegencoder.jpeg.segments.SegmentWriter;
import jpegencoder.jpeg.streams.BitOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long Bui on 14.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class APP0Writer extends SegmentWriter
{
    public static int APP0MARKER = 0xe0;
    public static int[] JFIF_STRING = {0x4A, 0x46, 0x49, 0x46, 0x00};

    private int length = 16;
    private int major = 1;
    private int minor = 1;
    private int pixelUnit = 0;
    private int xDensityLow;
    private int xDensityHigh;
    private int yDensityLow;
    private int yDensityHigh;
    private int xThumb = 0;
    private int yThumb = 0;
    private List<Byte> thumbnail = new ArrayList<Byte>();

    public APP0Writer(OutputStream os)
    {
        super(os);
    }

    public void setMajor(int major)
    {
        if (major > 0 && major <= 0xFF)
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
        if (minor >= 0 && minor <= 0xFF)
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
        if (xDensity > 0 && xDensity <= 0xFFFF)
        {
            this.xDensityHigh = (xDensity & 0xFF00) >> 8;
            this.xDensityLow = xDensity & 0xFF;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void setyDensity(int yDensity)
    {
        if (yDensity > 0 && yDensity <= 0xFFFF)
        {
            this.yDensityHigh = (yDensity & 0xFF00) >> 8;
            this.yDensityLow = yDensity & 0xFF;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void setThumbnail(int xThumb, int yThumb, List<Byte> thumbnail)
    {
        if (xThumb >= 0 && xThumb <= 0xFF && yThumb >= 0 && yThumb <= 0xFF)
        {
            this.yThumb = yThumb;
            this.xThumb = xThumb;
            this.length = 16 + (xThumb * yThumb * 3);
            this.thumbnail = thumbnail;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void writeSegment() throws IOException
    {
        BitOutputStream.writeByte(os, 0xFF);
        BitOutputStream.writeByte(os, APP0MARKER);
        BitOutputStream.writeByte(os, (length & 0xFF00) >> 8);
        BitOutputStream.writeByte(os, length & 0xFF);
        for (int i = 0; i < JFIF_STRING.length; i++)
        {
            BitOutputStream.writeByte(os, JFIF_STRING[i]);
        }
        BitOutputStream.writeByte(os, major);
        BitOutputStream.writeByte(os, minor);
        BitOutputStream.writeByte(os, pixelUnit);
        BitOutputStream.writeByte(os, xDensityHigh);
        BitOutputStream.writeByte(os, xDensityLow);
        BitOutputStream.writeByte(os, yDensityHigh);
        BitOutputStream.writeByte(os, yDensityLow);
        BitOutputStream.writeByte(os, xThumb);
        BitOutputStream.writeByte(os, yThumb);
        for (Byte thumbnailByte : thumbnail)
        {
            BitOutputStream.writeByte(os, thumbnailByte);
        }
    }
}
