package jpegencoder.ppm.colors;

import jpegencoder.ppm.colors.rgb.RGB;
import jpegencoder.ppm.colors.rgb.RGBImage;
import jpegencoder.ppm.colors.ycbcr.YCbCr;
import jpegencoder.ppm.colors.ycbcr.YCbCrImage;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class ColorChannels
{
    public static YCbCrImage RGBToYCbCr(RGBImage rgbImage)
    {
        long start = System.currentTimeMillis();
        ArrayList<ArrayList<YCbCr>> newPictureValues = new ArrayList<ArrayList<YCbCr>>(rgbImage.getHeight());
        for (int i = 0; i < rgbImage.getHeight(); i++)
        {
            newPictureValues.add(new ArrayList<YCbCr>(rgbImage.getWidth()));
        }
        for (int pixelRow = 0; pixelRow < rgbImage.getHeight(); pixelRow++)
        {
            for (int pixelColumn = 0; pixelColumn < rgbImage.getWidth(); pixelColumn++)
            {
                newPictureValues.get(pixelRow)
                                .add(convertRGBToYCbCr(rgbImage.getRGBAt(pixelColumn, pixelRow)));
            }
        }
        System.out.println("Finished RGB to YCbCr conversion in "
                                   + ((System.currentTimeMillis() - start) / 1000d)
                                   + " seconds");
        return new YCbCrImage(newPictureValues);
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
