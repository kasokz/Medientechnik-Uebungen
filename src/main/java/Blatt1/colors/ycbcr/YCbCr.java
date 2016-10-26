package Blatt1.colors.ycbcr;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class YCbCr
{
    private int luminanceChannel;
    private int cbChannel;
    private int crChannel;

    public YCbCr(int luminanceChannel, int cb, int crChannel)
    {
        this.luminanceChannel = luminanceChannel;
        this.cbChannel = cb;
        this.crChannel = crChannel;
    }

    public int getLuminanceChannel()
    {
        return luminanceChannel;
    }

    public int getCbChannel()
    {
        return cbChannel;
    }

    public int getCrChannel()
    {
        return crChannel;
    }

    @Override
    public String toString()
    {
        return this.luminanceChannel + "," + this.cbChannel + "," + this.crChannel;
    }
}
