package jpegencoder.encoding;

import jpegencoder.encoding.dct.CosineTransformation;
import jpegencoder.image.colors.ColorChannel;
import jpegencoder.segments.dqt.QuantizationTable;
import org.jblas.DoubleMatrix;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Long Bui on 14.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class DCTTests
{
    private DoubleMatrix X;
    private DoubleMatrix expected;

    @Before
    public void init()
    {
        X = new DoubleMatrix(new double[][]
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
                                                    {-9, -4, -2, -3, 6, 1, -1, -1}
                                            });
    }

    @Test
    public void testArai()
    {
        DoubleMatrix input = X.dup();
        CosineTransformation.arai(input);
        for (int i = 0; i < input.getRows(); i++)
        {
            for (int j = 0; j < input.getColumns(); j++)
            {
                Assert.assertEquals((int) expected.get(i, j),
                                    (int) Math.signum(input.get(i, j)) * Math.round(Math.abs(input.get(i, j))));
            }
        }
        printMatrix(input);
    }

    @Test
    public void testDirectDCT()
    {
        DoubleMatrix Y = CosineTransformation.direct(X);
        for (int i = 0; i < Y.getRows(); i++)
        {
            for (int j = 0; j < Y.getColumns(); j++)
            {
                Assert.assertEquals((int) expected.get(i, j),
                                    (int) Math.signum(Y.get(i, j)) * Math.round(Math.abs(Y.get(i, j))));
            }
        }
        printMatrix(Y);
    }

    @Test
    public void testSeparatedDCT()
    {
        DoubleMatrix Y = X.dup();
        CosineTransformation.separated(Y);
        for (int i = 0; i < Y.getRows(); i++)
        {
            for (int j = 0; j < Y.getColumns(); j++)
            {
                Assert.assertEquals((int) expected.get(i, j),
                                    (int) Math.signum(Y.get(i, j)) * Math.round(Math.abs(Y.get(i, j))));
            }
        }
        printMatrix(Y);
    }

    @Test
    public void testInverseDCTDirect()
    {
        DoubleMatrix Y = CosineTransformation.direct(X);
        DoubleMatrix testResult = CosineTransformation.invert(Y);
        for (int i = 0; i < Y.getRows(); i++)
        {
            for (int j = 0; j < Y.getColumns(); j++)
            {
                Assert.assertEquals((int) X.get(i, j),
                                    (int) Math.signum(testResult.get(i, j)) * Math.round(Math.abs(testResult.get(i,
                                                                                                                 j))));
            }
        }
        printMatrix(testResult);
    }

    @Test
    public void testInverseDCTSeparate()
    {
        DoubleMatrix Y = X.dup();
        CosineTransformation.separated(Y);
        DoubleMatrix testResult = CosineTransformation.invert(Y);
        for (int i = 0; i < Y.getRows(); i++)
        {
            for (int j = 0; j < Y.getColumns(); j++)
            {
                Assert.assertEquals((int) X.get(i, j),
                                    (int) Math.signum(testResult.get(i, j)) * Math.round(Math.abs(testResult.get(i,
                                                                                                                 j))));
            }
        }
        printMatrix(testResult);
    }

    @Test
    public void testInverseDCTArai()
    {
        DoubleMatrix input = X.dup();
        CosineTransformation.arai(input);
        printMatrix(input);
        DoubleMatrix testResult = CosineTransformation.invert(input);
        for (int i = 0; i < input.getRows(); i++)
        {
            for (int j = 0; j < input.getColumns(); j++)
            {
                Assert.assertEquals((int) X.get(i, j),
                                    (int) Math.signum(testResult.get(i, j))
                                            * Math.round(Math.abs(testResult.get(i, j))));
            }
        }
        printMatrix(testResult);
    }

    @Test
    public void testQuantization()
    {
        CosineTransformation.quantize(expected, QuantizationTable.QUANTIZATION_MATRIX_LUMINANCE);
        printMatrix(expected);
    }

    private void printMatrix(DoubleMatrix matrix)
    {
        for (int i = 0; i < matrix.getRows(); i++)
        {
            for (int j = 0; j < matrix.getColumns(); j++)
            {
                System.out.print((int) Math.signum(matrix.get(i, j))
                                         * Math.round(Math.abs(matrix.get(i, j))) + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void testDCTWithImage()
    {
        ColorChannel channel = new ColorChannel(16, 8);
        for (int i = 0; i < X.getRows(); i++)
        {
            for (int j = 0; j < X.getColumns(); j++)
            {
                channel.setPixel(j, i, (int) X.get(i, j));
            }
        }
        for (int i = 0; i < X.getRows(); i++)
        {
            for (int j = 0; j < X.getColumns(); j++)
            {
                channel.setPixel(8 + j, i, (int) X.get(i, j));
            }
        }
        CosineTransformation.arai(channel.getBlock(0));
        CosineTransformation.arai(channel.getBlock(1));
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                System.out.print(channel.getPixel(j, i));
            }
            System.out.println();
        }
        for (int y = 0; y < expected.getRows(); y++)
        {
            for (int x = 0; x < expected.getColumns(); x++)
            {
                Assert.assertEquals((int) expected.get(y, x),
                                    (int) Math.signum(channel.getPixel(x, y)) *
                                            Math.round(Math.abs(channel.getPixel(x,
                                                                                 y))));
            }
        }
    }
}
