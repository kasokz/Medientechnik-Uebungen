package Blatt3.HuffmanTree;

import Blatt3.CodeWord;

import java.util.List;

/**
 * Created by Long Bui on 01.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public abstract class HuffmanTreeComponent implements Comparable<HuffmanTreeComponent>
{
    public abstract double getFrequency();

    public abstract void setLeft(HuffmanTreeComponent newLeft);

    public abstract void setRight(HuffmanTreeComponent newRight);

    public abstract HuffmanTreeComponent getLeft();

    public abstract HuffmanTreeComponent getRight();

    public abstract int getDepth(int currentDepth);

    public abstract void fillCodeBook(List<CodeWord> codeWords, int currentCode, int currentLength);

    public abstract void printCode(String currentCode);

    public int compareTo(HuffmanTreeComponent o)
    {
        return Double.valueOf(this.getFrequency()).compareTo(o.getFrequency());
    }
}
