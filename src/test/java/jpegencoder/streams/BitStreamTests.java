package jpegencoder.streams;

import jpegencoder.segments.app0.APP0Writer;
import jpegencoder.segments.sof0.SOF0Component;
import jpegencoder.segments.sof0.SOF0Writer;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long Bui on 21.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */

public class BitStreamTests
{
    @Test
    public void bitsReadWriteTest() throws IOException
    {
        BitOutputStream bos = new BitOutputStream(new FileOutputStream("bits.dat"));
        long overall = System.currentTimeMillis();
        for (int i = 1; i <= 10000000; i++)
        {
            bos.write(i % 2);
        }
        bos.close();
        System.out.println("Finished writing in " + (System.currentTimeMillis() - overall) / 1000d);
        long readStart = System.currentTimeMillis();
        BitInputStream bis = new BitInputStream(new FileInputStream("bits.dat"));
        int read;
        int counter = 0;
        while ((read = bis.read()) != -1)
        {
            if ((counter++ % 2) == 0)
            {
                Assert.assertEquals(1, read);
            }
            else
            {
                Assert.assertEquals(0, read);
            }
        }
        Assert.assertEquals(10000000, counter);
        System.out.println("Finished reading in " + (System.currentTimeMillis() - readStart) / 1000d);
        System.out.println("Finished both in " + (System.currentTimeMillis() - overall) / 1000d);
    }
}
