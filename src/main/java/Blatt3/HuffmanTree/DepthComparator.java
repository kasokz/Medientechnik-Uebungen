package Blatt3.HuffmanTree;

import java.util.Comparator;

/**
 * Created by Long Bui on 06.12.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class DepthComparator implements Comparator<HuffmanTreeComponent>
{
    public int compare(HuffmanTreeComponent o1, HuffmanTreeComponent o2)
    {
        int result;
        //Fallunterscheidung, damit c garantiert wird (kleinstes ganz rechts)
        if (o1.getDepth(0) == o2.getDepth(0))
        {
            result = -new Double(o1.getFrequency()).compareTo(o2.getFrequency());
        }
        else
        {
            result = new Integer(o1.getDepth(0)).compareTo(o2.getDepth(0));
        }
        return result;
    }
}
