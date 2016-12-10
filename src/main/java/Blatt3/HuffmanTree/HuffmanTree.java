package Blatt3.HuffmanTree;

import java.util.*;

/**
 * Created by Long Bui on 29.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class HuffmanTree
{
    private HuffmanTreeComponent root;
    private List<HuffmanTreeLeaf> symbols;
    private boolean fullBitEliminated = false;

    public HuffmanTree(HuffmanTreeComponent root, List<HuffmanTreeLeaf> symbols)
    {
        this.root = root;
        this.symbols = symbols;
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
        if (!fullBitEliminated)
        {
            HuffmanTreeComponent currentNode = root;
            HuffmanTreeComponent previousNode = root;
            while (currentNode.getRight() != null)
            {
                previousNode = currentNode;
                currentNode = currentNode.getRight();
            }
            previousNode.setRight(new HuffmanTreeNode(currentNode, new HuffmanTreeNullLeaf()));
            symbols.add(new HuffmanTreeNullLeaf());
        }
    }

    public void restrictToLength(int restriction)
    {
        if (validateRestriction(restriction))
        {
            Map<Integer, List<HuffmanTreeComponent>> coinDrawers = initCoinDrawers(restriction);
            packageMerge(restriction, coinDrawers);
            Map<HuffmanTreeLeaf, Integer> codeWordLengths = evaluate(restriction, coinDrawers);
            createLengthLimitedTree(codeWordLengths, restriction);
        }
    }

    private void createLengthLimitedTree(Map<HuffmanTreeLeaf, Integer> codeWordLengths, int restriction)
    {
        Map<Integer, List<HuffmanTreeComponent>> tree = new HashMap<Integer, List<HuffmanTreeComponent>>();
        for (int i = 0; i <= restriction; i++)
        {
            tree.put(i, new ArrayList<HuffmanTreeComponent>());
        }
        for (Map.Entry<HuffmanTreeLeaf, Integer> entry : codeWordLengths.entrySet())
        {
            tree.get(entry.getValue()).add(entry.getKey());
        }
        for (int i = restriction; i > 0; i--)
        {
            List<HuffmanTreeComponent> currentLevel = tree.get(i);
            List<HuffmanTreeComponent> nextLevel = tree.get(i - 1);
            Collections.sort(currentLevel, new DepthComparator());
            for (int j = currentLevel.size() - 1; j > 0; j = j - 2)
            {
                HuffmanTreeComponent newNode = new HuffmanTreeNode(currentLevel.get(j - 1), currentLevel.get(j));
                nextLevel.add(newNode);
            }
        }
        this.root = tree.get(0).get(0);
    }

    private Map<HuffmanTreeLeaf, Integer> evaluate(int restriction,
                                                   Map<Integer, List<HuffmanTreeComponent>> coinDrawers)
    {
        Map<HuffmanTreeLeaf, Integer> codeWordLengths = new HashMap<HuffmanTreeLeaf, Integer>();
        for (int denominationPower = -restriction; denominationPower < 0; denominationPower++)
        {
            List<HuffmanTreeComponent> currentDrawer = coinDrawers.get(denominationPower);
            for (HuffmanTreeComponent coin : currentDrawer)
            {
                if (coin instanceof HuffmanTreeLeaf)
                {
                    HuffmanTreeLeaf symbol = (HuffmanTreeLeaf) coin;
                    if (codeWordLengths.containsKey(symbol))
                    {
                        codeWordLengths.put(symbol, codeWordLengths.get(symbol) + 1);
                    }
                    else
                    {
                        codeWordLengths.put(symbol, 1);
                    }
                }
            }
        }
        return codeWordLengths;
    }

    private void packageMerge(int restriction, Map<Integer, List<HuffmanTreeComponent>> coinDrawers)
    {
        for (int denominationPower = -restriction; denominationPower < 0; denominationPower++)
        {
            List<HuffmanTreeComponent> currentDrawer = coinDrawers.get(denominationPower);
            Collections.sort(currentDrawer);
            for (int i = 0; i < currentDrawer.size() / 2; i++)
            {
                coinDrawers.get(denominationPower + 1)
                           .add(new HuffmanTreeNode(currentDrawer.get(i + i), currentDrawer.get(i + i + 1)));
            }
            if ((currentDrawer.size() % 2) != 0)
            {
                removeNodeAndItsChildren(coinDrawers, currentDrawer, denominationPower);
            }
        }
    }

    private void removeNodeAndItsChildren(Map<Integer, List<HuffmanTreeComponent>> coinDrawers,
                                          List<HuffmanTreeComponent> currentDrawer,
                                          int currentDenominationPower)
    {
        // lösche Kinder rekursiv
        if (!(currentDrawer.get(currentDrawer.size() - 1) instanceof HuffmanTreeLeaf))
        {
            List<HuffmanTreeComponent> previousDrawer = coinDrawers.get(currentDenominationPower - 1);
            removeNodeAndItsChildren(coinDrawers, previousDrawer, currentDenominationPower - 1);
            removeNodeAndItsChildren(coinDrawers, previousDrawer, currentDenominationPower - 1);
        }
        // lösche aktueller Knoten
        currentDrawer.remove(currentDrawer.size() - 1);
    }

    private Map<Integer, List<HuffmanTreeComponent>> initCoinDrawers(int restriction)
    {
        Map<Integer, List<HuffmanTreeComponent>> coinDrawer = new HashMap<Integer, List<HuffmanTreeComponent>>();
        coinDrawer.put(0, new ArrayList<HuffmanTreeComponent>());
        for (int i = -restriction; i < 0; i++)
        {
            coinDrawer.put(i, new ArrayList<HuffmanTreeComponent>());
            for (HuffmanTreeLeaf leaf : symbols)
            {
                coinDrawer.get(i).add(leaf);
            }
        }
        return coinDrawer;
    }

    public boolean validateRestriction(int restriction)
    {
        if (this.getDepth() <= restriction)
        {
            throw new IllegalArgumentException("Baum ist bereits auf gewünschter Höhe oder besser!");
        }
        if (Math.ceil(Math.log(getNumOfSymbols()) / Math.log(2)) > restriction)
        {
            throw new IllegalArgumentException("Restriktion unmöglich!");
        }
        return true;
    }

    public int getNumOfSymbols()
    {
        return this.symbols.size();
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
