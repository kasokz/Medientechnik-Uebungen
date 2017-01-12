package jpegencoder.encoding.acdc;

/**
 * Created by Long Bui on 12.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class ACRuntimeEncodedPair
{
    private int zeroCount;
    private int entry;

    public ACRuntimeEncodedPair(int zeroCount, int entry)
    {
        this.zeroCount = zeroCount;
        this.entry = entry;
    }

    public int getZeroCount()
    {
        return zeroCount;
    }

    public int getEntry()
    {
        return entry;
    }

    public String toString()
    {
        return "(" + zeroCount + "," + entry + ")";
    }
}
