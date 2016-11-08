package Blatt1.colors.rgb;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class RGB
{
    private int rgb = 0;

    public RGB(int red, int green, int blue)
    {
        this.rgb = red;
        this.rgb = (this.rgb << 8) + green;
        this.rgb = (this.rgb << 8) + blue;
    }

    public int getRed()
    {
        return (this.rgb >> 16) & 255;
    }

    public int getGreen()
    {
        return (this.rgb >> 8) & 255;
    }

    public int getBlue()
    {
        return this.rgb & 255;
    }

    public double[] getAsArray()
    {
        double[] rgbArray = new double[3];
        rgbArray[0] = this.getRed();
        rgbArray[1] = this.getGreen();
        rgbArray[2] = this.getBlue();
        return rgbArray;
    }

    @Override
    public String toString()
    {
        return this.getRed() + "," + this.getGreen() + "," + this.getBlue();
    }
}
