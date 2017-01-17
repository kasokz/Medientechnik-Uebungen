package jpegencoder;

import jpegencoder.encoding.huffman.CodeWord;
import jpegencoder.image.Image;
import jpegencoder.image.colors.ColorChannel;
import org.jblas.DoubleMatrix;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Long Bui on 17.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class JpegEncoderTest
{
    Image image;

    @Before
    public void initImage()
    {
        ColorChannel channel1 = new ColorChannel(256, 256);
        ColorChannel channel2 = new ColorChannel(256, 256);
        ColorChannel channel3 = new ColorChannel(256, 256);
        fillPicture(channel1);
        fillPicture(channel2);
        fillPicture(channel3);
        image = new Image(channel1, channel2, channel3)
        {
        };
    }

    @Test
    public void testImage()
    {
        print();
    }

    @Test
    public void testBlocks()
    {
        for (int i = 0; i < image.getChannel1().getBlock(0).getRows(); i++)
        {
            for (int j = 0; j < image.getChannel1().getBlock(0).getColumns(); j++)
            {
                System.out.print(image.getChannel1().getBlock(0).get(i, j) + " ");
            }
            System.out.println();
        }
    }

    @Test
    public void testHuffmanTableAcY()
    {
        for (Map.Entry<Integer, CodeWord> entry : JpegEncoder.withImage(image).convertToJpeg().acYCodeBook.entrySet())
        {
            System.out.println(entry.getValue().toString());
        }

    }

    @Test
    public void testHuffmanTableDcY()
    {
        for (Map.Entry<Integer, CodeWord> entry : JpegEncoder.withImage(image).convertToJpeg().dcYCodeBook.entrySet())
        {
            System.out.println(entry.getValue().toString());
        }

    }

    @Test
    public void testHuffmanTableAcCbCr()
    {
        for (Map.Entry<Integer, CodeWord> entry : JpegEncoder.withImage(image)
                                                             .convertToJpeg().acCbCrCodeBook.entrySet())
        {
            System.out.println(entry.getValue().toString());
        }

    }

    @Test
    public void testHuffmanTableDcCbCr()
    {
        for (Map.Entry<Integer, CodeWord> entry : JpegEncoder.withImage(image)
                                                             .convertToJpeg().dcCbCrCodeBook.entrySet())
        {
            System.out.println(entry.getValue().toString());
        }

    }

    @Test
    public void testJpegConversion()
    {
        JpegEncoder.withImage(image).convertToJpeg().writeImageToDisk();
    }

    private void fillPicture(ColorChannel colorChannel)
    {
        for (int y = 0; y < colorChannel.getHeight(); y++)
        {
            for (int x = 0; x < colorChannel.getWidth(); x++)
            {
                int value;
                value = (x + (y * 8)) % 256;
                colorChannel.setPixel(x, y, value);
            }
        }
    }

    public void print()
    {
        for (int row = 0; row < image.getChannel1().getHeight(); row++)
        {
            for (int col = 0; col < image.getChannel1().getWidth(); col++)
            {
                System.out.print(image.getChannel1().getPixel(col, row) + " ");
            }
            System.out.println();
        }
    }

}

