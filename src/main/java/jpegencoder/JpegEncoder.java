package jpegencoder;

import jpegencoder.encoding.acdc.ACCategoryEncodedPair;
import jpegencoder.encoding.acdc.AcDcEncoder;
import jpegencoder.encoding.acdc.DCCategoryEncodedPair;
import jpegencoder.encoding.huffman.CodeWord;
import jpegencoder.encoding.huffman.HuffmanEncoder;
import jpegencoder.image.Image;
import jpegencoder.segments.dht.HuffmanTable;

import java.util.List;

/**
 * Created by Long Bui on 16.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class JpegEncoder
{
    private Image image;

    public JpegEncoder(Image image)
    {
        this.image = image;
    }

    public HuffmanTable getHuffmanTableDCY()
    {
        List<DCCategoryEncodedPair> dcCategoryEncodedPairs = AcDcEncoder.getAllDCs(image.getChannel1());
        int[] symbols = new int[dcCategoryEncodedPairs.size()];
        int index = 0;
        for (DCCategoryEncodedPair dcCategoryEncodedPair : dcCategoryEncodedPairs)
        {
            symbols[index++] = dcCategoryEncodedPair.getPair();
        }
        return new HuffmanTable(0, 0,
                                (List<CodeWord>) HuffmanEncoder.encode(symbols)
                                                               .forJpeg()
                                                               .getCodebookAsMap()
                                                               .values());
    }

    public HuffmanTable getHuffmanTableDCCbCr()
    {
        List<DCCategoryEncodedPair> merged = AcDcEncoder.getAllDCs(image.getChannel2());
        merged.addAll(AcDcEncoder.getAllDCs(image.getChannel3()));
        int[] symbols = new int[merged.size()];
        int index = 0;
        for (DCCategoryEncodedPair dcCategoryEncodedPair : merged)
        {
            symbols[index++] = dcCategoryEncodedPair.getPair();
        }
        return new HuffmanTable(1, 0,
                                (List<CodeWord>) HuffmanEncoder.encode(symbols)
                                                               .forJpeg()
                                                               .getCodebookAsMap()
                                                               .values());
    }

    public HuffmanTable getHuffmanTableACY()
    {
        List<ACCategoryEncodedPair> acCategoryEncodedPairs = AcDcEncoder.getAllACs(image.getChannel1());
        int[] symbols = new int[acCategoryEncodedPairs.size()];
        int index = 0;
        for (ACCategoryEncodedPair acCategoryEncodedPair : acCategoryEncodedPairs)
        {
            symbols[index++] = acCategoryEncodedPair.getPair();
        }
        return new HuffmanTable(2, 1,
                                (List<CodeWord>) HuffmanEncoder.encode(symbols)
                                                               .forJpeg()
                                                               .getCodebookAsMap()
                                                               .values());
    }

    public HuffmanTable getHuffmanTableACCbCr()
    {
        List<ACCategoryEncodedPair> merged = AcDcEncoder.getAllACs(image.getChannel2());
        merged.addAll(AcDcEncoder.getAllACs(image.getChannel3()));
        int[] symbols = new int[merged.size()];
        int index = 0;
        for (ACCategoryEncodedPair acCategoryEncodedPair : merged)
        {
            symbols[index++] = acCategoryEncodedPair.getPair();
        }
        return new HuffmanTable(3, 1,
                                (List<CodeWord>) HuffmanEncoder.encode(symbols)
                                                               .forJpeg()
                                                               .getCodebookAsMap()
                                                               .values());
    }
}
