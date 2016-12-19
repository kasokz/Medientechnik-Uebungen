package jpegencoder.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Long Bui on 09.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class BitInputStream extends InputStream
{
    private InputStream is;
    private int bitBuffer;
    private short counter = 0;

    public BitInputStream(InputStream is)
    {
        this.is = is;
    }

    public int read() throws IOException
    {
        if ((counter % 8) == 0)
        {
            counter = 0;
            this.bitBuffer = is.read();
            // End of File check
            if (this.bitBuffer == -1)
            {
                return -1;
            }
        }
        int result = (this.bitBuffer & 128) >> 7;
        this.bitBuffer = bitBuffer << 1;
        counter++;
        return result;
    }
}
