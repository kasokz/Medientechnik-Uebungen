package Blatt3;

import Blatt1.colors.ycbcr.YCbCr;
import Blatt1.colors.ycbcr.YCbCrImage;

import java.util.*;

/**
 * Created by Long Bui on 29.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class HuffmanEncoder
{
    private Map<Integer, Integer> frequencies;

    public HuffmanEncoder()
    {
        frequencies = new HashMap<Integer, Integer>();
    }

    public List<HuffmanTreeNode> huffmanInit(int[] symbols)
    {
        int totalSymbols = symbols.length;
        for (int symbol : symbols)
        {
            if (frequencies.containsKey(symbol))
            {
                frequencies.put(symbol, frequencies.get(symbol) + 1);
            }
            else
            {
                frequencies.put(symbol, 1);
            }
        }
        List<HuffmanTreeNode> leafs = new ArrayList<HuffmanTreeNode>();
        for (Map.Entry<Integer, Integer> frequency : frequencies.entrySet())
        {
            leafs.add(new HuffmanTreeLeaf(frequency.getKey(), frequency.getValue() / (double) totalSymbols));
        }
        Collections.sort(leafs);
        return leafs;
    }

    public HuffmanTree createHuffmanTree(List<HuffmanTreeNode> nodes)
    {
        createHuffmanTreeNodes(nodes);
        return new HuffmanTree(nodes.get(0));
    }

    public void createHuffmanTreeNodes(List<HuffmanTreeNode> nodes)
    {
        if (nodes.size() > 1)
        {
            HuffmanTreeNode huffmanTreeNode = new HuffmanTreeNode(nodes.get(0), nodes.get(1));
            nodes.remove(0);
            nodes.remove(0);
            nodes.add(huffmanTreeNode);
            Collections.sort(nodes);
            createHuffmanTreeNodes(nodes);
        }
    }
}
