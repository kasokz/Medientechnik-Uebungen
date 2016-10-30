package Blatt1.colors;

import Blatt1.colors.rgb.RGB;
import Blatt1.colors.rgb.RGBPicture;
import Blatt1.colors.ycbcr.YCbCr;
import Blatt1.colors.ycbcr.YCbCrPicture;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class ColorChannels
{
    public static YCbCrPicture RGBToYCbCr(RGBPicture rgbPicture)
    {
        long start = System.currentTimeMillis();
        ArrayList<ArrayList<YCbCr>> newPictureValues = new ArrayList<ArrayList<YCbCr>>(rgbPicture.getHeight());
        for (int i = 0; i < rgbPicture.getHeight(); i++)
        {
            newPictureValues.add(new ArrayList<YCbCr>(rgbPicture.getWidth()));
        }
        for (int pixelRow = 0; pixelRow < rgbPicture.getHeight(); pixelRow++)
        {
            for (int pixelColumn = 0; pixelColumn < rgbPicture.getWidth(); pixelColumn++)
            {
                newPictureValues.get(pixelRow)
                                .add(convertRGBToYCbCr(rgbPicture.getRGBAt(pixelColumn, pixelRow)));
            }
        }
        System.out.println("Finished RGB to YCbCr conversion in "
                                   + ((System.currentTimeMillis() - start) / 1000d)
                                   + " seconds");
        return new YCbCrPicture(newPictureValues);
    }

    public static YCbCr convertRGBToYCbCr(RGB pixel)
    {
        DoubleMatrix prefixMatrix = new DoubleMatrix(new double[]{
                0, 128, 128
        });
        DoubleMatrix conversionMatrix = new DoubleMatrix(new double[][]{
                {0.299, 0.587, 0.114},
                {-0.168736, -0.331264, 0.5},
                {0.5, -0.418688, -0.081312}
        });
        DoubleMatrix rgbMatrix = new DoubleMatrix(pixel.getAsArray());
        DoubleMatrix resultMatrix = prefixMatrix.add((conversionMatrix.mmul(rgbMatrix)));
        return new YCbCr((int) resultMatrix.get(0), (int) resultMatrix.get(1), (int) resultMatrix.get(2));
    }
}
