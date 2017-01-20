package jpegencoder.image.colors.ycbcr;

import jpegencoder.image.Image;
import jpegencoder.image.colors.ColorChannel;
import org.jblas.DoubleMatrix;

import java.util.ArrayList;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class YCbCrImage extends Image
{
    public YCbCrImage(ColorChannel luminance, ColorChannel cbChannel, ColorChannel crChannel)
    {
        super(luminance, cbChannel, crChannel);

    }

    public int getHeight()
    {
        return getChannel1().getHeight();
    }

    public int getWidth()
    {
        return getChannel1().getWidth();
    }

    public YCbCr getPixelAt(int x, int y)
    {
        return new YCbCr((int) channel1.getPixel(x, y),
                         (int) channel2.getPixel(x / subSampling, y / subSampling),
                         (int) channel3.getPixel(x / subSampling, y / subSampling));
    }

    public void reduce(int subSampling)
    {
        this.channel2 = reduceChannel(channel2, subSampling);
        this.channel3 = reduceChannel(channel3, subSampling);
        this.subSampling = subSampling;
    }

    private ColorChannel reduceChannel(ColorChannel channel, int factor)
    {
        int reducedHeight = channel.getHeight() / factor;
        int reducedWidth = channel.getWidth() / factor;
        ColorChannel result = new ColorChannel(reducedWidth, reducedHeight);
        for (int y = 0; y < reducedHeight; y++)
        {
            for (int x = 0; x < reducedWidth; x++)
            {
                int sum = 0;
                for (int blockY = y * factor; blockY < y * factor + factor; blockY++)
                {
                    for (int blockX = x * factor; blockX < x * factor + factor; blockX++)
                    {
                        sum += channel.getPixel(blockX, blockY);
                    }
                }
                result.setPixel(x, y, (int) ((sum / (double) (factor * factor)) + 0.5));
            }
        }
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getHeight(); i++)
        {
            for (int j = 0; j < getWidth(); j++)
            {
                sb.append(getPixelAt(i, j).toString())
                  .append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
