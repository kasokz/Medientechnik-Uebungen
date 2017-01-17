package jpegencoder;

import jpegencoder.encoding.dct.CosineTransformation;
import jpegencoder.image.Image;
import jpegencoder.image.colors.ColorChannel;
import org.jblas.DoubleMatrix;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Long Bui on 17.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class JpegEncoderTest
{
    DoubleMatrix X = new DoubleMatrix(new double[][]
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
    DoubleMatrix expected = new DoubleMatrix(new double[][]
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

    @Test
    public void testChannelTransformation()
    {
        ColorChannel channelToTest = new ColorChannel(8, 8);
        for (int i = 0; i < X.getRows(); i++)
        {
            for (int j = 0; j < X.getColumns(); j++)
            {
                channelToTest.setPixel(j, i, (int) X.get(i, j));
            }
        }
        Image image = new Image(channelToTest, channelToTest, channelToTest)
        {

        };
        JpegEncoder jpegEncoder = new JpegEncoder(image);
        jpegEncoder.performDCT();
        printMatrix(jpegEncoder.image.getChannel1().getBlock(0));
    }

    private void printMatrix(DoubleMatrix matrix)
    {
        for (int i = 0; i < matrix.getRows(); i++)
        {
            for (int j = 0; j < matrix.getColumns(); j++)
            {
                System.out.print(matrix.get(j, i) + " ");
            }
            System.out.println();
        }
    }
}
