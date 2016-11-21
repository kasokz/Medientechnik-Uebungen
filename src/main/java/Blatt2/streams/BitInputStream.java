package Blatt2.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Long Bui on 09.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class BitInputStream extends InputStream
{
    private InputStream is;
    private int byteBuffer;
    private short counter = 0;

    public BitInputStream(InputStream is)
    {
        this.is = is;
    }

    public int read() throws IOException
    {
        if ((counter % 8) == 0)// counter = 8 or 0
        {
            counter = 0;
            this.byteBuffer = is.read();// read byte
            if (this.byteBuffer == -1)
            {
                return -1;
            }
        }
        int result = (this.byteBuffer & 128) >> 7; // 11110000 * 1000000 = 10000000 -> 1
        this.byteBuffer = byteBuffer << 1; // shift bytebuffer left -> 11100000
        counter++;
        return result;
    }
}
