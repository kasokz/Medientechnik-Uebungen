package jpegencoder.image;

import jpegencoder.image.colors.ColorChannel;

/**
 * Created by Long Bui on 16.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public abstract class Image
{
    protected ColorChannel channel1;
    protected ColorChannel channel2;
    protected ColorChannel channel3;
    protected int originalHeight;
    protected int originalWidth;
    protected int subSampling = 1;

    public Image(ColorChannel channel1, ColorChannel channel2, ColorChannel channel3)
    {
        this.channel1 = channel1;
        this.channel2 = channel2;
        this.channel3 = channel3;
    }

    public ColorChannel getChannel1()
    {
        return channel1;
    }

    public ColorChannel getChannel2()
    {
        return channel2;
    }

    public ColorChannel getChannel3()
    {
        return channel3;
    }

    public int getHeight()
    {
        return channel1.getHeight();
    }

    public int getWidth()
    {
        return channel1.getWidth();
    }

    public int getSubSampling()
    {
        return subSampling;
    }

    public int getOriginalHeight()
    {
        return originalHeight;
    }

    public int getOriginalWidth()
    {
        return originalWidth;
    }
}
