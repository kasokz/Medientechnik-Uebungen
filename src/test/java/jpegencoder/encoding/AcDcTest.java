package jpegencoder.encoding;

import jpegencoder.encoding.acdc.ACCoefficient;
import jpegencoder.encoding.acdc.ACZeroCategoryPair;
import jpegencoder.streams.BitOutputStream;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Long Bui on 12.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class AcDcTest
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
        for (ACCoefficient coefficient : AcDcEncoder.encodeAc(toTest))
        {
            System.out.println(coefficient.toString());
        }
    }

    @Test
    public void testCategoryEncode()
    {
        for (ACZeroCategoryPair acZeroCategoryPair : AcDcEncoder.encodeCategories(AcDcEncoder.encodeAc(toTest)))
        {
            System.out.println(acZeroCategoryPair.toString());
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
        List<ACZeroCategoryPair> pairs = AcDcEncoder.encodeCategories(AcDcEncoder.encodeAc(toTest));
        Map<Integer, CodeWord> codebook = new HashMap<Integer, CodeWord>();
        for (ACZeroCategoryPair pair : pairs)
        {
            codebook.put(pair.getPair(), new CodeWord(pair.getPair(), pair.getPair(), 8));
        }
        for (Map.Entry<Integer, CodeWord> entry : codebook.entrySet())
        {
            System.out.println(entry.getValue());
        }
        BitOutputStream bitOutputStream = new BitOutputStream(new FileOutputStream("acTest.dat"));
        AcDcEncoder.writeACTable(bitOutputStream, pairs, codebook);
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
        AcDcEncoder.writeDC(bos, 511, codebook);
        bos.close();
    }
}
