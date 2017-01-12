package jpegencoder.segments.dqt;

import jpegencoder.segments.SegmentWriter;
import jpegencoder.streams.BitOutputStream;
import org.jblas.DoubleMatrix;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by Long Bui on 10.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class DQTWriter extends SegmentWriter
{
    public static int DQTMARKER = 0xDB;
    private int length;
    private List<QuantizationTable> tables;

    public DQTWriter(BitOutputStream os)
    {
        super(os);
    }

    public void setTables(List<QuantizationTable> tables)
    {
        this.tables = tables;
        length = 2 + tables.size() * 65;
    }

    public void writeSegment() throws IOException
    {
        os.writeByte(0xff);
        os.writeByte(DQTMARKER);
        os.writeByte((length & 0xFF00) >> 8);
        os.writeByte(length & 0xFF);
        for (QuantizationTable table : tables)
        {
            table.writeTable(os);
        }
    }


}
