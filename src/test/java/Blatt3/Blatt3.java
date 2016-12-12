package Blatt3;

import Blatt3.DHT.DHTWriter;
import Blatt3.HuffmanTree.HuffmanTree;
import Blatt3.HuffmanTree.HuffmanTreeComponent;
import jpegencoder.jpeg.streams.BitOutputStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Long Bui on 29.11.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class Blatt3
{
    private HuffmanEncoder huffmanEncoder;
    private HuffmanTree huffmanTree;
    private int[] symbols = {1, 1, 1, 1,
                             2, 2, 2, 2,
                             3, 3, 3, 3, 3, 3,
                             4, 4, 4, 4, 4, 4,
                             5, 5, 5, 5, 5, 5, 5,
                             6, 6, 6, 6, 6, 6, 6, 6, 6};

    @Before
    public void initTests()
    {
        huffmanEncoder = new HuffmanEncoder();
        huffmanTree = huffmanEncoder.createHuffmanTree(huffmanEncoder.huffmanInit(symbols));
    }

    @Test
    public void testHuffmanInit()
    {
        huffmanEncoder = new HuffmanEncoder();
        int[] symbols = {1, 1, 1, 1,
                         2, 2, 2, 2,
                         3, 3, 3, 3, 3, 3,
                         4, 4, 4, 4, 4, 4,
                         5, 5, 5, 5, 5, 5, 5,
                         6, 6, 6, 6, 6, 6, 6, 6, 6};
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
        huffmanTree.printCodes();
    }

    @Test
    public void testHuffmanTreeCanonical()
    {
        huffmanTree.makeCanonical();
        huffmanTree.printCodes();
    }

    @Test
    public void testHuffmanTreeFullBitcode()
    {
        huffmanTree.makeCanonical();
        huffmanTree.replaceMostRight();
        huffmanTree.printCodes();
    }

    @Test
    public void testRestrictionValidationLowerThanMinHeight()
    {
        huffmanTree.makeCanonical();
        huffmanTree.replaceMostRight();
        Assert.assertEquals(false, huffmanTree.validateRestriction(2));
    }

    @Test
    public void testRestrictionValidationEqualsCurrentHeight()
    {
        huffmanTree.makeCanonical();
        huffmanTree.replaceMostRight();
        Assert.assertEquals(false, huffmanTree.validateRestriction(4));
    }

    @Test
    public void testRestrictionValidationHigherThanCurrentHeight()
    {
        huffmanTree.makeCanonical();
        huffmanTree.replaceMostRight();
        Assert.assertEquals(false, huffmanTree.validateRestriction(5));
    }

    @Test
    public void testRestrictionValidationValidRestriction()
    {
        huffmanTree.makeCanonical();
        huffmanTree.replaceMostRight();
        Assert.assertEquals(true, huffmanTree.validateRestriction(3));
    }

    @Test
    public void testCodeBook()
    {
        huffmanTree.makeCanonical();
        huffmanTree.replaceMostRight();
        for (CodeWord code : huffmanTree.getCodeBook())
        {
            System.out.println(code.toString());
        }
    }

    @Test
    public void testCodeBookWithLengthRestriction()
    {
        huffmanTree.makeCanonical();
        huffmanTree.replaceMostRight();
        huffmanTree.restrictToLength(3);
        for (CodeWord codeWord : huffmanTree.getCodeBook())
        {
            Assert.assertNotEquals(4, codeWord.getLength());
        }
    }

    @Test
    public void testDHTWriter() throws IOException
    {
        DHTWriter dhtWriter = new DHTWriter(new BitOutputStream(new FileOutputStream("testImage.jpg")));
        huffmanTree.makeCanonical();
        huffmanTree.replaceMostRight();
        huffmanTree.restrictToLength(16);
        dhtWriter.setCodeBook(huffmanTree.getCodeBook());
        dhtWriter.writeSegment();
    }
}
