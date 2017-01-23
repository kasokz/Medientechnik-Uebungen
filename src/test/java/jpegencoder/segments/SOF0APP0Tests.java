package jpegencoder.segments;

import jpegencoder.segments.app0.APP0Writer;
import jpegencoder.segments.sof0.SOF0Component;
import jpegencoder.segments.sof0.SOF0Writer;
import jpegencoder.streams.BitOutputStream;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long Bui on 17.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class SOF0APP0Tests
{
    @Test
    public void aufgabe2() throws IOException
    {
        BitOutputStream bos = new BitOutputStream(new FileOutputStream("testImage.jpg"));
        SOF0Writer sof0Writer = new SOF0Writer(bos, 800, 600, 1);
        APP0Writer app0Writer = new APP0Writer(bos, 300, 300);
        app0Writer.writeSegment();
        sof0Writer.writeSegment();
        bos.close();
        FileInputStream fis1 = new FileInputStream("expectedSegment.jpg");
        FileInputStream fis2 = new FileInputStream("testImage.jpg");
        int read1;
        int read2;
        while ((read1 = fis1.read()) != -1
                && (read2 = fis2.read()) != -1)
        {
            Assert.assertEquals(read1, read2);
        }
    }
}
