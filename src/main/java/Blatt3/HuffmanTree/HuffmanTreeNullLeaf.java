package Blatt3.HuffmanTree;

/**
 * Created by Long Bui on 10.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class HuffmanTreeNullLeaf extends HuffmanTreeLeaf
{

    public HuffmanTreeNullLeaf()
    {
        super(Integer.MIN_VALUE, 0.0);
    }

    public void printCode(String currentCode)
    {
    }

    public String toString()
    {
        return "null";
    }


}
