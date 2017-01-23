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
    private int widthInBlocks;
    private int heightInBlocks;

    public ColorChannel(int width, int height)
    {
        this.height = height;
        this.width = width;
        widthInBlocks = (int) Math.ceil(width / 8d);
        heightInBlocks = (int) Math.ceil(height / 8d);
        blocks = new DoubleMatrix[heightInBlocks * widthInBlocks];
        for (int i = 0; i < heightInBlocks * widthInBlocks; i++)
        {
            blocks[i] = DoubleMatrix.zeros(8, 8);
        }
    }

    public void setPixel(int x, int y, double value)
    {
        blocks[getPlainIndexOfBlock(x / 8, y / 8)].put(y % 8, x % 8, value);
    }

    public double getPixel(int x, int y)
    {
        return blocks[getPlainIndexOfBlock(x / 8, y / 8)].get(y % 8, x % 8);
    }

    public int getPlainIndexOfBlock(int x, int y)
    {
        return (x + (y * (width / 8)));
    }

    public DoubleMatrix getBlock(int x, int y)
    {
        return blocks[getPlainIndexOfBlock(x, y)];
    }

    public DoubleMatrix getBlock(int index) throws ArrayIndexOutOfBoundsException
    {
        return blocks[index];
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

    public int getWidthInBlocks()
    {
        return widthInBlocks;
    }

    public int getHeightInBlocks()
    {
        return heightInBlocks;
    }

    public void fillWithMockData()
    {
        for (int y = 0; y < getHeight(); y++)
        {
            for (int x = 0; x < getWidth(); x++)
            {
                int value = x % 256;
                setPixel(x, y, value);
            }
        }
    }
}
