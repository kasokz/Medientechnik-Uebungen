package jpegencoder.segments.eoi;

import jpegencoder.segments.SegmentWriter;
import jpegencoder.streams.BitOutputStream;

import java.io.IOException;

/**
 * Created by Long Bui on 17.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class EOIWriter extends SegmentWriter
{
    private static final int EOI_MARKER = 0xFFD9;

    public EOIWriter(BitOutputStream os)
    {
        super(os);
    }

    public void writeSegment() throws IOException
    {
        os.writeMarker(EOI_MARKER);
    }
}
