package jpegencoder.encoding.acdc;

import jpegencoder.encoding.Util;
import jpegencoder.encoding.huffman.CodeWord;
import jpegencoder.image.colors.ColorChannel;
import jpegencoder.streams.BitOutputStream;
import org.jblas.DoubleMatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Long Bui on 12.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class AcDcEncoder
{
    public static List<DCCategoryEncodedPair> getAllDCs(ColorChannel channel)
    {
        List<DCCategoryEncodedPair> result = new ArrayList<DCCategoryEncodedPair>(channel.getNumOfBlocks());
        for (int i = 0; i < channel.getNumOfBlocks(); i++)
        {
            result.add(calculateDifferenceDC(channel, i));
        }
        return result;
    }

    public static DCCategoryEncodedPair calculateDifferenceDC(ColorChannel channel, int i)
    {
        int result = (int) channel.getBlock(i).get(0);
        if (i != 0)
        {
            result = (int) channel.getBlock(i).get(0) - (int) channel.getBlock(i - 1).get(0);
        }
        return new DCCategoryEncodedPair(AbstractCategoryEncodedPair.calculateCategory(result),
                                         AbstractCategoryEncodedPair.encodeCategory(result));
    }

    public static List<ACCategoryEncodedPair> getAllACs(ColorChannel channel)
    {
        List<ACCategoryEncodedPair> result = new ArrayList<ACCategoryEncodedPair>();
        for (int i = 0; i < channel.getNumOfBlocks(); i++)
        {
            DoubleMatrix block = channel.getBlock(i);
            List<ACRunlengthEncodedPair> runlengthEncodedBlock = encodeRunlength(Util.zigzagSort(block));
            List<ACCategoryEncodedPair> categoryEncodedBlock = encodeCategoriesAC(runlengthEncodedBlock);
            result.addAll(categoryEncodedBlock);
        }
        return result;
    }

    public static List<ACRunlengthEncodedPair> encodeRunlength(int[] zigzaged)
    {
        List<ACRunlengthEncodedPair> resultList = new ArrayList<ACRunlengthEncodedPair>();
        // loop starts at index 1 because index 0 is DC
        int zeroCount = 0;
        for (int i = 1; i < zigzaged.length; i++)
        {
            if (zigzaged[i] != 0 || zeroCount == 15)
            {
                resultList.add(new ACRunlengthEncodedPair(zeroCount, zigzaged[i]));
                zeroCount = 0;
            }
            else
            {
                zeroCount++;
            }
        }
        // EOB
        if (zeroCount != 0 || resultList.get(resultList.size() - 1).getEntry() == 0)
        {
            while (resultList.size() != 0 && resultList.get(resultList.size() - 1).getEntry() == 0)
            {
                resultList.remove(resultList.size() - 1);
            }
            resultList.add(new ACRunlengthEncodedPair(0, 0));
        }
        return resultList;
    }

    public static List<ACCategoryEncodedPair> encodeCategoriesAC(List<ACRunlengthEncodedPair> acRunlengthEncodedPairs)
    {
        List<ACCategoryEncodedPair> resultList = new ArrayList<ACCategoryEncodedPair>();
        for (ACRunlengthEncodedPair acRunlengthEncodedPair : acRunlengthEncodedPairs)
        {
            int category = AbstractCategoryEncodedPair.calculateCategory(acRunlengthEncodedPair.getEntry());
            int pair = (acRunlengthEncodedPair.getZeroCount() << 4) +
                    category;
            int categoryEncoded = ACCategoryEncodedPair.encodeCategory(acRunlengthEncodedPair.getEntry());
            resultList.add(new ACCategoryEncodedPair(pair, categoryEncoded));
        }
        return resultList;
    }

    public static void writeACTable(BitOutputStream bos, List<ACCategoryEncodedPair> acEncoding,
                                    Map<Integer, CodeWord> codebook) throws IOException
    {
        for (ACCategoryEncodedPair ac : acEncoding)
        {
            CodeWord codeWord = codebook.get(ac.getPair());
            bos.writeBits(codeWord.getCode(), codeWord.getLength());
            bos.writeBits(ac.getEntryCategoryEncoded(), ac.getCategory());
        }
    }

    public static void writeDC(BitOutputStream bos, DCCategoryEncodedPair dc, Map<Integer, CodeWord> codebook)
            throws IOException
    {
        CodeWord codeWord = codebook.get(dc.getPair());
        bos.writeBits(codeWord.getCode(), codeWord.getLength());
        bos.writeBits(dc.getEntryCategoryEncoded(), dc.getPair());
    }
}
