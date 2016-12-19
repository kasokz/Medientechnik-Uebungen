package jpegencoder.encoding.huffman;

import jpegencoder.encoding.CodeWord;

import java.util.List;

/**
 * Created by Long Bui on 29.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class HuffmanTreeNode extends HuffmanTreeComponent
{
    private HuffmanTreeComponent left;
    private HuffmanTreeComponent right;

    public HuffmanTreeNode()
    {
        left = null;
        right = null;
    }

    public HuffmanTreeNode(HuffmanTreeComponent left, HuffmanTreeComponent right)
    {
        this.left = left;
        this.right = right;
    }

    public double getFrequency()
    {
        return left.getFrequency() +
                right.getFrequency();
    }

    public void setLeft(HuffmanTreeComponent newLeft)
    {
        this.left = newLeft;
    }

    public void setRight(HuffmanTreeComponent newRight)
    {
        this.right = newRight;
    }

    public HuffmanTreeComponent getLeft()
    {
        return left;
    }

    public HuffmanTreeComponent getRight()
    {
        return right;
    }

    public int getDepth(int currentDepth)
    {
        return Math.max(left.getDepth(currentDepth + 1),
                        (right.getDepth(currentDepth + 1)));
    }

    public void fillCodeBook(List<CodeWord> codeWords, int currentCode, int currentLength)
    {
        left.fillCodeBook(codeWords, (currentCode << 1) + 0, currentLength + 1);
        right.fillCodeBook(codeWords, (currentCode << 1) + 1, currentLength + 1);
    }

    public void printCode(String currentCode)
    {
        left.printCode(currentCode + "0");
        right.printCode(currentCode + "1");
    }

    public String toString()
    {
        String result = "Node(";
        result += left.toString() + ",";
        result += right.toString();
        return result + ")";
    }
}
