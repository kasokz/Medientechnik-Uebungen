package jpegencoder.encoding.acdc;

import jpegencoder.encoding.Util;

/**
 * Created by Long Bui on 12.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class ACCategoryEncodedPair extends AbstractCategoryEncodedPair
{
    public ACCategoryEncodedPair(int pair, int entryCategoryEncoded)
    {
        super(pair, entryCategoryEncoded);
    }

    public int getZeroCount()
    {
        return (getPair() >> 4) & 0xF;
    }

    public int getCategory()
    {
        return getPair() & 0xF;
    }

    public String toString()
    {
        return "(" + getZeroCount() + "," + getCategory() + "), " +
                ((getEntryCategoryEncoded() == 0 && getCategory() == 0) ? "" : Util.getBitsAsString(getEntryCategoryEncoded(),
                                                                                              getCategory()));
    }
}
