package jpegencoder.segments.soi;

import jpegencoder.segments.SegmentWriter;
import jpegencoder.streams.BitOutputStream;

import java.io.IOException;

/**
 * Created by Long Bui on 17.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class SOIWriter extends SegmentWriter
{
    private static final int SOI_MARKER = 0xD8;

    public SOIWriter(BitOutputStream os)
    {
        super(os);
    }

    public void writeSegment() throws IOException
    {
        os.write(0xFF);
        os.write(SOI_MARKER);
    }
}
