package Blatt3.HuffmanTree;

/**
 * Created by Long Bui on 29.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class HuffmanTree
{
    private HuffmanTreeComponent root;

    public HuffmanTree(HuffmanTreeComponent root)
    {
        this.root = root;
    }

    public void replaceMostRight()
    {
        HuffmanTreeComponent currentNode = root;
        HuffmanTreeComponent previousNode = root;
        while (currentNode.getRight() != null)
        {
            previousNode = currentNode;
            currentNode = currentNode.getRight();
        }
        previousNode.setRight(new HuffmanTreeNode(currentNode, null));
    }

    public void printCodes()
    {
        root.printCode("");
    }

    public String toString()
    {
        return root.toString();
    }
}
