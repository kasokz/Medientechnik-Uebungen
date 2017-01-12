package jpegencoder.segments.dht;

import jpegencoder.encoding.CodeWord;
import jpegencoder.streams.BitOutputStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Long Bui on 12/01/2017.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class HuffmanTable
{
    private int tableClass;
    private List<CodeWord> codeBook;
    private Map<Integer, Integer> codeWordLengthMap = new HashMap<Integer, Integer>();
    private int id;

    public HuffmanTable(int id, int tableClass, List<CodeWord> codeBook)
    {
        this.id = id;
        this.tableClass = tableClass;
        this.codeBook = codeBook;
        this.setCodeWordLengthMap();
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

    public int getCodebookSize()
    {
        return codeBook.size();
    }

    public void write(BitOutputStream os) throws IOException
    {
        os.writeByte((tableClass << 4) + id);
        for (int i = 1; i <= 16; i++)
        {
            os.writeByte(codeWordLengthMap.get(i));
        }
        for (int i = 0; i < codeBook.size(); i++)
        {
            CodeWord codeWord = codeBook.get(i);
            os.writeByte(codeWord.getSymbol());
        }
    }
}
