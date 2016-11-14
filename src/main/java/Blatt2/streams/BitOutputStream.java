package Blatt2.streams;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Long Bui on 09.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class BitOutputStream extends OutputStream
{
    private OutputStream os;
    private byte byteBuffer = 0;
    private short counter = 0;

    public BitOutputStream(OutputStream os)
    {
        this.os = os;
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
            os.write(byteBuffer);
            counter = 0;
            byteBuffer = 0;
        }
    }

    public void close() throws IOException
    {
        if (counter != 0)
        {
            os.write(byteBuffer);
        }
        os.close();
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
