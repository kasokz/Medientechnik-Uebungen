package jpegencoder.encoding.huffman;

import java.io.OutputStream;

/**
 * Created by Long Bui on 12.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class CodeWord implements Comparable<CodeWord>
{
    private int symbol;
    private int code;
    private int length;

    public CodeWord(int symbol, int code, int length)
    {
        this.symbol = symbol;
        this.code = code;
        this.length = length;
    }

    public int getSymbol()
    {
        return symbol;
    }

    public int getCode()
    {
        return code;
    }

    public int getLength()
    {
        return length;
    }

    public String toString()
    {
        String result = symbol + ": ";
        for (int i = length; i > 0; i--)
        {
            result += (code >> (i - 1)) & 0x01;
        }
        return result;
    }

    public int compareTo(CodeWord o)
    {
        int result = Integer.valueOf(this.getLength()).compareTo(o.getLength());
        if (result == 0)
        {
            result = Integer.valueOf(this.code).compareTo(o.getCode());
        }
        return result;
    }
}
