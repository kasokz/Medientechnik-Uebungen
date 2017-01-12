package jpegencoder.segments.dct;

import org.jblas.DoubleMatrix;

import java.util.List;

/**
 * Created by Long Bui on 20/12/2016.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class DirectDCTTask implements Runnable
{
    private List<DoubleMatrix> blocks;

    public DirectDCTTask(List<DoubleMatrix> blocks)
    {
        this.blocks = blocks;
    }

    public void run()
    {
        for (DoubleMatrix block : blocks)
        {
            CosineTransformation.direct(block);
        }
    }
}
