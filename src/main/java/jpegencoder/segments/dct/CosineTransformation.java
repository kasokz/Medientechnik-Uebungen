package jpegencoder.segments.dct;

import org.jblas.DoubleMatrix;

/**
 * Created by Long Bui on 14.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class CosineTransformation
{
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

    public static void arai(DoubleMatrix x)
    {
        for (int i = 0; i < 8; i++)
        {
            x.putRow(i, Arai.calc(x.getRow(i)));
        }
        for (int i = 0; i < 8; i++)
        {
            x.putColumn(i, Arai.calc(x.getColumn(i)));
        }
    }

    public static DoubleMatrix direct(DoubleMatrix X)
    {
        return DCT.direct(X);
    }

    public static DoubleMatrix separated(DoubleMatrix X)
    {
        DCT.separated(X);
        return X;
    }

    public static DoubleMatrix invert(DoubleMatrix Y)
    {
        return DCT.invert(Y);
    }

    public static DoubleMatrix quantize(DoubleMatrix transformedMatrix, DoubleMatrix quantizationMatrix)
    {
        return transformedMatrix.div(quantizationMatrix);
    }
}
