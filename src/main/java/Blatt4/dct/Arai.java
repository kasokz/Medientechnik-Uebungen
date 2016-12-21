package Blatt4.dct;

import org.jblas.DoubleMatrix;

/**
 * Created by Long Bui on 14.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
class Arai
{
    private static final DoubleMatrix C = new DoubleMatrix(new double[]
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
    private static final DoubleMatrix A = new DoubleMatrix(new double[]
                                                                   {
                                                                           1,
                                                                           C.get(4),
                                                                           C.get(2) - C.get(6),
                                                                           C.get(4),
                                                                           C.get(6) + C.get(2),
                                                                           C.get(6)
                                                                   });
    private static final DoubleMatrix S = new DoubleMatrix(new double[]
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

    static DoubleMatrix calc(DoubleMatrix vector)
    {
        DoubleMatrix tempVector = new DoubleMatrix(8);
        araiStep1(vector, tempVector);
        araiStep2(tempVector, vector);
        araiStep3(vector, tempVector);
        araiStep4(tempVector, vector);
        araiStep5(vector, tempVector);
        araiStep6(tempVector, vector);
        araiStep7(vector, tempVector);
        return tempVector;
    }

    private static void araiStep1(DoubleMatrix input, DoubleMatrix output)
    {
        output.put(0, input.get(0) + input.get(7));
        output.put(1, input.get(1) + input.get(6));
        output.put(2, input.get(2) + input.get(5));
        output.put(3, input.get(3) + input.get(4));
        output.put(4, input.get(3) + -input.get(4));
        output.put(5, input.get(2) + -input.get(5));
        output.put(6, input.get(1) + -input.get(6));
        output.put(7, input.get(0) + -input.get(7));
    }

    private static void araiStep2(DoubleMatrix input, DoubleMatrix output)
    {
        output.put(0, input.get(0) + input.get(3));
        output.put(1, input.get(1) + input.get(2));
        output.put(2, input.get(1) + -input.get(2));
        output.put(3, input.get(0) + -input.get(3));
        output.put(4, -input.get(4) + -input.get(5));
        output.put(5, input.get(5) + input.get(6));
        output.put(6, input.get(6) + input.get(7));
        output.put(7, input.get(7));
    }

    private static void araiStep3(DoubleMatrix input, DoubleMatrix output)
    {
        output.put(0, input.get(0) + input.get(1));
        output.put(1, input.get(0) + -input.get(1));
        output.put(2, input.get(2) + input.get(3));
        output.put(3, input.get(3));
        output.put(4, input.get(4));
        output.put(5, input.get(5));
        output.put(6, input.get(6));
        output.put(7, input.get(7));
    }

    private static void araiStep4(DoubleMatrix input, DoubleMatrix output)
    {
        double araiStep4Temp = ((input.get(4) + input.get(6)) * A.get(5));
        output.put(0, input.get(0));
        output.put(1, input.get(1));
        output.put(2, input.get(2) * A.get(1));
        output.put(3, input.get(3));
        output.put(4, -(input.get(4) * A.get(2)) + -araiStep4Temp);
        output.put(5, input.get(5) * A.get(3));
        output.put(6, (input.get(6) * A.get(4)) + -(araiStep4Temp));
        output.put(7, input.get(7));
    }

    private static void araiStep5(DoubleMatrix input, DoubleMatrix output)
    {
        output.put(0, input.get(0));
        output.put(1, input.get(1));
        output.put(2, input.get(2) + input.get(3));
        output.put(3, -input.get(2) + input.get(3));
        output.put(4, input.get(4));
        output.put(5, input.get(5) + input.get(7));
        output.put(6, input.get(6));
        output.put(7, -input.get(5) + input.get(7));
    }

    private static void araiStep6(DoubleMatrix input, DoubleMatrix output)
    {
        output.put(0, input.get(0));
        output.put(1, input.get(1));
        output.put(2, input.get(2));
        output.put(3, input.get(3));
        output.put(4, input.get(4) + input.get(7));
        output.put(5, input.get(5) + input.get(6));
        output.put(6, input.get(5) + -input.get(6));
        output.put(7, -input.get(4) + input.get(7));
    }

    private static void araiStep7(DoubleMatrix input, DoubleMatrix output)
    {
        output.put(0, input.get(0) * S.get(0));
        output.put(1, input.get(5) * S.get(1));
        output.put(2, input.get(2) * S.get(2));
        output.put(3, input.get(7) * S.get(3));
        output.put(4, input.get(1) * S.get(4));
        output.put(5, input.get(4) * S.get(5));
        output.put(6, input.get(3) * S.get(6));
        output.put(7, input.get(6) * S.get(7));
    }
}
