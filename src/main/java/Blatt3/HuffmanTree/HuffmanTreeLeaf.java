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

    public int getSymbol()
    {
        return this.symbol;
    }

    public HuffmanTreeComponent getLeft()
    {
        return null;
    }

    public HuffmanTreeComponent getRight()
    {
        return null;
    }

    public int getDepth(int currentDepth)
    {
        return currentDepth;
    }

    public void printCode(String currentCode)
    {
        System.out.println(symbol + ": " + currentCode);
    }

    public String toString()
    {
        return String.valueOf(symbol);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        HuffmanTreeLeaf that = (HuffmanTreeLeaf) o;

        return symbol == that.symbol;
    }

    @Override
    public int hashCode()
    {
        return symbol;
    }
}
