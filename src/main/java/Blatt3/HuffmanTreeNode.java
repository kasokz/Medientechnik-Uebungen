package Blatt3;

/**
 * Created by Long Bui on 29.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class HuffmanTreeNode implements Comparable<HuffmanTreeNode>
{
    private HuffmanTreeNode left;
    private HuffmanTreeNode right;

    public HuffmanTreeNode()
    {
        left = null;
        right = null;
    }

    public HuffmanTreeNode(HuffmanTreeNode left, HuffmanTreeNode right)
    {
        this.left = left;
        this.right = right;
    }

    public double getFrequency()
    {
        return left.getFrequency() + right.getFrequency();
    }

    public int compareTo(HuffmanTreeNode o)
    {
        return Double.valueOf(this.getFrequency()).compareTo(o.getFrequency());
    }

    public String toString()
    {
        String result = "Node(";
        if (left != null)
        {
            result += left.toString();
        }
        if (right != null)
        {
            result += right.toString();
        }
        return result + ")";
    }
}
