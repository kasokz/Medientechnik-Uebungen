package jpegencoder.segments.dqt;

import jpegencoder.streams.BitOutputStream;
import org.jblas.DoubleMatrix;

import java.io.IOException;

/**
 * Created by Long Bui on 12.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class QuantizationTable
{
    private int id;
    private int precision = 0;
    private DoubleMatrix table;

    public QuantizationTable(int id, DoubleMatrix table)
    {
        this.id = id;
        this.table = table;
    }

    public void writeTable(BitOutputStream bos) throws IOException
    {
        bos.writeByte((precision << 4) + id);
        zigzagSort(bos);
    }

    private void zigzagSort(BitOutputStream os) throws IOException
    {
        int[] zigzaged = zigzagSort(table);
        for (int tableEntry : zigzaged)
        {
            os.writeByte(tableEntry);
        }
    }

    public static int[] zigzagSort(DoubleMatrix matrix)
    {
        int[] result = new int[64];
        result[0] = (int) matrix.get(0, 0);
        result[1] = (int) matrix.get(0, 1);
        result[2] = (int) matrix.get(1, 0);
        result[3] = (int) matrix.get(2, 0);
        result[4] = (int) matrix.get(1, 1);
        result[5] = (int) matrix.get(0, 2);
        result[6] = (int) matrix.get(0, 3);
        result[7] = (int) matrix.get(1, 2);

        result[8] = (int) matrix.get(2, 1);
        result[9] = (int) matrix.get(3, 0);
        result[10] = (int) matrix.get(4, 0);
        result[11] = (int) matrix.get(3, 1);
        result[12] = (int) matrix.get(2, 2);
        result[13] = (int) matrix.get(1, 3);
        result[14] = (int) matrix.get(0, 4);
        result[15] = (int) matrix.get(0, 5);

        result[16] = (int) matrix.get(1, 4);
        result[17] = (int) matrix.get(2, 3);
        result[18] = (int) matrix.get(3, 2);
        result[19] = (int) matrix.get(4, 1);
        result[20] = (int) matrix.get(5, 0);
        result[21] = (int) matrix.get(6, 0);
        result[22] = (int) matrix.get(5, 1);
        result[23] = (int) matrix.get(4, 2);

        result[24] = (int) matrix.get(3, 3);
        result[25] = (int) matrix.get(2, 4);
        result[26] = (int) matrix.get(1, 5);
        result[27] = (int) matrix.get(0, 6);
        result[28] = (int) matrix.get(0, 7);
        result[29] = (int) matrix.get(1, 6);
        result[30] = (int) matrix.get(2, 5);
        result[31] = (int) matrix.get(3, 4);

        result[32] = (int) matrix.get(4, 3);
        result[33] = (int) matrix.get(5, 2);
        result[34] = (int) matrix.get(6, 1);
        result[35] = (int) matrix.get(7, 0);
        result[36] = (int) matrix.get(7, 1);
        result[37] = (int) matrix.get(6, 2);
        result[38] = (int) matrix.get(5, 3);
        result[39] = (int) matrix.get(4, 4);

        result[40] = (int) matrix.get(3, 5);
        result[41] = (int) matrix.get(2, 6);
        result[42] = (int) matrix.get(1, 7);
        result[43] = (int) matrix.get(2, 7);
        result[44] = (int) matrix.get(3, 6);
        result[45] = (int) matrix.get(4, 5);
        result[46] = (int) matrix.get(5, 4);
        result[47] = (int) matrix.get(6, 3);

        result[48] = (int) matrix.get(7, 2);
        result[49] = (int) matrix.get(7, 3);
        result[50] = (int) matrix.get(6, 4);
        result[51] = (int) matrix.get(5, 5);
        result[52] = (int) matrix.get(4, 6);
        result[53] = (int) matrix.get(3, 7);
        result[54] = (int) matrix.get(4, 7);
        result[55] = (int) matrix.get(5, 6);

        result[56] = (int) matrix.get(6, 5);
        result[57] = (int) matrix.get(7, 4);
        result[58] = (int) matrix.get(7, 5);
        result[59] = (int) matrix.get(6, 6);
        result[60] = (int) matrix.get(5, 7);
        result[61] = (int) matrix.get(6, 7);
        result[62] = (int) matrix.get(7, 6);
        result[63] = (int) matrix.get(7, 7);

        return result;
    }
}
