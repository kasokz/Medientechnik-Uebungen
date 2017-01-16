package jpegencoder.segments.dqt;

import jpegencoder.encoding.Util;
import jpegencoder.streams.BitOutputStream;
import org.jblas.DoubleMatrix;

import java.io.IOException;

/**
 * Created by Long Bui on 12.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class QuantizationTable
{
    private int id;
    private int precision = 0;
    private DoubleMatrix table;

    public QuantizationTable(int id, DoubleMatrix table)
    {
        this.id = id;
        this.table = table;
    }

    public void writeTable(BitOutputStream bos) throws IOException
    {
        bos.writeByte((precision << 4) + id);
        zigzagSort(bos);
    }

    private void zigzagSort(BitOutputStream os) throws IOException
    {
        int[] zigzaged = Util.zigzagSort(table);
        for (int tableEntry : zigzaged)
        {
            os.writeByte(tableEntry);
        }
    }

}
