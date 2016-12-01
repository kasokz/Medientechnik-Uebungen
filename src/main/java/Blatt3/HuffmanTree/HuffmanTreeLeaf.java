package Blatt3.HuffmanTree;

/**
 * Created by Long Bui on 29.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class HuffmanTreeLeaf extends HuffmanTreeComponent
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

    public void setLeft(HuffmanTreeComponent newLeft)
    {
    }

    public void setRight(HuffmanTreeComponent newRight)
    {
    }

    public HuffmanTreeComponent getLeft()
    {
        return null;
    }

    public HuffmanTreeComponent getRight()
    {
        return null;
    }

    public String toString()
    {
        return String.valueOf(symbol);
    }

}
