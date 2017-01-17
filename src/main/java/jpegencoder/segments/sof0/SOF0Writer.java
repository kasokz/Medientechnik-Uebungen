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
    private static final int SOFOMARKER = 0xc0;

    private int length;
    private int sampleRate = 8;
    private int yImgSizeHigh;
    private int yImgSizeLow;
    private int xImgSizeHigh;
    private int xImgSizeLow;
    private int numberOfComponents;
    private List<SOF0Component> components;

    public SOF0Writer(BitOutputStream os, int xImgSize, int yImgSize)
    {
        super(os);
        setComponents();
        this.numberOfComponents = components.size();
        this.length = 8 + numberOfComponents * 3;
        setXImgSize(xImgSize);
        setYImgSize(yImgSize);
    }

    private void setComponents()
    {
        components = new ArrayList<SOF0Component>();
        components.add(new SOF0Component(0, 2, 2, 0));
        components.add(new SOF0Component(1, 1, 1, 1));
        components.add(new SOF0Component(2, 1, 1, 1));
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
