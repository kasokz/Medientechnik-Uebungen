package Blatt3;

import Blatt3.HuffmanTree.HuffmanTree;
import Blatt3.HuffmanTree.HuffmanTreeComponent;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Long Bui on 29.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class Blatt3
{
    private HuffmanEncoder huffmanEncoder = new HuffmanEncoder();
    private int[] symbols = {1, 1, 1, 1,
                             2, 2, 2, 2,
                             3, 3, 3, 3, 3, 3,
                             4, 4, 4, 4, 4, 4,
                             5, 5, 5, 5, 5, 5, 5,
                             6, 6, 6, 6, 6, 6, 6, 6, 6};

    @Test
    public void testHuffmanInit()
    {
        huffmanEncoder = new HuffmanEncoder();
        double[] expectedValues = {0.1111, 0.1111, 0.1666, 0.1666, 0.1944, 0.25};
        double errorMargin = 0.001;
        List<HuffmanTreeComponent> frequencies = huffmanEncoder.huffmanInit(symbols);
        int i = 0;
        for (HuffmanTreeComponent frequency : frequencies)
        {
            Assert.assertEquals(expectedValues[i++], frequency.getFrequency(), errorMargin);
        }
    }

    @Test
    public void testHuffmanTree()
    {
        HuffmanTree huffmanTree = huffmanEncoder.createHuffmanTree(huffmanEncoder.huffmanInit(symbols));
        huffmanTree.printCodes();
    }

    @Test
    public void testHuffmanTreeCanonical()
    {
        HuffmanTree huffmanTree = huffmanEncoder.createHuffmanTree(huffmanEncoder.huffmanInit(symbols));
        huffmanTree.makeCanonical();
        huffmanTree.printCodes();
    }

    @Test
    public void testHuffmanTreeFullBitcode()
    {
        HuffmanTree huffmanTree = huffmanEncoder.createHuffmanTree(huffmanEncoder.huffmanInit(symbols));
        huffmanTree.makeCanonical();
        huffmanTree.replaceMostRight();
        huffmanTree.printCodes();
    }
}
