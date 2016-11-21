package Blatt2.jpeg;

import Blatt2.streams.BitOutputStream;

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
    public static APP0WriterBuilder newBuilder(OutputStream os, int xDensity, int yDensity)
    {
        return new APP0WriterBuilder(os, xDensity, yDensity);
    }

    public static int APP0MARKER = 0xe0;
    public static int[] JFIF_STRING = {74, 70, 73, 70, 0};

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

    public static class APP0WriterBuilder
    {
        private int xDensityLow;
        private int xDensityHigh;
        private int yDensityLow;
        private int yDensityHigh;
        private OutputStream os;

        private int length = 16;
        private int major = 1;
        private int minor = 1;
        private int pixelUnit = 0;
        private int xThumb = 0;
        private int yThumb = 0;
        private List<Byte> thumbnail = new ArrayList<Byte>();

        private APP0WriterBuilder(OutputStream os, int xDensity, int yDensity)
        {
            this.os = os;
            this.setXDensity(xDensity);
            this.setYDensity(yDensity);
        }

        public APP0WriterBuilder withMajor(int major)
        {
            if (major <= 255 && major >= 1)
            {
                this.major = major;
            }
            else
            {
                throw new IllegalArgumentException();
            }
            return this;
        }

        public APP0WriterBuilder withMinor(int minor)
        {
            if (minor <= 255 && minor >= 0)
            {
                this.minor = minor;
            }
            else
            {
                throw new IllegalArgumentException();
            }
            return this;
        }

        public APP0WriterBuilder withPixelUnit(int pixelUnit)
        {
            if (pixelUnit >= 0 && pixelUnit <= 2)
            {
                this.pixelUnit = pixelUnit;
            }
            else
            {
                throw new IllegalArgumentException();
            }
            return this;
        }

        public APP0WriterBuilder withXDensity(int xDensity)
        {
            this.setXDensity(xDensity);
            return this;
        }

        public APP0WriterBuilder withYDensity(int yDensity)
        {
            this.setYDensity(yDensity);
            return this;
        }

        public APP0WriterBuilder withThumbnail(int xThumb, int yThumb, List<Byte> thumbnail)
        {
            if (xThumb >= 0 && xThumb <= 255 && yThumb >= 0 && yThumb <= 255)
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
            return this;
        }

        private void setXDensity(int xDensity)
        {
            if (xDensity > 0 && xDensity <= 0xFFFF)
            {
                this.xDensityHigh = (xDensity & 0xFF00) >> 8;
                this.xDensityLow = xDensity & 0x00FF;
            }
            else
            {
                throw new IllegalArgumentException();
            }
        }

        private void setYDensity(int yDensity)
        {
            if (yDensity > 0 && yDensity <= 0xFFFF)
            {
                this.yDensityHigh = (yDensity & 0xFF00) >> 8;
                this.yDensityLow = yDensity & 0x00FF;
            }
            else
            {
                throw new IllegalArgumentException();
            }
        }

        public APP0Writer build()
        {
            return new APP0Writer(this);
        }
    }

    private APP0Writer(OutputStream os)
    {
        super(os);
    }

    private APP0Writer(APP0WriterBuilder builder)
    {
        this(builder.os);
        this.length = builder.length;
        this.major = builder.major;
        this.minor = builder.minor;
        this.xDensityHigh = builder.xDensityHigh;
        this.xDensityLow = builder.xDensityLow;
        this.yDensityHigh = builder.yDensityHigh;
        this.yDensityLow = builder.yDensityLow;
        this.pixelUnit = builder.pixelUnit;
        this.xThumb = builder.xThumb;
        this.yThumb = builder.yThumb;
        this.thumbnail = builder.thumbnail;
    }

    public void writeSegment() throws IOException
    {
        BitOutputStream.writeByte(os, 255);
        BitOutputStream.writeByte(os, APP0MARKER);
        BitOutputStream.writeByte(os, (length & 0xFF00) >> 8);
        BitOutputStream.writeByte(os, length & 0x00FF);
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
