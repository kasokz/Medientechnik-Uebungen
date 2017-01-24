package jpegencoder.image.colors;

import jpegencoder.image.colors.rgb.RGB;
import jpegencoder.image.colors.rgb.RGBImage;
import jpegencoder.image.colors.ycbcr.YCbCr;
import jpegencoder.image.colors.ycbcr.YCbCrImage;
import org.jblas.DoubleMatrix;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class ColorChannels
{
    public static YCbCrImage RGBToYCbCr(RGBImage rgbImage)
    {
        long start = System.currentTimeMillis();
        ColorChannel luminance = new ColorChannel(rgbImage.getWidth(), rgbImage.getHeight());
        ColorChannel cbChannel = new ColorChannel(rgbImage.getWidth(), rgbImage.getHeight());
        ColorChannel crChannel = new ColorChannel(rgbImage.getWidth(), rgbImage.getHeight());
        for (int y = 0; y < rgbImage.getHeight(); y++)
        {
            for (int x = 0; x < rgbImage.getWidth(); x++)
            {
                YCbCr converted = convertRGBToYCbCr(rgbImage.getRGBAt(x, y));
                luminance.setPixel(x, y, converted.getLuminanceChannel());
                cbChannel.setPixel(x, y, converted.getCbChannel());
                crChannel.setPixel(x, y, converted.getCrChannel());
            }
        }
        System.out.println("Finished RGB to YCbCr conversion in "
                                   + ((System.currentTimeMillis() - start) / 1000d)
                                   + " seconds");
        return new YCbCrImage(luminance,
                              cbChannel,
                              crChannel,
                              rgbImage.getOriginalWidth(),
                              rgbImage.getOriginalHeight());
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
        DoubleMatrix offsetMatrix = new DoubleMatrix(new double[]{
                128, 128, 128
        });
        DoubleMatrix rgbMatrix = new DoubleMatrix(pixel.getAsArray());
        DoubleMatrix yCbCrMatrix = prefixMatrix.add(conversionMatrix.mmul(rgbMatrix));
        DoubleMatrix resultMatrix = yCbCrMatrix.sub(offsetMatrix);
        return new YCbCr((int) Math.round(resultMatrix.get(0)),
                         (int) Math.round(resultMatrix.get(1)),
                         (int) Math.round(resultMatrix.get(2)));
    }

    public static int cap(double num)
    {
        if (num < -127)
        {
            return -127;
        }
        if (num > 128)
        {
            return 128;
        }
        return 0;
    }
}
