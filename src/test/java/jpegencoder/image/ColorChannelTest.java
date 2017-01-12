package jpegencoder.image;

import jpegencoder.image.colors.ColorChannel;
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
        Assert.assertEquals(300, testChannel.getPixel(16, 16));
    }

    @Test
    public void testSettersWithGetters()
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                testChannel.setPixel(j, i, i + j);
            }
        }
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++)
            {
                Assert.assertEquals(i+j,testChannel.getPixel(j,i));
            }
        }
    }
}
