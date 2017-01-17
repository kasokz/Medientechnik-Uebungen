package jpegencoder.image.colors;

import org.jblas.DoubleMatrix;

import java.util.Arrays;
import java.util.List;

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
        for (int i = 0; i < (height / 8) * (width / 8); i++)
        {
            blocks[i] = DoubleMatrix.zeros(8, 8);
        }
    }

    public void setPixel(int x, int y, double value)
    {
        blocks[(x / 8) + ((height / 8) * (y / 8))].put(y % 8, x % 8, value);
    }

    public double getPixel(int x, int y)
    {
        DoubleMatrix block = blocks[x / 8 + ((height / 8) * (y / 8))];
        return block.get(y % 8, x % 8);
    }

    public int getPlainIndexOfBlock(int x, int y)
    {
        return (x % (width / 8)) + (y * (height / 8));
    }

    public DoubleMatrix getBlock(int x, int y)
    {
        return blocks[(x % (width / 8)) + (y * (height / 8))];
    }

    public DoubleMatrix getBlock(int index)
    {
        return blocks[index];
    }

    public void setBlock(int index, DoubleMatrix modified)
    {
        blocks[index] = modified;
    }

    public List<DoubleMatrix> getBlocks(int start, int end)
    {
        return Arrays.asList(blocks).subList(start, end);
    }

    public int getNumOfBlocks()
    {
        return blocks.length;
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
