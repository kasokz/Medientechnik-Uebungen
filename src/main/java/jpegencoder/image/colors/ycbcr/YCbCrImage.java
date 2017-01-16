package jpegencoder.image.colors.ycbcr;

import org.jblas.DoubleMatrix;

import java.util.ArrayList;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class YCbCrImage
{
    private DoubleMatrix luminance;
    private DoubleMatrix cbChannel;
    private DoubleMatrix crChannel;

    private int cbReductionFaktor = 1;
    private int crReductionFaktor = 1;


    public YCbCrImage(DoubleMatrix luminance, DoubleMatrix cbChannel, DoubleMatrix crChannel)
    {
        this.luminance = luminance;
        this.cbChannel = cbChannel;
        this.crChannel = crChannel;
    }

    public int getHeight()
    {
        return luminance.getRows();
    }

    public int getWidth()
    {
        return luminance.getColumns();
    }

    public YCbCr getPixelAt(int x, int y)
    {
        return new YCbCr((int) luminance.get(x, y), (int) cbChannel.get(x, y), (int) crChannel.get(x, y));
    }

    public void reduce(int luminanceFactor, int cbFactor, int crFactor)
    {
        this.luminance = reduceChannel(luminance, luminanceFactor);
        this.cbChannel = reduceChannel(cbChannel, cbFactor);
        this.crChannel = reduceChannel(crChannel, crFactor);
    }

    private DoubleMatrix reduceChannel(DoubleMatrix channel, int factor)
    {
        int reducedRowCount = channel.getRows() / factor;
        int reducedColumnCount = channel.getColumns() / factor;
        DoubleMatrix result = new DoubleMatrix(reducedRowCount, reducedColumnCount);
        for (int row = 0; row < reducedRowCount; row++)
        {
            for (int column = 0; column < reducedColumnCount; column++)
            {
                int sum = 0;
                for (int blockRow = 0; blockRow < factor; blockRow++)
                {
                    int rowInInput = row * factor + blockRow;
                    for (int blockColumn = 0; blockColumn < factor; blockColumn++)
                    {
                        int columnInInput = column * factor + blockColumn;
                        sum += channel.get(rowInInput, columnInInput);
                    }
                }
                result.put(row, column, (int) ((sum / (double) (factor * factor)) + 0.5));
            }
        }
        return result;

    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getHeight(); i++)
        {
            for (int j = 0; j < getWidth(); j++)
            {
                sb.append(getPixelAt(i, j).toString())
                  .append(",");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
