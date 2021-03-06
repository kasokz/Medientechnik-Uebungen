package jpegencoder.segments;

import jpegencoder.segments.dqt.DQTWriter;
import jpegencoder.segments.dqt.QuantizationTable;
import jpegencoder.streams.BitOutputStream;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long Bui on 12.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class DQTTests
{
    @Test
    public void testDqtWriter() throws IOException
    {
        BitOutputStream bos = new BitOutputStream(new FileOutputStream("dqt.jpg"));
        DQTWriter dqtWriter = new DQTWriter(bos);
        dqtWriter.writeSegment();
        bos.close();
    }
}
