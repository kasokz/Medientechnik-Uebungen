package Blatt3.DHT;

import Blatt3.CodeWord;
import jpegencoder.jpeg.SegmentWriter;
import jpegencoder.jpeg.streams.BitOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Long Bui on 12.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class DHTWriter extends SegmentWriter
{
    public static int DHTMARKER = 0xC4;

    private Map<Integer, Integer> codeWordLengthMap = new HashMap<Integer, Integer>();
    private List<CodeWord> codeBook;
    private int tableClass = 0;
    private int huffmanTableIdentifier = 0;
    private int numOfTables = 1;

    public DHTWriter(OutputStream os)
    {
        super(os);
    }

    public int getLength()
    {
        int sum = 0;
        for (int i = 0; i < numOfTables; i++)
        {
            sum += (17 + codeBook.size());
        }
        return 2 + sum;
    }

    public void setNumOfTables(int numOfTables)
    {
        this.numOfTables = numOfTables;
    }

    public void setCodeBook(List<CodeWord> codeBook)
    {
        this.codeBook = codeBook;
        setCodeWordLengthMap();
    }

    public Map<Integer, Integer> getCodeWordLengthMap()
    {
        return codeWordLengthMap;
    }

    private void setCodeWordLengthMap()
    {
        for (int i = 1; i <= 16; i++)
        {
            codeWordLengthMap.put(i, 0);
        }
        for (CodeWord codeWord : codeBook)
        {
            int codeWordLength = codeWord.getLength();
            codeWordLengthMap.put(codeWordLength, codeWordLengthMap.get(codeWordLength) + 1);
        }
    }

    public void setHuffmanTableIdentifier(int huffmanTableIdentifier)
    {
        this.huffmanTableIdentifier = huffmanTableIdentifier;
    }

    public void setTableClass(int tableClass)
    {
        this.tableClass = tableClass;
    }

    public void writeSegment() throws IOException
    {
        BitOutputStream.writeByte(os, 0xFF);
        BitOutputStream.writeByte(os, DHTMARKER);
        BitOutputStream.writeByte(os, (getLength() & 0xFF00) >> 8);
        BitOutputStream.writeByte(os, getLength() & 0xFF);
        BitOutputStream.writeByte(os, (tableClass << 4) + huffmanTableIdentifier);
        for (int i = 1; i <= 16; i++)
        {
            BitOutputStream.writeByte(os, codeWordLengthMap.get(i));
        }
        for (int i = 0; i < codeBook.size(); i++)
        {
            CodeWord codeWord = codeBook.get(i);
            BitOutputStream.writeByte(os, codeWord.getSymbol());
        }
    }
}
