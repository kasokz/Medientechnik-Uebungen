package jpegencoder.image.colors;

import org.jblas.DoubleMatrix;

/**
 * Created by Long Bui on 12/01/2017.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class ColorChannel
{
    private DoubleMatrix[] blocks;
    private int height;
    private int width;

    public ColorChannel(int height, int width)
    {
        this.height = height;
        this.width = width;
        blocks = new DoubleMatrix[(height / 8) * (width / 8)];
        for (int i = 0; i < (height/8) * (width/8); i++)
        {
            blocks[i] = DoubleMatrix.zeros(8, 8);
        }
    }

    public void setPixel(int x, int y, int value)
    {
        blocks[(x / 8) + ((height/8) * (y / 8))].put(y % 8, x % 8, value);
    }

    public int getPixel(int x, int y)
    {
        DoubleMatrix block = blocks[x / 8 + ((height/8) * (y / 8))];
        return (int) block.get(y % 8, x % 8);
    }

    public DoubleMatrix getBlock(int index)
    {
        return blocks[index];
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }
}
