package jpegencoder.streams;

import jpegencoder.encoding.Util;
import org.apache.commons.lang3.ArrayUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long Bui on 09.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class BitOutputStream extends OutputStream
{
    private static int BUFFER_CAPACITY = 1000000;

    private boolean allowFF = false;
    private OutputStream os;
    private int bitBuffer;
    private short counter;
    private ByteArrayOutputStream byteBuffer;

    public BitOutputStream(OutputStream os)
    {
        this.os = os;
        this.byteBuffer = new ByteArrayOutputStream(BUFFER_CAPACITY);
        this.bitBuffer = 0;
        this.counter = 0;
    }

    public void write(int b) throws IOException
    {
        if (b != 0 && b != 1)
        {
            throw new IllegalArgumentException();
        }
        bitBuffer = (byte) (bitBuffer + (b << (7 - counter)));
        counter++;
        if (counter == 8)
        {
            byteBuffer.write(bitBuffer);
            if ((bitBuffer & 0xFF) == 0xFF && !allowFF)
            {
                byteBuffer.write(0);
            }
            counter = 0;
            bitBuffer = 0;
        }

        if (byteBuffer.size() >= BUFFER_CAPACITY)
        {
            this.flush();
        }
    }

    public void close() throws IOException
    {
        this.flush();
        os.close();
    }

    public void flush() throws IOException
    {
        if (counter != 0)
        {
            byteBuffer.write(bitBuffer);
        }
        os.write(byteBuffer.toByteArray());
        byteBuffer = new ByteArrayOutputStream(BUFFER_CAPACITY);
        counter = 0;
        bitBuffer = 0;
    }

    public void writeByte(int byteToWrite) throws IOException
    {
        writeBits(byteToWrite, 8);
    }

    public void writeBits(int bits, int length) throws IOException
    {
        for (int i = length - 1; i >= 0; i--)
        {
            write((bits >> i) & 0x1);
        }
    }

    public void writeMarker(int marker) throws IOException
    {
        allowFF = true;
        writeBits(marker, 16);
        allowFF = false;
    }
}
