package Blatt2;

import jpegencoder.jpeg.segments.APP0.APP0Writer;
import jpegencoder.jpeg.segments.SOF0.SOF0Component;
import jpegencoder.jpeg.segments.SOF0.SOF0Writer;
import jpegencoder.jpeg.streams.BitInputStream;
import jpegencoder.jpeg.streams.BitOutputStream;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Long Bui on 21.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */

public class Blatt2
{

    @Test
    public void aufgabe1() throws IOException
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

    @Test
    public void aufgabe2() throws IOException
    {
        BitOutputStream bos = new BitOutputStream(new FileOutputStream("testImage.jpg"));
        SOF0Writer sof0Writer = new SOF0Writer(bos);
        APP0Writer app0Writer = new APP0Writer(bos);
        initAPP0(app0Writer);
        initSOF0(sof0Writer);
        app0Writer.writeSegment();
        sof0Writer.writeSegment();
        bos.close();
        FileInputStream fis1 = new FileInputStream("expectedSegments.jpg");
        FileInputStream fis2 = new FileInputStream("testImage.jpg");
        int read1;
        int read2;
        while ((read1 = fis1.read()) != -1
                && (read2 = fis2.read()) != -1)
        {
            Assert.assertEquals(read1, read2);
        }
    }

    private void initSOF0(SOF0Writer writer)
    {
        writer.setXImgSize(800);
        writer.setYImgSize(600);
        writer.setComponents(1, new SOF0Component(1, 1, 1, 1));
    }

    private void initAPP0(APP0Writer writer)
    {
        writer.setMajor(1);
        writer.setMinor(1);
        writer.setXDensity(300);
        writer.setyDensity(300);
        writer.setThumbnail(0, 0, new ArrayList<Byte>());
    }
}
