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
    private int yImgSize;
    private int xImgSize;
    private int numberOfComponents;
    private List<SOF0Component> components;

    public SOF0Writer(BitOutputStream os, int xImgSize, int yImgSize)
    {
        super(os);
        setComponents();
        this.numberOfComponents = components.size();
        this.length = 8 + numberOfComponents * 3;
        this.xImgSize = xImgSize;
        this.yImgSize = yImgSize;
    }

    private void setComponents()
    {
        components = new ArrayList<SOF0Component>();
        components.add(new SOF0Component(0, 2, 2, 0));
        components.add(new SOF0Component(1, 1, 1, 1));
        components.add(new SOF0Component(2, 1, 1, 1));
    }

    public void writeSegment() throws IOException
    {
        os.writeByte(0xFF);
        os.writeByte(SOFOMARKER);
        os.writeByte((length & 0xFF00) >> 8);
        os.writeByte(length & 0xFF);
        os.writeByte(sampleRate);
        os.writeBits(yImgSize, 16);
        os.writeBits(xImgSize, 16);
        os.writeByte(numberOfComponents);
        for (SOF0Component sof0Component : components)
        {
            sof0Component.writeToStream(os);
        }
    }
}
