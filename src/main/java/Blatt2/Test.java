package Blatt2;

import Blatt2.streams.BitInputStream;
import Blatt2.streams.BitOutputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
            BitOutputStream bos = new BitOutputStream(new FileOutputStream("bytes.dat"));
            long overall = System.currentTimeMillis();
            for (int i = 0; i < 10000000; i++)
            {
                bos.write((int) (Math.random() * 2));
            }
            bos.close();
            System.out.println("Finished writing in " + (System.currentTimeMillis() - overall) / 1000d);
            long readStart = System.currentTimeMillis();
            BitInputStream bis = new BitInputStream(new FileInputStream("bytes.dat"));
            int read;
            while ((read = bis.read()) != -1)
            {
//                System.out.print(read);
            }
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
}
