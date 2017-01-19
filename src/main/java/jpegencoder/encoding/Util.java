package jpegencoder.encoding;

import org.jblas.DoubleMatrix;

/**
 * Created by Long Bui on 16.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class Util
{
    public static DoubleMatrix testZigZag = new DoubleMatrix(new double[][]{
            {0, 1, 5, 6, 14, 15, 27, 28},
            {2, 4, 7, 13, 16, 26, 29, 42},
            {3, 8, 12, 17, 25, 30, 41, 43},
            {9, 11, 18, 24, 31, 40, 44, 53},
            {10, 19, 23, 32, 39, 45, 52, 54},
            {20, 22, 33, 38, 46, 51, 55, 60},
            {21, 34, 37, 47, 50, 56, 59, 61},
            {35, 36, 48, 49, 57, 58, 62, 63}});

    private static final int[] indexXzigzag = {0, 1, 0, 0, 1, 2, 3, 2,
                                               1, 0, 0, 1, 2, 3, 4, 5,
                                               4, 3, 2, 1, 0, 0, 1, 2,
                                               3, 4, 5, 6, 7, 6, 5, 4,
                                               3, 2, 1, 0, 1, 2, 3, 4,
                                               5, 6, 7, 7, 6, 5, 4, 3,
                                               2, 3, 4, 5, 6, 7, 7, 6,
                                               5, 4, 5, 6, 7, 7, 6, 7};
    private static final int[] indexYzigzag = {0, 0, 1, 2, 1, 0, 0, 1,
                                               2, 3, 4, 3, 2, 1, 0, 0,
                                               1, 2, 3, 4, 5, 6, 5, 4,
                                               3, 2, 1, 0, 0, 1, 2, 3,
                                               4, 5, 6, 7, 7, 6, 5, 4,
                                               3, 2, 1, 2, 3, 4, 5, 6,
                                               7, 7, 6, 5, 4, 3, 4, 5,
                                               6, 7, 7, 6, 5, 6, 7, 7};

    public static int[] zigzagSort(DoubleMatrix matrix)
    {
        int[] result = new int[64];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = round(matrix.get(indexYzigzag[i], indexXzigzag[i]));
        }
        return result;
    }

    public static int round(double toRound)
    {
        return (int) (Math.signum(toRound)
                * Math.round(Math.abs(toRound)));
    }

    public static String getBitsAsString(int bits, int length)
    {
        String result = "";
        for (int i = length - 1; i >= 0; i--)
        {
            result += "" + ((bits >> i) & 0x1);
        }
        return result;
    }
}
