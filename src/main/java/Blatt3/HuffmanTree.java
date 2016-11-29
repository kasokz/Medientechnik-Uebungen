package Blatt3;

/**
 * Created by Long Bui on 29.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class HuffmanTree
{
    private HuffmanTreeNode root;

    public HuffmanTree(HuffmanTreeNode root)
    {
        this.root = root;
    }

    public String toString()
    {
        return root.toString();
    }
}
