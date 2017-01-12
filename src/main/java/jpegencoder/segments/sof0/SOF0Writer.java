package jpegencoder.segments.sof0;

import jpegencoder.segments.SegmentWriter;
import jpegencoder.streams.BitOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long Bui on 14.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class SOF0Writer extends SegmentWriter
{
    public static int SOFOMARKER = 0xc0;

    private int length;
    private int sampleRate = 8;
    private int yImgSizeHigh;
    private int yImgSizeLow;
    private int xImgSizeHigh;
    private int xImgSizeLow;
    private int numberOfComponents;
    private List<SOF0Component> components;

    public SOF0Writer(BitOutputStream os)
    {
        super(os);
    }

    public void setSampleRate(int sampleRate)
    {
        if (sampleRate == 8 || sampleRate == 12 || sampleRate == 16)
        {
            this.sampleRate = sampleRate;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void setYImgSize(int yImgSize)
    {
        if (yImgSize > 0 && yImgSize <= 0xFFFF)
        {
            this.yImgSizeHigh = (yImgSize & 0xFF00) >> 8;
            this.yImgSizeLow = yImgSize & 0xFF;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void setXImgSize(int xImgSize)
    {
        if (xImgSize > 0 && xImgSize <= 0xFFFF)
        {
            this.xImgSizeHigh = (xImgSize & 0xFF00) >> 8;
            this.xImgSizeLow = xImgSize & 0xFF;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void setComponents(int numberOfComponents, SOF0Component... components)
    {
        if (numberOfComponents == 1 || numberOfComponents == 3)
        {
            this.numberOfComponents = numberOfComponents;
            this.length = 8 + numberOfComponents * 3;
            this.components = new ArrayList<SOF0Component>(numberOfComponents);
            for (int i = 0; i < numberOfComponents; i++)
            {
                this.components.add(components[i]);
            }
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    public void writeSegment() throws IOException
    {
        os.writeByte(0xFF);
        os.writeByte(SOFOMARKER);
        os.writeByte((length & 0xFF00) >> 8);
        os.writeByte(length & 0xFF);
        os.writeByte(sampleRate);
        os.writeByte(yImgSizeHigh);
        os.writeByte(yImgSizeLow);
        os.writeByte(xImgSizeHigh);
        os.writeByte(xImgSizeLow);
        os.writeByte(numberOfComponents);
        for (SOF0Component sof0Component : components)
        {
            sof0Component.writeToStream(os);
        }
    }
}
