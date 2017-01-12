package jpegencoder.encoding.acdc;

/**
 * Created by Long Bui on 12.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class ACZeroCategoryPair
{
    private int pair;
    private int entryCategoryEncoded;

    public ACZeroCategoryPair(int pair, int entryCategoryEncoded)
    {
        this.pair = pair;
        this.entryCategoryEncoded = entryCategoryEncoded;
    }

    public int getZeroCount()
    {
        return (pair >> 4) & 0xF;
    }

    public int getCategory()
    {
        return pair & 0xF;
    }

    public int getPair()
    {
        return pair;
    }

    public int getEntryCategoryEncoded()
    {
        return entryCategoryEncoded;
    }

    public void setEntryCategoryEncoded(int entryCategoryEncoded)
    {
        this.entryCategoryEncoded = entryCategoryEncoded;
    }

    public String toString()
    {
        return "(" + (pair >> 4) + "," + (pair & 0xf) + "), " +
                ((entryCategoryEncoded == Integer.MIN_VALUE) ? "" : entryCategoryEncoded);
    }

    public static int encodeCategory(int toEncode)
    {
        int result;
        int category = (int) Math.round(Math.log(Math.abs(toEncode)) /
                                                Math.log(2) + 0.5);
        if (toEncode < 0)
        {
            int maxValueCategory = (int) (Math.pow(2, category) - 1);
            result = Math.abs(toEncode) ^ maxValueCategory;
        }
        else if (toEncode > 0)
        {
            result = toEncode;
        }
        else
        {
            result = Integer.MIN_VALUE;
        }
        return result;
    }
}
