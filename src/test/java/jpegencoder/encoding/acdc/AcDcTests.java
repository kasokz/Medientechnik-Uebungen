package jpegencoder.encoding.acdc;

import jpegencoder.encoding.Util;
import jpegencoder.encoding.huffman.CodeWord;
import jpegencoder.streams.BitOutputStream;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Long Bui on 12.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class AcDcTests
{
    private int[] toTest = {127, 57, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 2, 0,
                            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, -30, 0, 0, 0, -31, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 895, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0};

    @Test
    public void testACEncode()
    {
        for (ACRunlengthEncodedPair coefficient : AcDcEncoder.encodeRunlength(toTest))
        {
            System.out.println(coefficient.toString());
        }
    }

    @Test
    public void testCategoryEncode()
    {
        for (ACCategoryEncodedPair acCategoryEncodedPair : AcDcEncoder.encodeCategoriesAC(AcDcEncoder.encodeRunlength(
                toTest)))
        {
            System.out.println(acCategoryEncodedPair.toString());
        }
    }

    /* Test Output:
        0000 0110 111001
        1111 0000
        0010 0010 11
        0100 0010 10
        1110 0101 00001
        0011 0101 00000
        1110 1010 1101111111
        0000 0000
        00
     */
    @Test
    public void testWriteACTable() throws IOException
    {
        List<ACCategoryEncodedPair> pairs = AcDcEncoder.encodeCategoriesAC(AcDcEncoder.encodeRunlength(toTest));
        Map<Integer, CodeWord> codebook = new HashMap<Integer, CodeWord>();
        for (ACCategoryEncodedPair pair : pairs)
        {
            codebook.put(pair.getPair(), new CodeWord(pair.getPair(), pair.getPair(), 8));
        }
        for (Map.Entry<Integer, CodeWord> entry : codebook.entrySet())
        {
            System.out.println(entry.getValue());
        }
        BitOutputStream bitOutputStream = new BitOutputStream(new FileOutputStream("acTest.dat"));
        AcDcEncoder.writeACCoefficients(bitOutputStream, pairs, codebook);
        bitOutputStream.close();
    }

    @Test
    public void testDC() throws IOException
    {
        Map<Integer, CodeWord> codebook = new HashMap<Integer, CodeWord>();
        for (int i = 0; i < 9; i++)
        {
            codebook.put(i + 1, new CodeWord(i, i, 8));
        }
        BitOutputStream bos = new BitOutputStream(new FileOutputStream("dcTest.dat"));
        AcDcEncoder.writeDCCoefficient(bos, new DCCategoryEncodedPair(9, 511), codebook);
        bos.close();
    }

    @Test
    public void testZigZag()
    {
        int expected = 0;
        int[] testMatrix = Util.zigzagSort(Util.testZigZag);
        for (int i = 0; i < testMatrix.length; i++)
        {
            Assert.assertEquals(expected++, testMatrix[i]);
        }
    }
}
