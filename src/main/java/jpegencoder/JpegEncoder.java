package jpegencoder;

import jpegencoder.encoding.acdc.ACCategoryEncodedPair;
import jpegencoder.encoding.acdc.AcDcEncoder;
import jpegencoder.encoding.acdc.DCCategoryEncodedPair;
import jpegencoder.encoding.dct.CosineTransformation;
import jpegencoder.encoding.huffman.CodeWord;
import jpegencoder.encoding.huffman.HuffmanEncoder;
import jpegencoder.image.Image;
import jpegencoder.image.colors.ColorChannel;
import jpegencoder.segments.SegmentWriter;
import jpegencoder.segments.app0.APP0Writer;
import jpegencoder.segments.dht.DHTWriter;
import jpegencoder.segments.dht.HuffmanTable;
import jpegencoder.segments.dqt.DQTWriter;
import jpegencoder.segments.dqt.QuantizationTable;
import jpegencoder.segments.eoi.EOIWriter;
import jpegencoder.segments.sof0.SOF0Writer;
import jpegencoder.segments.soi.SOIWriter;
import jpegencoder.segments.sos.SOSWriter;
import jpegencoder.streams.BitOutputStream;
import org.jblas.DoubleMatrix;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Long Bui on 16.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class JpegEncoder
{
    Image image;
    List<DCCategoryEncodedPair> dcYValues;
    List<ACCategoryEncodedPair> acYValues;
    List<DCCategoryEncodedPair> dcCbCrValues;
    List<ACCategoryEncodedPair> acCbCrValues;
    Map<Integer, CodeWord> dcYCodeBook;
    Map<Integer, CodeWord> acYCodeBook;
    Map<Integer, CodeWord> dcCbCrCodeBook;
    Map<Integer, CodeWord> acCbCrCodeBook;

    public static JpegEncoder withImage(Image image)
    {
        return new JpegEncoder(image);
    }

    JpegEncoder(Image image)
    {
        this.image = image;
    }

    public JpegEncoder convertToJpeg()
    {
        performDCT();
        performQuantization();
        performAcDcEncoding();
        performHuffmanEncoding();
        return this;
    }

    void performDCT()
    {
        transformChannel(image.getChannel1());
        transformChannel(image.getChannel2());
        transformChannel(image.getChannel2());
    }

    void performQuantization()
    {
        quantizeChannel(image.getChannel1(), QuantizationTable.QUANTIZATION_MATRIX_LUMINANCE);
        quantizeChannel(image.getChannel2(), QuantizationTable.QUANTIZATION_MATRIX_CHROMINANCE);
        quantizeChannel(image.getChannel3(), QuantizationTable.QUANTIZATION_MATRIX_CHROMINANCE);
    }

    void performAcDcEncoding()
    {
        dcYValues = AcDcEncoder.getAllDCs(image.getChannel1());
        getDCCbCrValues();
        acYValues = AcDcEncoder.getAllACs(image.getChannel1());
        getACCbCrValues();
    }

    void performHuffmanEncoding()
    {
        huffmanEncodeDCY();
        huffmanEncodeACY();
        huffmanEncodeDCCbCr();
        huffmanEncodeACCbCr();
    }

    private void huffmanEncodeDCY()
    {
        int[] symbols = new int[dcYValues.size()];
        int index = 0;
        for (DCCategoryEncodedPair dcCategoryEncodedPair : dcYValues)
        {
            symbols[index++] = dcCategoryEncodedPair.getPair();
        }
        dcYCodeBook = HuffmanEncoder.encode(symbols).forJpeg().getCodebookAsMap();
    }

    private void huffmanEncodeACY()
    {
        int[] symbols = new int[acYValues.size()];
        int index = 0;
        for (ACCategoryEncodedPair acCategoryEncodedPair : acYValues)
        {
            symbols[index++] = acCategoryEncodedPair.getPair();
        }
        acYCodeBook = HuffmanEncoder.encode(symbols).forJpeg().getCodebookAsMap();
    }


    private void huffmanEncodeDCCbCr()
    {
        int[] symbols = new int[dcCbCrValues.size()];
        int index = 0;
        for (DCCategoryEncodedPair dcCategoryEncodedPair : dcCbCrValues)
        {
            symbols[index++] = dcCategoryEncodedPair.getPair();
        }
        dcCbCrCodeBook = HuffmanEncoder.encode(symbols).forJpeg().getCodebookAsMap();
    }

    private void huffmanEncodeACCbCr()
    {
        int[] symbols = new int[acCbCrValues.size()];
        int index = 0;
        for (ACCategoryEncodedPair acCategoryEncodedPair : acCbCrValues)
        {
            symbols[index++] = acCategoryEncodedPair.getPair();
        }
        acCbCrCodeBook = HuffmanEncoder.encode(symbols).forJpeg().getCodebookAsMap();
    }


    private void getDCCbCrValues()
    {
        dcCbCrValues = AcDcEncoder.getAllDCs(image.getChannel2());
        dcCbCrValues.addAll(AcDcEncoder.getAllDCs(image.getChannel3()));
    }

    private void getACCbCrValues()
    {
        acCbCrValues = AcDcEncoder.getAllACs(image.getChannel2());
        acCbCrValues.addAll(AcDcEncoder.getAllACs(image.getChannel3()));
    }

    private void transformChannel(ColorChannel channel)
    {
        for (int i = 0; i < channel.getNumOfBlocks(); i++)
        {
            CosineTransformation.arai(channel.getBlock(i));
        }
    }

    private void quantizeChannel(ColorChannel channel, DoubleMatrix quantizationTable)
    {
        for (int i = 0; i < channel.getNumOfBlocks(); i++)
        {
            CosineTransformation.quantize(channel.getBlock(i), quantizationTable);
        }
    }

    public HuffmanTable getHuffmanTableDCY()
    {
        return new HuffmanTable(0, 0,
                                (List<CodeWord>) dcYCodeBook.values());
    }

    public HuffmanTable getHuffmanTableDCCbCr()
    {
        return new HuffmanTable(1, 0,
                                (List<CodeWord>) dcCbCrCodeBook.values());
    }

    public HuffmanTable getHuffmanTableACY()
    {
        return new HuffmanTable(0, 1,
                                (List<CodeWord>) acYCodeBook.values());
    }

    public HuffmanTable getHuffmanTableACCbCr()
    {
        return new HuffmanTable(1, 1,
                                (List<CodeWord>) acCbCrCodeBook.values());
    }

    public void writeImageToDisk()
    {
        try
        {
            BitOutputStream bos = new BitOutputStream(new FileOutputStream("FinalImage.jpg"));
            List<SegmentWriter> segmentWriters = new ArrayList<SegmentWriter>();
            segmentWriters.add(new SOIWriter(bos));
            segmentWriters.add(new APP0Writer(bos, 0x0048, 0x0048));
            segmentWriters.add(new DQTWriter(bos));
            segmentWriters.add(new SOF0Writer(bos, image.getHeight(), image.getWidth()));
            List<HuffmanTable> huffmanTables = new ArrayList<HuffmanTable>();
            huffmanTables.add(getHuffmanTableDCY());
            huffmanTables.add(getHuffmanTableDCCbCr());
            huffmanTables.add(getHuffmanTableACY());
            huffmanTables.add(getHuffmanTableACCbCr());
            segmentWriters.add(new DHTWriter(bos, huffmanTables));
            segmentWriters.add(new SOSWriter(bos));

            segmentWriters.add(new EOIWriter(bos));
            for (SegmentWriter segmentWriter : segmentWriters)
            {
                segmentWriter.writeSegment();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
