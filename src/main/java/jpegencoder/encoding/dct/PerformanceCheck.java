package jpegencoder.encoding.dct;

import jpegencoder.image.colors.ColorChannel;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

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
                value = (x + (y * 8)) % picture.getHeight();
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

    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
        PerformanceCheck performanceCheck = new PerformanceCheck();
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(numberOfThreads);
        Set<Future<Integer>> set = new HashSet<Future<Integer>>();
        int count = 0;
        System.out.println("Starting Direct DCT Benchmark");
        long start = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; i++)
        {
            Callable<Integer> callable = new FullImageDirectTask(performanceCheck.picture);
            Future<Integer> future = pool.submit(callable);
            set.add(future);
        }
        for (Future<Integer> future : set)
        {
            count += future.get();
        }
        long finishedAfter = System.currentTimeMillis() - start;
        System.out.println("Finished after " + finishedAfter / 1000d + "seconds");
        System.out.println("Direct DCT takes " + (double) finishedAfter / count + " ms/image");
        System.out.println("Managed " + count + " images");
        pool = Executors.newFixedThreadPool(numberOfThreads);
        set = new HashSet<Future<Integer>>();
        count = 0;
        System.out.println("Starting Separated DCT Benchmark");
        start = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; i++)
        {
            Callable<Integer> callable = new FullImageSeparatedTask(performanceCheck.picture);
            Future<Integer> future = pool.submit(callable);
            set.add(future);
        }
        for (Future<Integer> future : set)
        {
            count += future.get();
        }
        finishedAfter = System.currentTimeMillis() - start;
        System.out.println("Finished after " + finishedAfter / 1000d + "seconds");
        System.out.println("Separated DCT takes " + (double) finishedAfter / count + " ms/image");
        System.out.println("Managed " + count + " images");
        pool = Executors.newFixedThreadPool(numberOfThreads);
        set = new HashSet<Future<Integer>>();
        count = 0;
        System.out.println("Starting Arai Benchmark");
        start = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; i++)
        {
            Callable<Integer> callable = new FullImageAraiTask(performanceCheck.picture);
            Future<Integer> future = pool.submit(callable);
            set.add(future);
        }
        for (Future<Integer> future : set)
        {
            count += future.get();
        }
        finishedAfter = System.currentTimeMillis() - start;
        System.out.println("Finished after " + finishedAfter / 1000d + "seconds");
        System.out.println("Arai takes " + (double) finishedAfter / count + " ms/image");
        System.out.println("Managed " + count + " images");
        System.exit(0);
    }
}
