package jpegencoder.segments.sos;

import jpegencoder.segments.SegmentWriter;
import jpegencoder.streams.BitOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long Bui on 17.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class SOSWriter extends SegmentWriter
{
    private static final int SOS_MARKER = 0xDA;

    private int length;
    private List<SOSComponent> components;
    private int numOfComponents;
    private int startOfSpectralSelection = 0;
    private int endOfSpectralSelection = 63;
    private int successiveApproximationHigh = 0;
    private int successiveApproximationLow = 0;

    public SOSWriter(BitOutputStream os)
    {
        super(os);
        setComponents();
        this.numOfComponents = components.size();
        this.length = 6 + (2 * components.size());
    }

    private void setComponents()
    {
        components = new ArrayList<SOSComponent>();
        components.add(new SOSComponent(0, 0, 0));
        components.add(new SOSComponent(1, 1, 1));
        components.add(new SOSComponent(2, 1, 1));
    }

    public void writeSegment() throws IOException
    {
        os.writeByte(0xFF);
        os.writeByte(SOS_MARKER);
        os.writeBits(length, 16);
        os.writeByte(numOfComponents);
        for (SOSComponent component : components)
        {
            component.writeComponent(os);
        }
        os.writeByte(startOfSpectralSelection);
        os.writeByte(endOfSpectralSelection);
        os.writeBits(successiveApproximationHigh, 4);
        os.writeBits(successiveApproximationLow, 4);
    }
}
