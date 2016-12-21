package Blatt4.dct;

import org.jblas.DoubleMatrix;
import org.jblas.ranges.IntervalRange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long Bui on 19.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class PerformanceCheck
{
    private DoubleMatrix picture = new DoubleMatrix(256, 256);

    public PerformanceCheck()
    {
        fillPicture();
    }

    private void fillPicture()
    {
        for (int y = 0; y < picture.getRows(); y++)
        {
            for (int x = 0; x < picture.getColumns(); x++)
            {
                int value;
                value = (x + (y * 8)) % 256;
                picture.put(y, x, value);
            }
        }
    }

    public DoubleMatrix getPicture()
    {
        return picture;
    }

    public DoubleMatrix getBlock(int blockNum)
    {
        DoubleMatrix matrix = new DoubleMatrix(8, 8);
        for (int y = (blockNum / 32) * 8; y < ((blockNum / 32) * 8) + 8; y++)
        {
            for (int x = (blockNum * 8) % 256; x < (((blockNum * 8) % 256) + 8); x++)
            {
                matrix.put(y % 8, x % 8, picture.get(y, x));
            }
        }
        return matrix;
    }

    public void print()
    {
        for (int row = 0; row < picture.getRows(); row++)
        {
            for (int col = 0; col < picture.getColumns(); col++)
            {
                System.out.print((int) picture.get(row, col) + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws InterruptedException
    {
        PerformanceCheck performanceCheck = new PerformanceCheck();
        Thread[] threads = new Thread[4];
        List<DoubleMatrix> blocks = new ArrayList<DoubleMatrix>(1024);
        for (int i = 0; i < 1024; i++)
        {
            blocks.add(performanceCheck.getBlock(i));
        }
        long start = System.currentTimeMillis();
        long end = start + 10000;
        int count = 0;
        while (System.currentTimeMillis() < end)
        {
            threads[0] = new Thread(new DirectDCTTask(blocks.subList(0, 255)));
            threads[0].start();
            threads[1] = new Thread(new DirectDCTTask(blocks.subList(256, 511)));
            threads[1].start();
            threads[2] = new Thread(new DirectDCTTask(blocks.subList(512, 767)));
            threads[2].start();
            threads[3] = new Thread(new DirectDCTTask(blocks.subList(768, 1023)));
            threads[3].start();
            for (Thread thread : threads)
            {
                thread.join();
            }
            count++;
        }
        System.out.println("Direct DCT takes " + 10000d / count + " ms/image");
        start = System.currentTimeMillis();
        end = start + 10000;
        count = 0;
        while (System.currentTimeMillis() < end)
        {
            threads[0] = new Thread(new SeparatedDCTTask(blocks.subList(0, 255)));
            threads[0].start();
            threads[1] = new Thread(new SeparatedDCTTask(blocks.subList(256, 511)));
            threads[1].start();
            threads[2] = new Thread(new SeparatedDCTTask(blocks.subList(512, 767)));
            threads[2].start();
            threads[3] = new Thread(new SeparatedDCTTask(blocks.subList(768, 1023)));
            threads[3].start();
            for (Thread thread : threads)
            {
                thread.join();
            }
            count++;
        }
        System.out.println("Separated DCT takes " + 10000d / count + " ms/image");
        start = System.currentTimeMillis();
        end = start + 10000;
        count = 0;
        while (System.currentTimeMillis() < end)
        {
            threads[0] = new Thread(new AraiTask(blocks.subList(0, 255)));
            threads[0].start();
            threads[1] = new Thread(new AraiTask(blocks.subList(256, 511)));
            threads[1].start();
            threads[2] = new Thread(new AraiTask(blocks.subList(512, 767)));
            threads[2].start();
            threads[3] = new Thread(new AraiTask(blocks.subList(768, 1023)));
            threads[3].start();
            for (Thread thread : threads)
            {
                thread.join();
            }
            count++;
        }
        System.out.println("Arai takes " + 10000d / count + " ms/image");
    }
}
