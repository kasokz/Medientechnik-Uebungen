package jpegencoder.segments.imageData;

import jpegencoder.encoding.Util;
import jpegencoder.encoding.acdc.ACCategoryEncodedPair;
import jpegencoder.encoding.acdc.ACRunlengthEncodedPair;
import jpegencoder.encoding.acdc.AcDcEncoder;
import jpegencoder.encoding.acdc.DCCategoryEncodedPair;
import jpegencoder.encoding.huffman.CodeWord;
import jpegencoder.image.Image;
import jpegencoder.image.colors.ColorChannel;
import jpegencoder.segments.SegmentWriter;
import jpegencoder.streams.BitOutputStream;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Long Bui on 17.01.17.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class ImageDataWriter extends SegmentWriter
{
    private Image image;
    private Map<Integer, CodeWord> dcYCodeBook;
    private Map<Integer, CodeWord> acYCodeBook;
    private Map<Integer, CodeWord> dcCbCrCodeBook;
    private Map<Integer, CodeWord> acCbCrCodeBook;
    private int subSampling = 2;

    public ImageDataWriter(BitOutputStream os, Image image,
                           Map<Integer, CodeWord> dcYCodeBook,
                           Map<Integer, CodeWord> acYCodeBook,
                           Map<Integer, CodeWord> dcCbCrCodeBook,
                           Map<Integer, CodeWord> acCbCrCodeBook)
    {
        super(os);
        this.image = image;
        this.dcYCodeBook = dcYCodeBook;
        this.acYCodeBook = acYCodeBook;
        this.dcCbCrCodeBook = dcCbCrCodeBook;
        this.acCbCrCodeBook = acCbCrCodeBook;
    }

    public void writeSegment() throws IOException
    {
        for (int currentX = 0; currentX < image.getWidth() / 8 / subSampling; currentX++)
        {
            for (int currentY = 0; currentY < image.getHeight() / 8 / subSampling; currentY++)
            {
                for (int currentXLuminance = currentX * subSampling;
                     currentXLuminance < currentX + subSampling;
                     currentXLuminance++)
                {
                    for (int currentYLuminance = currentY * subSampling;
                         currentYLuminance < currentY + subSampling;
                         currentYLuminance++)
                    {
                        writeAcDcEncodedBlock(image.getChannel1(),
                                              currentXLuminance,
                                              currentYLuminance,
                                              dcYCodeBook,
                                              acYCodeBook);
                    }
                }
                writeAcDcEncodedBlock(image.getChannel2(), currentX, currentY, dcCbCrCodeBook, acCbCrCodeBook);
                writeAcDcEncodedBlock(image.getChannel3(), currentX, currentY, dcCbCrCodeBook, acCbCrCodeBook);
            }
        }
        os.flush();
    }

    private void writeAcDcEncodedBlock(ColorChannel channel, int xOfChannel, int yOfChannel,
                                       Map<Integer, CodeWord> dcCodeBook,
                                       Map<Integer, CodeWord> acCodeBook)
            throws IOException
    {
        DCCategoryEncodedPair dc = AcDcEncoder.calculateDifferenceDC(channel,
                                                                     channel
                                                                             .getPlainIndexOfBlock(
                                                                                     xOfChannel,
                                                                                     yOfChannel));
        List<ACRunlengthEncodedPair> acRunlengthEncodedPairs =
                AcDcEncoder.encodeRunlength(Util.zigzagSort(channel.getBlock(xOfChannel,
                                                                             yOfChannel)));
        List<ACCategoryEncodedPair> acCategoryEncodedPairs = AcDcEncoder.encodeCategoriesAC(
                acRunlengthEncodedPairs);
        AcDcEncoder.writeDC(os, dc.getEntryCategoryEncoded(), dcCodeBook);
        AcDcEncoder.writeACTable(os, acCategoryEncodedPairs, acCodeBook);
    }
}
