package jpegencoder.segments.sos;

import jpegencoder.streams.BitOutputStream;

import java.io.IOException;

/**
 * Created by Long Bui on 17.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class SOSComponent
{
    private int id;
    private int huffmanTableIdAC;
    private int huffmanTableIdDC;

    public SOSComponent(int id, int huffmanTableIdAC, int huffmanTableIdDC)
    {
        this.id = id;
        this.huffmanTableIdAC = huffmanTableIdAC;
        this.huffmanTableIdDC = huffmanTableIdDC;
    }

    public void writeComponent(BitOutputStream bos) throws IOException
    {
        bos.writeByte(id);
        bos.writeBits(huffmanTableIdAC, 4);
        bos.writeBits(huffmanTableIdDC, 4);
    }
}
