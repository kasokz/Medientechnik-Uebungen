package Blatt4;

import Blatt4.dct.CosinusTransformation;
import org.jblas.DoubleMatrix;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static Blatt4.dct.DCT.A;
import static Blatt4.dct.DCT.A_T;

/**
 * Created by Long Bui on 14.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class Blatt4
{
    DoubleMatrix x;
    DoubleMatrix expected;

    @Before
    public void init()
    {
        x = new DoubleMatrix(new double[][]
                                     {
                                             {47, 18, 13, 16, 41, 90, 47, 27},
                                             {62, 42, 35, 39, 66, 90, 41, 26},
                                             {71, 55, 56, 67, 55, 40, 22, 39},
                                             {53, 60, 63, 50, 48, 25, 37, 87},
                                             {31, 27, 33, 27, 37, 50, 81, 147},
                                             {54, 31, 33, 46, 58, 104, 144, 179},
                                             {76, 70, 71, 91, 118, 151, 176, 184},
                                             {102, 105, 115, 124, 135, 168, 173, 181}
                                     });
        expected = new DoubleMatrix(new double[][]
                                            {
                                                    {581, -144, 56, 17, 15, -7, 25, -9},
                                                    {-242, 133, -48, 42, -2, -7, 13, -4},
                                                    {108, -18, -40, 71, -33, 12, 6, -10},
                                                    {-56, -93, 48, 19, -8, 7, 6, -2},
                                                    {-17, 9, 7, -23, -3, -10, 5, 3},
                                                    {4, 9, -4, -5, 2, 2, -7, 3},
                                                    {-9, 7, 8, -6, 5, 12, 2, -5},
                                                    {-9, -4, -2, -3, 6, 1, -1, -1},
                                                    });
    }

    @Test
    public void testArai()
    {
        DoubleMatrix x = new DoubleMatrix(new double[]{1, 1, 1, 1, 1, 1, 1, 1});
        DoubleMatrix y = CosinusTransformation.arai(x);
        Assert.assertEquals(4d / Math.sqrt(2), y.get(0), 0.001);
        Assert.assertEquals(0, y.get(1), 0.001);
        Assert.assertEquals(0, y.get(2), 0.001);
        Assert.assertEquals(0, y.get(3), 0.001);
        Assert.assertEquals(0, y.get(4), 0.001);
        Assert.assertEquals(0, y.get(5), 0.001);
        Assert.assertEquals(0, y.get(6), 0.001);
        Assert.assertEquals(0, y.get(7), 0.001);
    }

    @Test
    public void testDirectDCT()
    {
        DoubleMatrix y = CosinusTransformation.direct(x);
        for (int i = 0; i < y.getRows(); i++)
        {
            for (int j = 0; j < y.getColumns(); j++)
            {
                Assert.assertEquals((int) expected.get(i, j),
                                    (int) Math.signum(y.get(i, j)) * Math.round(Math.abs(y.get(i, j))));
            }
            System.out.println();
        }
    }

    @Test
    public void testSeparatedDCT()
    {
        DoubleMatrix y = CosinusTransformation.separated(x);
        for (int i = 0; i < y.getRows(); i++)
        {
            for (int j = 0; j < y.getColumns(); j++)
            {
                Assert.assertEquals((int) expected.get(i, j),
                                    (int) Math.signum(y.get(i, j)) * Math.round(Math.abs(y.get(i, j))));
            }
            System.out.println();
        }
    }
}
