package jpegencoder.segments;

import jpegencoder.segments.dct.CosineTransformation;
import jpegencoder.segments.dqt.DQTWriter;
import jpegencoder.segments.dqt.QuantizationTable;
import jpegencoder.streams.BitOutputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long Bui on 12.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class Blatt5
{
    public void testDqtWriter() throws IOException
    {
        BitOutputStream bos = new BitOutputStream(new FileOutputStream("bits.dat"));
        DQTWriter dqtWriter = new DQTWriter(bos);
        List<QuantizationTable> tables = new ArrayList<QuantizationTable>();
        tables.add(new QuantizationTable(0, CosineTransformation.QUANTIZATION_MATRIX_LUMINANCE));
        tables.add(new QuantizationTable(1, CosineTransformation.QUANTIZATION_MATRIX_CHROMINANCE));
        dqtWriter.setTables(tables);
        dqtWriter.writeSegment();
    }
}
