package Blatt3;

/**
 * Created by Long Bui on 29.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class HuffmanTreeLeaf extends HuffmanTreeNode
{
    private int symbol;
    private double frequency;

    public HuffmanTreeLeaf(int symbol, double frequency)
    {
        this.symbol = symbol;
        this.frequency = frequency;
    }

    public double getFrequency()
    {
        return frequency;
    }

    public String toString()
    {
        return String.valueOf(symbol);
    }

}
