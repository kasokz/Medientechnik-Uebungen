package Blatt2.streams;

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
    private byte byteBuffer;
    private short counter;
    private List<Byte> byteArrayBuffer;

    public BitOutputStream(OutputStream os)
    {
        this.os = os;
        this.byteArrayBuffer = new ArrayList<Byte>(BUFFER_CAPACITY);
        this.byteBuffer = 0;
        this.counter = 0;
    }

    public void write(int b) throws IOException
    {
        if (b != 0 && b != 1)
        {
            throw new IllegalArgumentException();
        }
        byteBuffer = (byte) ((byteBuffer << 1) + b);
        counter++;
        if (counter == 8)
        {
            byteArrayBuffer.add(byteBuffer);
            counter = 0;
            byteBuffer = 0;
        }
        if (byteArrayBuffer.size() == BUFFER_CAPACITY)
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
            byteArrayBuffer.add(byteBuffer);
        }
        Byte[] writeOut = byteArrayBuffer.toArray(new Byte[byteArrayBuffer.size()]);
        os.write(ArrayUtils.toPrimitive(writeOut));
        byteArrayBuffer = new ArrayList<Byte>(BUFFER_CAPACITY);
        byteArrayBuffer.clear();
        counter = 0;
        byteBuffer = 0;
    }

    public static void writeByte(OutputStream os, int byteToWrite) throws IOException
    {
        for (int i = 0; i < 8; i++)
        {
            os.write((byteToWrite & 128) >> 7);
            byteToWrite = byteToWrite << 1;
        }
    }
}
