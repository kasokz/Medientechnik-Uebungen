package jpegencoder.segments.dct;

import org.jblas.DoubleMatrix;

/**
 * Created by Long Bui on 14.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
class DCT
{
    private static final DoubleMatrix C = new DoubleMatrix(new double[]
                                                                   {
                                                                           1d / Math.sqrt(2),
                                                                           1, 1, 1, 1, 1, 1, 1
                                                                   });
    private static DoubleMatrix A;
    private static DoubleMatrix A_T;

    static
    {
        A = new DoubleMatrix(new double[8][8]);
        for (int n = 0; n < 8; n++)
        {
            for (int k = 0; k < 8; k++)
            {
                double a_k_n = C.get(k) * Math.sqrt(2d / 8) * Math.cos((2 * n + 1) * ((k * Math.PI) / (2 * 8)));
                A.put(k, n, a_k_n);
            }
        }
        A_T = A.transpose();
    }

    static DoubleMatrix direct(DoubleMatrix X)
    {
        int N = X.getRows();
        DoubleMatrix Y = new DoubleMatrix(N, N);
        for (int j = 0; j < N; j++)
        {
            for (int i = 0; i < N; i++)
            {
                double temp = 0;
                for (int x = 0; x < N; x++)
                {
                    for (int y = 0; y < N; y++)
                    {
                        temp += X.get(y, x)
                                * Math.cos(((2 * x + 1) * i * Math.PI) / (2d * N))
                                * Math.cos(((2 * y + 1) * j * Math.PI) / (2d * N));
                    }
                }
                double Y_i_j = (2d / N) * C.get(i) * C.get(j) * temp;
                Y.put(j, i, Y_i_j);
            }
        }
        return Y;
    }

    static void separated(DoubleMatrix X)
    {
        A.mmuli(X, X).mmuli(A_T, X);
    }

    static DoubleMatrix invert(DoubleMatrix Y)
    {
        int N = Y.getRows();
        DoubleMatrix X = new DoubleMatrix(N, N);
        for (int y = 0; y < N; y++)
        {
            for (int x = 0; x < N; x++)
            {
                double X_x_y = 0;
                for (int i = 0; i < N; i++)
                {
                    for (int j = 0; j < N; j++)
                    {
                        X_x_y += (2d / N) * C.get(i) * C.get(j) * Y.get(j, i)
                                * Math.cos(((2 * x + 1) * i * Math.PI) / (2d * N))
                                * Math.cos(((2 * y + 1) * j * Math.PI) / (2d * N));
                    }
                }
                X.put(y, x, X_x_y);
            }
        }
        return X;
    }
}
