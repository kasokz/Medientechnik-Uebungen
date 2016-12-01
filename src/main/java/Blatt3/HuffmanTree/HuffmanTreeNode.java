package Blatt3.HuffmanTree;

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
        return left.getFrequency() + right.getFrequency();
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

    public void printCode(String currentCode)
    {
        left.printCode(currentCode + "0");
        if (right != null)
        {
            right.printCode(currentCode + "1");
        }
    }

    public String toString()
    {
        String result = "Node(";
        result += (left != null) ? left.toString() + "," : "null";
        result += (right != null) ? right.toString() : "null";
        return result + ")";
    }
}
