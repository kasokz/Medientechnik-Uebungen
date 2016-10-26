package Blatt1;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class RGB
{
    private int red;
    private int green;
    private int blue;

    public RGB(int red, int green, int blue)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed()
    {
        return red;
    }

    public int getGreen()
    {
        return green;
    }

    public int getBlue()
    {
        return blue;
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
        return this.red + "," + this.green + "," + this.blue;
    }
}
