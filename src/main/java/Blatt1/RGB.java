package Blatt1;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class RGB
{
    int red;
    int green;
    int blue;

    public RGB(int red, int green, int blue)
    {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public String toString()
    {
        return this.red + "," + this.green + "," + this.blue;
    }
}
