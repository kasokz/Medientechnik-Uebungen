package jpegencoder.encoding;

import jpegencoder.encoding.acdc.ACCoefficient;
import jpegencoder.encoding.acdc.ACZeroCategoryPair;
import jpegencoder.streams.BitOutputStream;

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
    public static int encodeDc(int[] zigzagedCurrent, int[] zigzagedPrev)
    {
        return zigzagedCurrent[0] - zigzagedPrev[0];
    }

    // Weiterer Schritt wo anders, z.B. Hauptprogramm

    public static void writeDC(BitOutputStream bos, int deltaDc, Map<Integer, CodeWord> codebook) throws IOException
    {
        int category = (int) Math.round(Math.log(Math.abs(deltaDc)) /
                                                Math.log(2) + 0.5);
        CodeWord codeWord = codebook.get(category);
        bos.writeBits(codeWord.getCode(), codeWord.getLength());
        bos.writeBits(ACZeroCategoryPair.encodeCategory(deltaDc), category);
    }

    public static List<ACCoefficient> encodeAc(int[] zigzaged)
    {
        List<ACCoefficient> resultList = new ArrayList<ACCoefficient>();
        // loop starts at index 1 because index 0 is DC
        int zeroCount = 0;
        for (int i = 1; i < zigzaged.length; i++)
        {
            if (zigzaged[i] != 0 || zeroCount == 15)
            {
                resultList.add(new ACCoefficient(zeroCount, zigzaged[i]));
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
            while (resultList.get(resultList.size() - 1).getEntry() == 0)
            {
                resultList.remove(resultList.size() - 1);
            }
            resultList.add(new ACCoefficient(0, 0));
        }
        return resultList;
    }

    public static List<ACZeroCategoryPair> encodeCategories(List<ACCoefficient> acCoefficients)
    {
        List<ACZeroCategoryPair> resultList = new ArrayList<ACZeroCategoryPair>();
        for (ACCoefficient acCoefficient : acCoefficients)
        {
            int category = (int) Math.round(Math.log(Math.abs(acCoefficient.getEntry())) /
                                                    Math.log(2) + 0.5);
            int pair = (acCoefficient.getZeroCount() << 4) +
                    category;
            int categoryEncoded = ACZeroCategoryPair.encodeCategory(acCoefficient.getEntry());
            resultList.add(new ACZeroCategoryPair(pair, categoryEncoded));
        }
        return resultList;
    }

    // Weiterer Schritt wo anders, z.B. Hauptprogramm

    public static void writeACTable(BitOutputStream bos, List<ACZeroCategoryPair> acEncoding,
                                    Map<Integer, CodeWord> codebook) throws IOException
    {
        for (ACZeroCategoryPair pair : acEncoding)
        {
            CodeWord codeWord = codebook.get(pair.getPair());
            bos.writeBits(codeWord.getCode(), codeWord.getLength());
            bos.writeBits(pair.getEntryCategoryEncoded(), pair.getCategory());
        }
    }
}
