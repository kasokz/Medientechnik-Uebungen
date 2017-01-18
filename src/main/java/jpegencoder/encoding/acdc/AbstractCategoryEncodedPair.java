package jpegencoder.encoding.acdc;

/**
 * Created by Long Bui on 16.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public abstract class AbstractCategoryEncodedPair
{
    private int pair;
    private int entryCategoryEncoded;

    public AbstractCategoryEncodedPair(int pair, int entryCategoryEncoded)
    {
        this.pair = pair;
        this.entryCategoryEncoded = entryCategoryEncoded;
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

    public static int calculateCategory(int number)
    {
        int category = (int) Math.round(Math.log(Math.abs(number)) /
                                                Math.log(2) + 0.5);
        return category;
    }

    public static int encodeCategory(int toEncode)
    {
        int result;
        int category = calculateCategory(toEncode);
        if (toEncode < 0)
        {
            int maxValueCategory = (int) (Math.pow(2, category) - 1);
            result = Math.abs(toEncode) ^ maxValueCategory;
        }
        else
        {
            result = toEncode;
        }
        return result;
    }

    public String toString()
    {
        return pair + ", " + entryCategoryEncoded;
    }
}
