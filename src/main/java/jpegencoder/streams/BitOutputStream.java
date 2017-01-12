package jpegencoder.streams;

import org.apache.commons.lang3.ArrayUtils;

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

    private OutputStream os;
    private byte bitBuffer;
    private short counter;
    private List<Byte> byteBuffer;

    public BitOutputStream(OutputStream os)
    {
        this.os = os;
        this.byteBuffer = new ArrayList<Byte>(BUFFER_CAPACITY);
        this.bitBuffer = 0;
        this.counter = 0;
    }

    public void write(int b) throws IOException
    {
        if (b != 0 && b != 1)
        {
            throw new IllegalArgumentException();
        }
        // bitweise rechts nach links
//        bitBuffer = (byte) ((bitBuffer << 1) + b);
        // bitweise links nach rechts
        bitBuffer = (byte) (bitBuffer + (b << (7 - counter)));
        counter++;
        if (counter == 8)
        {
            byteBuffer.add(bitBuffer);
            counter = 0;
            bitBuffer = 0;
        }
        if (byteBuffer.size() == BUFFER_CAPACITY)
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
            byteBuffer.add(bitBuffer);
        }
        // ByteArrayBuffer zu Array konvertieren
        Byte[] writeOut = byteBuffer.toArray(new Byte[byteBuffer.size()]);
        os.write(ArrayUtils.toPrimitive(writeOut));
        byteBuffer = new ArrayList<Byte>(BUFFER_CAPACITY);
        byteBuffer.clear();
        counter = 0;
        bitBuffer = 0;
    }

    // Hilfsmethode Aufgabe 2
    public void writeByte(int byteToWrite) throws IOException
    {
        for (int i = 0; i < 8; i++)
        {
            write((byteToWrite & 128) >> 7);
            byteToWrite = byteToWrite << 1;
        }
    }

    public void writeBits(int bits, int length) throws IOException
    {
        for (int i = length - 1; i >= 0; i--)
        {
            write((bits >> i) & 0x1);
        }
    }
}
