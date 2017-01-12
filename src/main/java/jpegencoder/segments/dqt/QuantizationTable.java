package jpegencoder.segments.dqt;

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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public DoubleMatrix getTable()
    {
        return table;
    }

    public void setTable(DoubleMatrix table)
    {
        this.table = table;
    }

    public void writeTable(BitOutputStream bos) throws IOException
    {
        bos.writeByte((precision << 4) + id);
        zigzag(bos);
    }

    private void zigzag(BitOutputStream os)
    {
        try
        {
            os.writeByte((int) table.get(0, 0));
            os.writeByte((int) table.get(0, 1));
            os.writeByte((int) table.get(1, 0));
            os.writeByte((int) table.get(2, 0));
            os.writeByte((int) table.get(1, 1));
            os.writeByte((int) table.get(0, 2));
            os.writeByte((int) table.get(0, 3));
            os.writeByte((int) table.get(1, 2));

            os.writeByte((int) table.get(2, 1));
            os.writeByte((int) table.get(3, 0));
            os.writeByte((int) table.get(4, 0));
            os.writeByte((int) table.get(3, 1));
            os.writeByte((int) table.get(2, 2));
            os.writeByte((int) table.get(1, 3));
            os.writeByte((int) table.get(0, 4));
            os.writeByte((int) table.get(0, 5));

            os.writeByte((int) table.get(1, 4));
            os.writeByte((int) table.get(2, 3));
            os.writeByte((int) table.get(3, 2));
            os.writeByte((int) table.get(4, 1));
            os.writeByte((int) table.get(5, 0));
            os.writeByte((int) table.get(6, 0));
            os.writeByte((int) table.get(5, 1));
            os.writeByte((int) table.get(4, 2));

            os.writeByte((int) table.get(3, 3));
            os.writeByte((int) table.get(2, 4));
            os.writeByte((int) table.get(1, 5));
            os.writeByte((int) table.get(0, 6));
            os.writeByte((int) table.get(0, 7));
            os.writeByte((int) table.get(1, 6));
            os.writeByte((int) table.get(2, 5));
            os.writeByte((int) table.get(3, 4));

            os.writeByte((int) table.get(4, 3));
            os.writeByte((int) table.get(5, 2));
            os.writeByte((int) table.get(6, 1));
            os.writeByte((int) table.get(7, 0));
            os.writeByte((int) table.get(7, 1));
            os.writeByte((int) table.get(6, 2));
            os.writeByte((int) table.get(5, 3));
            os.writeByte((int) table.get(4, 4));

            os.writeByte((int) table.get(3, 5));
            os.writeByte((int) table.get(2, 6));
            os.writeByte((int) table.get(1, 7));
            os.writeByte((int) table.get(2, 7));
            os.writeByte((int) table.get(3, 6));
            os.writeByte((int) table.get(4, 5));
            os.writeByte((int) table.get(5, 4));
            os.writeByte((int) table.get(6, 3));

            os.writeByte((int) table.get(7, 2));
            os.writeByte((int) table.get(7, 3));
            os.writeByte((int) table.get(6, 4));
            os.writeByte((int) table.get(5, 5));
            os.writeByte((int) table.get(4, 6));
            os.writeByte((int) table.get(3, 7));
            os.writeByte((int) table.get(4, 7));
            os.writeByte((int) table.get(5, 6));

            os.writeByte((int) table.get(6, 5));
            os.writeByte((int) table.get(7, 4));
            os.writeByte((int) table.get(7, 5));
            os.writeByte((int) table.get(6, 6));
            os.writeByte((int) table.get(5, 7));
            os.writeByte((int) table.get(6, 7));
            os.writeByte((int) table.get(7, 6));
            os.writeByte((int) table.get(7, 7));

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
