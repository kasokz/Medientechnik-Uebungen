package jpegencoder.segments.dqt;

import jpegencoder.encoding.Util;
import jpegencoder.streams.BitOutputStream;
import org.jblas.DoubleMatrix;

import java.io.IOException;

/**
 * Created by Long Bui on 12.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class QuantizationTable
{
    public static DoubleMatrix QUANTIZATION_MATRIX_LUMINANCE1 = new DoubleMatrix(new double[][]{
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15}
    });
    public static DoubleMatrix QUANTIZATION_MATRIX_LUMINANCE = new DoubleMatrix(new double[][]{
            {16, 11, 10, 16, 24, 40, 51, 61},
            {12, 12, 14, 19, 26, 58, 60, 55},
            {14, 13, 16, 24, 40, 57, 69, 56},
            {14, 17, 22, 29, 51, 87, 80, 62},
            {18, 22, 37, 56, 68, 109, 103, 77},
            {24, 35, 55, 64, 81, 104, 113, 92},
            {49, 64, 78, 87, 103, 121, 120, 101},
            {72, 92, 95, 98, 112, 100, 103, 99}
    });
    public static DoubleMatrix QUANTIZATION_MATRIX_CHROMINANCE1 = new DoubleMatrix(new double[][]{
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15},
            {15, 15, 15, 15, 15, 15, 15, 15}
    });
    public static DoubleMatrix QUANTIZATION_MATRIX_CHROMINANCE = new DoubleMatrix(new double[][]{
            {17, 18, 24, 47, 99, 99, 99, 99},
            {18, 21, 26, 66, 99, 99, 99, 99},
            {24, 26, 56, 99, 99, 99, 99, 99},
            {47, 66, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99}
    });

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
        int[] zigzaged = Util.zigzagSort(table);
        for (int tableEntry : zigzaged)
        {
            os.writeByte(tableEntry);
        }
    }

}
