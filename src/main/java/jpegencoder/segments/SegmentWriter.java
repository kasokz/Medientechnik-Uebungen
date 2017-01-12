package jpegencoder.segments;

import jpegencoder.streams.BitOutputStream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Long Bui on 09.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */

// Für JPEG-Pipeline später
public abstract class SegmentWriter
{
    protected BitOutputStream os;

    protected SegmentWriter(BitOutputStream os)
    {
        this.os = os;
    }

    public abstract void writeSegment() throws IOException;
}
