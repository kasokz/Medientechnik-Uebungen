package Blatt2.jpeg;

import java.io.OutputStream;

/**
 * Created by Long Bui on 14.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class SOF0Writer extends SegmentWriter
{
    public SOF0Writer(OutputStream os)
    {
        super(os);
    }

    public void writeMarker()
    {

    }
}
