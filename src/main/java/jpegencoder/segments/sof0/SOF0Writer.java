package jpegencoder.segments.sof0;

import jpegencoder.image.Image;
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
    private static final int SOFOMARKER = 0xFFC0;

    private int length;
    private int sampleRate = 8;
    private int yImgSize;
    private int xImgSize;
    private int subsampling;
    private int numberOfComponents;
    private List<SOF0Component> components;

    public SOF0Writer(BitOutputStream os, int xImgSize, int yImgSize, int subsampling)
    {
        super(os);
        this.xImgSize = xImgSize;
        this.yImgSize = yImgSize;
        this.subsampling = subsampling;
        setComponents();
        this.numberOfComponents = components.size();
        this.length = 8 + numberOfComponents * 3;
        this.xImgSize = xImgSize;
        this.yImgSize = yImgSize;
        this.subsampling = subsampling;
    }

    private void setComponents()
    {
        components = new ArrayList<SOF0Component>();
        components.add(new SOF0Component(0, subsampling, subsampling, 0));
        components.add(new SOF0Component(1, 1, 1, 1));
        components.add(new SOF0Component(2, 1, 1, 1));
    }

    public void writeSegment() throws IOException
    {
        os.writeMarker(SOFOMARKER);
        os.writeBits(length, 16);
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
