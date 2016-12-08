package Blatt3.HuffmanTree;

import java.util.*;

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

    public void makeCanonical()
    {
        Map<Integer, List<HuffmanTreeComponent>> tree = new HashMap<Integer, List<HuffmanTreeComponent>>();
        layerTreeAndSortNodesByDepth(tree);
        recompositeTree(tree);
    }

    private void layerTreeAndSortNodesByDepth(Map<Integer, List<HuffmanTreeComponent>> tree)
    {
        List<HuffmanTreeComponent> nodesOfCurrentLevel = new ArrayList<HuffmanTreeComponent>();
        nodesOfCurrentLevel.add(root);
        for (int i = 0; i <= this.getDepth(); i++)
        {
            List<HuffmanTreeComponent> nodesOfNextLevel = new ArrayList<HuffmanTreeComponent>();
            tree.put(i, new ArrayList<HuffmanTreeComponent>());
            for (HuffmanTreeComponent currentNode : nodesOfCurrentLevel)
            {
                tree.get(i).add(currentNode);
                if (currentNode.getLeft() != null)
                {
                    nodesOfNextLevel.add(currentNode.getLeft());
                }
                if (currentNode.getRight() != null)
                {
                    nodesOfNextLevel.add(currentNode.getRight());
                }
            }
            Collections.sort(nodesOfNextLevel, new DepthComparator());
            nodesOfCurrentLevel = nodesOfNextLevel;
        }
    }

    private void recompositeTree(Map<Integer, List<HuffmanTreeComponent>> tree)
    {
        List<HuffmanTreeComponent> nodesOfCurrentLevel;
        for (int i = tree.size() - 1; i > 0; i--)
        {
            nodesOfCurrentLevel = tree.get(i);
            List<HuffmanTreeComponent> nodesOfPreviousLevel = tree.get(i - 1);
            int finishedNodes = 0;
            for (int j = nodesOfCurrentLevel.size() - 1; j > 0; j = j - 2)
            {
                HuffmanTreeComponent lastUnusedNode =
                        nodesOfPreviousLevel.get(nodesOfPreviousLevel.size() - finishedNodes++ - 1);
                lastUnusedNode.setLeft(nodesOfCurrentLevel.get(j - 1));
                lastUnusedNode.setRight(nodesOfCurrentLevel.get(j));
            }
        }
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

    public int getDepth()
    {
        return root.getDepth(0);
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
