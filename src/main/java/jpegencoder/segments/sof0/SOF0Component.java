package jpegencoder.segments.sof0;

import jpegencoder.streams.BitOutputStream;

import java.io.IOException;

/**
 * Created by Long Bui on 14.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class SOF0Component
{
    private int id;
    private int subSamplingFactorVertical;
    private int subSamplingFactorHorizontal;
    private int quantizationTableId;

    public SOF0Component(int id, int subSamplingFactorVertical, int subSamplingFactorHorizontal,
                         int quantizationTableId)
    {
        this.id = id;
        this.subSamplingFactorHorizontal = subSamplingFactorHorizontal;
        this.subSamplingFactorVertical = subSamplingFactorVertical;
        this.quantizationTableId = quantizationTableId;
    }

    public void writeToStream(BitOutputStream os) throws IOException
    {
        os.writeByte(id);
        os.writeBits(subSamplingFactorHorizontal, 4);
        os.writeBits(subSamplingFactorVertical, 4);
        os.writeByte(quantizationTableId);
    }
}
