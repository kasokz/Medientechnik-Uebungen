package Blatt2;

import Blatt2.jpeg.APP0Writer;
import Blatt2.jpeg.SOF0Component;
import Blatt2.jpeg.SOF0Writer;
import Blatt2.streams.BitInputStream;
import Blatt2.streams.BitOutputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Long Bui on 09.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class Test
{
    public static void main(String[] args)
    {
        try
        {
            BitOutputStream bos = new BitOutputStream(new FileOutputStream("testImage.jpg"));
            long overall = System.currentTimeMillis();
            APP0Writer app0Writer = APP0Writer.newBuilder(bos, 300, 300)
                                              .withMajor(1)
                                              .withMinor(1)
                                              .withThumbnail(0, 0, new ArrayList<Byte>())
                                              .build();
            app0Writer.writeSegment();
            SOF0Writer sof0Writer = new SOF0Writer(bos);
            initSOF0(sof0Writer);
//            sof0Writer.writeSegment();
            bos.close();
            System.out.println("Finished writing in " + (System.currentTimeMillis() - overall) / 1000d);
            long readStart = System.currentTimeMillis();
            BitInputStream bis = new BitInputStream(new FileInputStream("testImage.jpg"));
            int read;
            int counter = 0;
            while ((read = bis.read()) != -1)
            {
                if (counter++ % 8 == 0)
                {
                    System.out.print(" ");
                }
                System.out.print(read);
            }
            System.out.println();
            System.out.println("Finished reading in " + (System.currentTimeMillis() - readStart) / 1000d);
            System.out.println("Finished both in " + (System.currentTimeMillis() - overall) / 1000d);

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void initSOF0(SOF0Writer writer)
    {
        writer.setXImgSize(800);
        writer.setYImgSize(600);
        writer.setComponents(1, new SOF0Component(1, 1, 1, 1));
    }
}
