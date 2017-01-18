package jpegencoder.image;

import jpegencoder.image.colors.ColorChannel;
import org.jblas.DoubleMatrix;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Long Bui on 12/01/2017.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class ColorChannelTest
{
    ColorChannel testChannel;

    @Before
    public void init()
    {
        testChannel = new ColorChannel(32, 32);
    }

    @Test
    public void testInit()
    {
        Assert.assertEquals(32, testChannel.getHeight());
        Assert.assertEquals(32, testChannel.getWidth());
    }

    @Test
    public void testSetterWithGetter()
    {
        testChannel.setPixel(16, 16, 300);
        Assert.assertEquals(300, testChannel.getPixel(16, 16), 1);
    }

    @Test
    public void testSettersWithGetters()
    {
        int index = 0;
        for (int i = 0; i < 32; i++)
        {
            for (int j = 0; j < 32; j++)
            {
                testChannel.setPixel(j, i, index++);
            }
        }
        index = 0;
        for (int i = 0; i < 32; i++)
        {
            for (int j = 0; j < 32; j++)
            {
                Assert.assertEquals(index++, testChannel.getPixel(j, i), 1);
            }
        }
    }

    @Test
    public void testFirstBlock()
    {
        testSettersWithGetters();
        DoubleMatrix block = testChannel.getBlock(0);
        for (int i = 0; i < block.getRows(); i++)
        {
            int index = i * testChannel.getWidth();
            for (int j = 0; j < block.getColumns(); j++)
            {
                Assert.assertEquals(index++, block.get(i, j), 0.5);
            }
        }
    }

    @Test
    public void testBlockAccess()
    {
        int x = 0;
        for (int i = 0; i < testChannel.getHeight() / 8; i++)
        {
            for (int j = 0; j < testChannel.getWidth() / 8; j++)
            {
                Assert.assertEquals(x++, testChannel.getPlainIndexOfBlock(j, i));
            }
        }
    }
}
