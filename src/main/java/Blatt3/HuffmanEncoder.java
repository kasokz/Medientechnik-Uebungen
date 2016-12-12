package Blatt3;

import Blatt3.HuffmanTree.HuffmanTree;
import Blatt3.HuffmanTree.HuffmanTreeComponent;
import Blatt3.HuffmanTree.HuffmanTreeLeaf;
import Blatt3.HuffmanTree.HuffmanTreeNode;

import java.util.*;

/**
 * Created by Long Bui on 29.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */

//Erstelle Huffman Tree von Liste aus Symbolen, Teilaufgabe a

public class HuffmanEncoder
{
    private Map<Integer, Integer> frequencies;

    public HuffmanEncoder()
    {
        frequencies = new HashMap<Integer, Integer>();
    }

    List<HuffmanTreeComponent> huffmanInit(int[] symbols)
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
        List<HuffmanTreeComponent> leafs = new ArrayList<HuffmanTreeComponent>();
        for (Map.Entry<Integer, Integer> frequency : frequencies.entrySet())
        {
            leafs.add(new HuffmanTreeLeaf(frequency.getKey(), frequency.getValue() / (double) totalSymbols));
        }
        Collections.sort(leafs);
        return leafs;
    }

    //Baum aufspannen
    public HuffmanTree createHuffmanTree(List<HuffmanTreeComponent> nodes)
    {
        List<HuffmanTreeLeaf> symbols = new ArrayList<HuffmanTreeLeaf>();
        for(HuffmanTreeComponent node: nodes)
        {
            symbols.add((HuffmanTreeLeaf) node);
        }
        createHuffmanTreeNodes(nodes);
        return new HuffmanTree(nodes.get(0), symbols);
    }

    private void createHuffmanTreeNodes(List<HuffmanTreeComponent> nodes)
    {
        if (nodes.size() > 1)
        {
            HuffmanTreeNode huffmanTreeNode = new HuffmanTreeNode(nodes.get(1), nodes.get(0));
            nodes.remove(0);
            nodes.remove(0);
            nodes.add(huffmanTreeNode);
            Collections.sort(nodes);
            createHuffmanTreeNodes(nodes);
        }
    }
}
