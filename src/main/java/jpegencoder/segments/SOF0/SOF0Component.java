package jpegencoder.segments.SOF0;

import jpegencoder.streams.BitOutputStream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Long Bui on 14.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class SOF0Component
{
    private int id;
    private int subSamplingFactorVertical;
    private int subSamplingFactorHorizontal;
    private int quantNum;

    public SOF0Component(int id, int subSamplingFactorVertical, int subSamplingFactorHorizontal, int quantNum)
    {
        this.id = id;
        this.subSamplingFactorHorizontal = subSamplingFactorHorizontal;
        this.subSamplingFactorVertical = subSamplingFactorVertical;
        this.quantNum = quantNum;
    }

    public void writeToStream(OutputStream os) throws IOException
    {
        BitOutputStream.writeByte(os, id);
        int subsamplingFactor = (subSamplingFactorHorizontal << 4) + subSamplingFactorVertical;
        BitOutputStream.writeByte(os, subsamplingFactor);
        BitOutputStream.writeByte(os, quantNum);
    }
}
