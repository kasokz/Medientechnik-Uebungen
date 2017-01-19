package jpegencoder;

import jpegencoder.encoding.Util;
import jpegencoder.encoding.acdc.ACCategoryEncodedPair;
import jpegencoder.encoding.acdc.ACRunlengthEncodedPair;
import jpegencoder.encoding.acdc.AcDcEncoder;
import jpegencoder.encoding.acdc.DCCategoryEncodedPair;
import jpegencoder.encoding.huffman.CodeWord;
import jpegencoder.image.colors.ColorChannels;
import jpegencoder.image.colors.rgb.RGBImage;
import jpegencoder.image.colors.ycbcr.YCbCrImage;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Long Bui on 19/01/2017.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class JpegEncoderFileTest
{
    YCbCrImage image;

    @Before
    public void initImage() throws FileNotFoundException
    {
        RGBImage rgbImage = RGBImage.RGBImageBuilder.from(new FileInputStream("test-pic.ppm")).build();
        image = ColorChannels.RGBToYCbCr(rgbImage);
    }

    @Test
    public void testBlockAfterDCT()
    {
        JpegEncoder.withImage(image).performDCT();
        for (int i = 10; i < 20; i++)
        {
            printBlock(i);
        }
    }

    @Test
    public void testBlocks()
    {
        for (int k = 0; k < image.getChannel1().getNumOfBlocks(); k++)
        {
            printBlock(k);
        }
    }

    @Test
    public void testBlockCountsAllChannels()
    {
        System.out.println(image.getChannel1().getNumOfBlocks());
        System.out.println(image.getChannel2().getNumOfBlocks());
        System.out.println(image.getChannel3().getNumOfBlocks());
    }

    @Test
    public void testBlockChannel2()
    {
        for (int i = 0; i < image.getChannel2().getBlock(0).getRows(); i++)
        {
            for (int j = 0; j < image.getChannel2().getBlock(0).getColumns(); j++)
            {
                System.out.print(Util.round(image.getChannel2().getBlock(0).get(i, j)) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printBlock(int k)
    {
        for (int i = 0; i < image.getChannel1().getBlock(k).getRows(); i++)
        {
            for (int j = 0; j < image.getChannel1().getBlock(k).getColumns(); j++)
            {
                System.out.print(Util.round(image.getChannel1().getBlock(k).get(i, j)) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Test
    public void testImageAfterDCT()
    {
        JpegEncoder jpegEncoder = JpegEncoder.withImage(image).performDCT();
        for (int k = 0; k < jpegEncoder.getImage().getChannel1().getNumOfBlocks(); k++)
        {
            printBlock(k);
        }
    }

    @Test
    public void testImageAfterDCTChannel2()
    {
        JpegEncoder jpegEncoder = JpegEncoder.withImage(image).performDCT();
        for (int i = 0; i < jpegEncoder.getImage().getChannel2().getBlock(0).getRows(); i++)
        {
            for (int j = 0; j < jpegEncoder.getImage().getChannel2().getBlock(0).getColumns(); j++)
            {
                System.out.print(Util.round(jpegEncoder.getImage().getChannel2().getBlock(0).get(i, j)) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Test
    public void testImageAfterQuantization()
    {
        JpegEncoder jpegEncoder = JpegEncoder.withImage(image).performDCT().performQuantization();
        for (int k = 0; k < jpegEncoder.getImage().getChannel1().getNumOfBlocks(); k++)
        {
            printBlock(k);
        }
    }

    @Test
    public void testImageAfterQuantizationChannel2()
    {
        JpegEncoder jpegEncoder = JpegEncoder.withImage(image).performDCT().performQuantization();
        for (int i = 0; i < jpegEncoder.getImage().getChannel2().getBlock(0).getRows(); i++)
        {
            for (int j = 0; j < jpegEncoder.getImage().getChannel2().getBlock(0).getColumns(); j++)
            {
                System.out.print(Util.round(jpegEncoder.getImage().getChannel2().getBlock(0).get(i, j)) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    @Test
    public void testImageRunlengthEncoding()
    {
        JpegEncoder jpegEncoder = JpegEncoder.withImage(image).performDCT().performQuantization();
        List<ACRunlengthEncodedPair> acRunlengthEncodedPairs = AcDcEncoder.encodeRunlength(Util.zigzagSort(jpegEncoder.getImage()
                                                                                                                      .getChannel1()
                                                                                                                      .getBlock(0)));
        for (ACRunlengthEncodedPair acRunlengthEncodedPair : acRunlengthEncodedPairs)
        {
            System.out.println(acRunlengthEncodedPair);
        }
    }

    @Test
    public void testImageRunlengthEncodingChannel2()
    {
        JpegEncoder jpegEncoder = JpegEncoder.withImage(image).performDCT().performQuantization();
        List<ACRunlengthEncodedPair> acRunlengthEncodedPairs =
                AcDcEncoder.encodeRunlength(Util.zigzagSort(jpegEncoder.getImage()
                                                                       .getChannel2()
                                                                       .getBlock(
                                                                               0)));
        for (ACRunlengthEncodedPair acRunlengthEncodedPair : acRunlengthEncodedPairs)
        {
            System.out.println(acRunlengthEncodedPair);
        }
    }

    @Test
    public void testImageAcCategoryEncoding()
    {
        JpegEncoder jpegEncoder = JpegEncoder.withImage(image).performDCT().performQuantization();
        List<ACRunlengthEncodedPair> acRunlengthEncodedPairs = AcDcEncoder.encodeRunlength(Util.zigzagSort(jpegEncoder.getImage()
                                                                                                                      .getChannel1()
                                                                                                                      .getBlock(
                                                                                                                              0)));
        for (ACCategoryEncodedPair acCategoryEncodedPair : AcDcEncoder.encodeCategoriesAC(acRunlengthEncodedPairs))
        {
            System.out.println(acCategoryEncodedPair);
        }
    }

    @Test
    public void testImageAcCategoryEncodingChannel2()
    {
        JpegEncoder jpegEncoder = JpegEncoder.withImage(image).performDCT().performQuantization();
        List<ACRunlengthEncodedPair> acRunlengthEncodedPairs = AcDcEncoder.encodeRunlength(Util.zigzagSort(jpegEncoder.getImage()
                                                                                                                      .getChannel2()
                                                                                                                      .getBlock(
                                                                                                                              0)));
        for (ACCategoryEncodedPair acCategoryEncodedPair : AcDcEncoder.encodeCategoriesAC(acRunlengthEncodedPairs))
        {
            System.out.println(acCategoryEncodedPair);
        }
    }

    @Test
    public void testImageAfterAcDcEncodingY()
    {
        JpegEncoder jpegEncoder = JpegEncoder.withImage(image)
                                             .performDCT()
                                             .performQuantization()
                                             .performAcDcEncoding();
        System.out.println("DCs:");
        for (DCCategoryEncodedPair dcCategoryEncodedPair : jpegEncoder.dcYValues)
        {
            System.out.println(dcCategoryEncodedPair.toString());
        }
        System.out.println();
        System.out.println("ACs:");
        for (ACCategoryEncodedPair acCategoryEncodedPair : jpegEncoder.acYValues)
        {
            System.out.println(acCategoryEncodedPair);
        }
    }

    @Test
    public void testImageAfterAcDcEncodingCbCr()
    {
        JpegEncoder jpegEncoder = JpegEncoder.withImage(image)
                                             .performDCT()
                                             .performQuantization()
                                             .performAcDcEncoding();
        System.out.println("DCs:");
        for (DCCategoryEncodedPair dcCategoryEncodedPair : jpegEncoder.dcCbValues)
        {
            System.out.println(dcCategoryEncodedPair.toString());
        }
        System.out.println();
        System.out.println("ACs:");
        for (ACCategoryEncodedPair acCategoryEncodedPair : jpegEncoder.acCbValues)
        {
            System.out.println(acCategoryEncodedPair);
        }
    }

    @Test
    public void testHuffmanTableAcY()
    {
        JpegEncoder jpegEncoder = JpegEncoder.withImage(image)
                                             .performDCT()
                                             .performQuantization()
                                             .performAcDcEncoding()
                                             .performHuffmanEncoding();
        for (Map.Entry<Integer, CodeWord> entry : jpegEncoder.acYCodeBook.entrySet())
        {
            System.out.println(entry.getValue().toString());
        }

    }

    @Test
    public void testHuffmanTableDcY()
    {
        List<CodeWord> codeWords = new ArrayList<CodeWord>(JpegEncoder.withImage(image)
                                                                      .convertToJpeg().dcYCodeBook.values());
        Collections.sort(codeWords);
        for (CodeWord codeWord : codeWords)
        {
            System.out.println(codeWord.toString());
        }

    }

    @Test
    public void testHuffmanTableAcCbCr()
    {
        List<CodeWord> codeWords = new ArrayList<CodeWord>(JpegEncoder.withImage(image)
                                                                      .convertToJpeg().acYCodeBook.values());
        Collections.sort(codeWords);
        for (CodeWord codeWord : codeWords)
        {
            System.out.println(codeWord.toString());
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

}
