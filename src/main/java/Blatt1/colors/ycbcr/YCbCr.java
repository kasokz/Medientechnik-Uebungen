package Blatt1.colors.ycbcr;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class YCbCr
{
    private int ycbcr = 0;

    public YCbCr(int luminanceChannel, int cb, int cr)
    {

        this.ycbcr = luminanceChannel;
        this.ycbcr = (this.ycbcr << 8) + cb;
        this.ycbcr = (this.ycbcr << 8) + cr;
    }

    public int getLuminanceChannel()
    {
        return (ycbcr >> 16) & 255;
    }

    public int getCbChannel()
    {
        return (ycbcr >> 8) & 255;
    }

    public int getCrChannel()
    {
        return ycbcr & 255;
    }

    @Override
    public String toString()
    {
        return this.getLuminanceChannel() + "," + this.getCbChannel() + "," + this.getCrChannel();
    }
}
