package jpegencoder.encoding.dct;

import jpegencoder.image.colors.ColorChannel;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long Bui on 19.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class PerformanceCheck
{
    private ColorChannel picture = new ColorChannel(256, 256);

    public PerformanceCheck()
    {
        fillPicture();
    }

    private void fillPicture()
    {
        for (int y = 0; y < picture.getHeight(); y++)
        {
            for (int x = 0; x < picture.getWidth(); x++)
            {
                int value;
                value = (x + (y * 8)) % 256;
                picture.setPixel(y, x, value);
            }
        }
    }

    public List<DoubleMatrix> getBlocksAsList(int start, int end)
    {
        return picture.getBlocks(start, end);
    }

    public int getNumOfBlocks()
    {
        return picture.getNumOfBlocks();
    }

    public void print()
    {
        for (int row = 0; row < picture.getHeight(); row++)
        {
            for (int col = 0; col < picture.getWidth(); col++)
            {
                System.out.print(picture.getPixel(row, col) + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        PerformanceCheck performanceCheck = new PerformanceCheck();
        Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors()];
        int numOfBlocks = performanceCheck.getNumOfBlocks();
        int count = 0;
        System.out.println("Starting Direct DCT Benchmark");
        long start = System.currentTimeMillis();
        long end = start + 10000;
        while (System.currentTimeMillis() < end)
        {
            for (int i = 0; i < threads.length; i++)
            {
                threads[i] = new Thread(
                        new DirectDCTTask(performanceCheck.getBlocksAsList(i * numOfBlocks / threads.length,
                                                                           (i + 1) * (numOfBlocks / threads.length - 1))));
                threads[i].setPriority(Thread.MAX_PRIORITY);
                threads[i].start();
            }
            for (Thread thread : threads)
            {
                thread.join();
            }
            count++;
        }
        System.out.println("Direct DCT takes " + 10000d / count + " ms/image");
        System.out.println("Managed " + count + " images");
        count = 0;
        System.out.println("Starting Separated DCT Benchmark");
        start = System.currentTimeMillis();
        end = start + 10000;
        while (System.currentTimeMillis() < end)
        {
            for (int i = 0; i < threads.length; i++)
            {
                threads[i] = new Thread(
                        new SeparatedDCTTask(performanceCheck.getBlocksAsList(i * numOfBlocks / threads.length,
                                                                              (i + 1) * (numOfBlocks / threads.length - 1))));
                threads[i].setPriority(Thread.MAX_PRIORITY);
                threads[i].start();
            }
            for (Thread thread : threads)
            {
                thread.join();
            }
            count++;
        }
        System.out.println("Separated DCT takes " + 10000d / count + " ms/image");
        System.out.println("Managed " + count + " images");
        count = 0;
        System.out.println("Starting Arai Benchmark");
        start = System.currentTimeMillis();
        end = start + 10000;
        while (System.currentTimeMillis() < end)
        {
            for (int i = 0; i < threads.length; i++)
            {
                threads[i] = new Thread(
                        new AraiTask(performanceCheck.getBlocksAsList(i * numOfBlocks / threads.length,
                                                                      (i + 1) * (numOfBlocks / threads.length - 1))));
                threads[i].setPriority(Thread.MAX_PRIORITY);
                threads[i].start();
            }
            for (Thread thread : threads)
            {
                thread.join();
            }
            count++;
        }
        System.out.println("Arai takes " + 10000d / count + " ms/image");
        System.out.println("Managed " + count + " images");

    }
}
