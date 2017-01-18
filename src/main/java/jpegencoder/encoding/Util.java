package jpegencoder.encoding;

import org.jblas.DoubleMatrix;

/**
 * Created by Long Bui on 16.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class Util
{
    public static int[] zigzagSort(DoubleMatrix matrix)
    {

        int[] result = new int[64];
        result[0] = round(matrix.get(0, 0));
        result[1] = round(matrix.get(0, 1));
        result[2] = round(matrix.get(1, 0));
        result[3] = round(matrix.get(2, 0));
        result[4] = round(matrix.get(1, 1));
        result[5] = round(matrix.get(0, 2));
        result[6] = round(matrix.get(0, 3));
        result[7] = round(matrix.get(1, 2));

        result[8] = round(matrix.get(2, 1));
        result[9] = round(matrix.get(3, 0));
        result[10] = round(matrix.get(4, 0));
        result[11] = round(matrix.get(3, 1));
        result[12] = round(matrix.get(2, 2));
        result[13] = round(matrix.get(1, 3));
        result[14] = round(matrix.get(0, 4));
        result[15] = round(matrix.get(0, 5));

        result[16] = round(matrix.get(1, 4));
        result[17] = round(matrix.get(2, 3));
        result[18] = round(matrix.get(3, 2));
        result[19] = round(matrix.get(4, 1));
        result[20] = round(matrix.get(5, 0));
        result[21] = round(matrix.get(6, 0));
        result[22] = round(matrix.get(5, 1));
        result[23] = round(matrix.get(4, 2));

        result[24] = round(matrix.get(3, 3));
        result[25] = round(matrix.get(2, 4));
        result[26] = round(matrix.get(1, 5));
        result[27] = round(matrix.get(0, 6));
        result[28] = round(matrix.get(0, 7));
        result[29] = round(matrix.get(1, 6));
        result[30] = round(matrix.get(2, 5));
        result[31] = round(matrix.get(3, 4));

        result[32] = round(matrix.get(4, 3));
        result[33] = round(matrix.get(5, 2));
        result[34] = round(matrix.get(6, 1));
        result[35] = round(matrix.get(7, 0));
        result[36] = round(matrix.get(7, 1));
        result[37] = round(matrix.get(6, 2));
        result[38] = round(matrix.get(5, 3));
        result[39] = round(matrix.get(4, 4));

        result[40] = round(matrix.get(3, 5));
        result[41] = round(matrix.get(2, 6));
        result[42] = round(matrix.get(1, 7));
        result[43] = round(matrix.get(2, 7));
        result[44] = round(matrix.get(3, 6));
        result[45] = round(matrix.get(4, 5));
        result[46] = round(matrix.get(5, 4));
        result[47] = round(matrix.get(6, 3));

        result[48] = round(matrix.get(7, 2));
        result[49] = round(matrix.get(7, 3));
        result[50] = round(matrix.get(6, 4));
        result[51] = round(matrix.get(5, 5));
        result[52] = round(matrix.get(4, 6));
        result[53] = round(matrix.get(3, 7));
        result[54] = round(matrix.get(4, 7));
        result[55] = round(matrix.get(5, 6));

        result[56] = round(matrix.get(6, 5));
        result[57] = round(matrix.get(7, 4));
        result[58] = round(matrix.get(7, 5));
        result[59] = round(matrix.get(6, 6));
        result[60] = round(matrix.get(5, 7));
        result[61] = round(matrix.get(6, 7));
        result[62] = round(matrix.get(7, 6));
        result[63] = round(matrix.get(7, 7));

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
