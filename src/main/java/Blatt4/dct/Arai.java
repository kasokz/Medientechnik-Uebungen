package Blatt4.dct;

import org.jblas.DoubleMatrix;

/**
 * Created by Long Bui on 14.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class Arai
{
    private static DoubleMatrix C = new DoubleMatrix(new double[]
                                                             {
                                                                     1,
                                                                     Math.cos((1 * Math.PI) / 16),
                                                                     Math.cos((2 * Math.PI) / 16),
                                                                     Math.cos((3 * Math.PI) / 16),
                                                                     Math.cos((4 * Math.PI) / 16),
                                                                     Math.cos((5 * Math.PI) / 16),
                                                                     Math.cos((6 * Math.PI) / 16),
                                                                     Math.cos((7 * Math.PI) / 16)
                                                             });
    private static DoubleMatrix A = new DoubleMatrix(new double[]
                                                             {
                                                                     1,
                                                                     C.get(4),
                                                                     C.get(2) - C.get(6),
                                                                     C.get(4),
                                                                     C.get(6) + C.get(2),
                                                                     C.get(6)
                                                             });
    private static DoubleMatrix S = new DoubleMatrix(new double[]
                                                             {
                                                                     1d / (2 * Math.sqrt(2)),
                                                                     1d / (4 * C.get(1)),
                                                                     1d / (4 * C.get(2)),
                                                                     1d / (4 * C.get(3)),
                                                                     1d / (4 * C.get(4)),
                                                                     1d / (4 * C.get(5)),
                                                                     1d / (4 * C.get(6)),
                                                                     1d / (4 * C.get(7))
                                                             });

    public static DoubleMatrix calc(DoubleMatrix x)
    {
        DoubleMatrix y = new DoubleMatrix(8);
        araiStep1(x, y);
        araiStep2(x, y);
        araiStep3(x, y);
        araiStep4(x, y);
        araiStep5(x, y);
        araiStep6(x, y);
        araiStep7(x, y);
        y.put(0, x.get(0));
        y.put(1, x.get(5));
        y.put(2, x.get(2));
        y.put(3, x.get(7));
        y.put(4, x.get(1));
        y.put(5, x.get(4));
        y.put(6, x.get(3));
        y.put(7, x.get(6));
        return y;
    }

    private static void araiStep1(DoubleMatrix x, DoubleMatrix y)
    {
        y.put(0, x.get(0) + x.get(7));
        y.put(1, x.get(1) + x.get(6));
        y.put(2, x.get(2) + x.get(5));
        y.put(3, x.get(3) + x.get(4));
        y.put(4, x.get(3) + -x.get(4));
        y.put(5, x.get(2) + -x.get(5));
        y.put(6, x.get(1) + -x.get(6));
        y.put(7, x.get(0) + -x.get(7));
        updateInputMatrix(x, y);
    }

    private static void araiStep2(DoubleMatrix x, DoubleMatrix y)
    {
        y.put(0, x.get(0) + x.get(3));
        y.put(1, x.get(1) + x.get(2));
        y.put(2, x.get(1) + -x.get(2));
        y.put(3, x.get(0) + -x.get(3));
        y.put(4, -x.get(4) + -x.get(5));
        y.put(5, x.get(5) + x.get(6));
        y.put(6, x.get(6) + x.get(7));
        updateInputMatrix(x, y);
    }

    private static void araiStep3(DoubleMatrix x, DoubleMatrix y)
    {
        y.put(0, x.get(0) + x.get(1));
        y.put(1, x.get(0) + -x.get(1));
        y.put(2, x.get(2) + x.get(3));
        updateInputMatrix(x, y);
    }

    private static void araiStep4(DoubleMatrix x, DoubleMatrix y)
    {
        double araiStep4Temp = ((x.get(4) + x.get(6)) * A.get(5));
        y.put(2, x.get(2) * A.get(1));
        y.put(4, -(x.get(4) * A.get(2)) + -araiStep4Temp);
        y.put(5, x.get(5) * x.get(3));
        y.put(6, (x.get(6) * x.get(4)) + -(araiStep4Temp));
        updateInputMatrix(x, y);
    }

    private static void araiStep5(DoubleMatrix x, DoubleMatrix y)
    {
        y.put(2, x.get(2) + x.get(3));
        y.put(3, -x.get(2) + x.get(3));
        y.put(5, x.get(5) + x.get(7));
        y.put(7, -x.get(5) + x.get(7));
        updateInputMatrix(x, y);
    }

    private static void araiStep6(DoubleMatrix x, DoubleMatrix y)
    {
        y.put(4, x.get(4) + x.get(7));
        y.put(5, x.get(5) + x.get(6));
        y.put(6, x.get(5) + -x.get(6));
        y.put(7, -x.get(4) + x.get(7));
        updateInputMatrix(x, y);
    }

    private static void araiStep7(DoubleMatrix x, DoubleMatrix y)
    {
        y.put(0, x.get(0) * S.get(0));
        y.put(1, x.get(1) * S.get(4));
        y.put(2, x.get(2) * S.get(2));
        y.put(3, x.get(3) * S.get(6));
        y.put(4, x.get(4) * S.get(5));
        y.put(5, x.get(5) * S.get(1));
        y.put(6, x.get(6) * S.get(7));
        y.put(7, x.get(7) * S.get(3));
        updateInputMatrix(x, y);
    }

    private static void updateInputMatrix(DoubleMatrix x, DoubleMatrix y)
    {
        for (int i = 0; i < 8; i++)
        {
            x.put(i, y.get(i));
        }
    }
}
