package Blatt3;

import Blatt3.huffman.HuffmanTree;
import jpegencoder.jpeg.streams.BitInputStream;
import jpegencoder.jpeg.streams.BitOutputStream;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Long Bui on 13.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class EncodeDecodeTest
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
    public void encodeAndDecodeTest() throws IOException
    {
        BitOutputStream bitOutputStream = new BitOutputStream(new FileOutputStream("huffmanTest.dat"));
        BitInputStream bitInputStream = new BitInputStream(new FileInputStream("huffmanTest.dat"));
        Map<Integer, CodeWord> codes = huffmanTree.getCodeBookAsMap();
        int[] sentence = {3, 4, 1, 2, 2, 6, 3, 4};
        for (int i = 0; i < sentence.length; i++)
        {
            CodeWord codeWord = codes.get(sentence[i]);
            for (int j = codeWord.getLength() - 1; j >= 0; j--)
            {
                bitOutputStream.write((codeWord.getCode() >> j) & 0x01);
            }
        }
        bitOutputStream.close();
        int read;
        int readSymbols = 0;
        int readCode = 0;
        int readCounter = 0;
        while ((read = bitInputStream.read()) != -1)
        {
            readCode = (readCode << 1) + read;
            readCounter++;
            for (CodeWord codeWord : huffmanTree.getCodeBook())
            {
                if (codeWord.getCode() == readCode && readCounter == codeWord.getLength())
                {
                    Assert.assertEquals(sentence[readSymbols++], codeWord.getSymbol());
                    readCode = 0;
                    readCounter = 0;
                    break;
                }
            }
        }
        bitInputStream.close();
    }
}
