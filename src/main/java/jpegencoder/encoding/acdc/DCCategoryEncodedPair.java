package jpegencoder.encoding.acdc;

import jpegencoder.encoding.Util;

/**
 * Created by Long Bui on 16.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class DCCategoryEncodedPair extends AbstractCategoryEncodedPair
{
    public DCCategoryEncodedPair(int pair, int entryCategoryEncoded)
    {
        super(pair, entryCategoryEncoded);
    }

    @Override
    public String toString()
    {
        return getPair() + ", " +
                Util.getBitsAsString(getEntryCategoryEncoded(), getPair());
    }
}
