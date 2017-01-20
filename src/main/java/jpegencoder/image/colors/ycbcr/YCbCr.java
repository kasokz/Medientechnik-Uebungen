package jpegencoder.image.colors.ycbcr;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class YCbCr
{
    private int y;
    private int cb;
    private int cr;

    public YCbCr(int luminanceChannel, int cb, int cr)
    {

        this.y = luminanceChannel;
        this.cb = cb;
        this.cr = cr;
    }

    public int getLuminanceChannel()
    {
        return y;
    }

    public int getCbChannel()
    {
        return cb;
    }

    public int getCrChannel()
    {
        return cr;
    }

    @Override
    public String toString()
    {
        return this.getLuminanceChannel() + "," + this.getCbChannel() + "," + this.getCrChannel();
    }
}
