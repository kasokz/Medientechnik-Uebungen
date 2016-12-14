package Blatt4.dct;

import org.jblas.DoubleMatrix;

/**
 * Created by Long Bui on 14.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class CosineTransformation
{

    public static DoubleMatrix arai(DoubleMatrix x)
    {
        DoubleMatrix y = new DoubleMatrix(8, 8);
        for (int i = 0; i < 8; i++)
        {
            y.putRow(i,
                     Arai.calc(x.getRow(i)));
        }
        for (int i = 0; i < 8; i++)
        {
            y.putColumn(i,
                        Arai.calc(y.getColumn(i)));
        }
        return y;
    }

    public static DoubleMatrix direct(DoubleMatrix X)
    {
        return DCT.direct(X);
    }

    public static DoubleMatrix separated(DoubleMatrix X)
    {
        return DCT.separated(X);
    }

    public static DoubleMatrix invert(DoubleMatrix Y)
    {
        return DCT.invert(Y);
    }
}
