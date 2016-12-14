package Blatt4.dct;

import org.jblas.DoubleMatrix;

/**
 * Created by Long Bui on 14.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class CosinusTransformation
{

    public static DoubleMatrix arai(DoubleMatrix x)
    {
        return Arai.calc(x);
    }

    public static DoubleMatrix direct(DoubleMatrix X)
    {
        return DCT.direct(X);
    }

    public static DoubleMatrix separated(DoubleMatrix x)
    {
        return DCT.separated(x);
    }
}
