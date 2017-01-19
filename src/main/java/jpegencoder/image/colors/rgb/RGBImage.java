package jpegencoder.image.colors.rgb;

import jpegencoder.image.Image;
import jpegencoder.image.colors.ColorChannel;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class RGBImage extends Image
{
    public static class RGBImageBuilder
    {
        private int strideWidth = 8;
        private int strideHeight = 8;
        private int imageWidth;
        private int imageHeight;
        ColorChannel red;
        ColorChannel green;
        ColorChannel blue;

        public static RGBImageBuilder from(InputStream is)
        {
            return new RGBImageBuilder(is);
        }

        public static RGBImageBuilder from(ColorChannel red, ColorChannel green, ColorChannel blue)
        {
            return new RGBImageBuilder(red, green, blue);
        }

        private RGBImageBuilder(ColorChannel red, ColorChannel green, ColorChannel blue)
        {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.imageWidth = red.getWidth();
            this.imageHeight = red.getHeight();
        }

        private RGBImageBuilder(InputStream is)
        {
            long start = System.currentTimeMillis();
            Scanner sc = new Scanner(is);
            try
            {
                extractMetaInformation(sc);
                initPicture();
                int x = 0;
                int y = 0;
                while (sc.hasNext())
                {
                    red.setPixel(x, y, sc.nextInt());
                    green.setPixel(x, y, sc.nextInt());
                    blue.setPixel(x, y, sc.nextInt());
                    x++;
                    if (x % imageWidth == 0)
                    {
                        x = 0;
                        y++;
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            System.out.println("Finished reading PPM in "
                                       + ((System.currentTimeMillis() - start) / 1000d)
                                       + " seconds");
        }

        private void initPicture()
        {
            red = new ColorChannel(getRealHeight(), getRealWidth());
            green = new ColorChannel(getRealHeight(), getRealWidth());
            blue = new ColorChannel(getRealHeight(), getRealWidth());
        }

        private void extractMetaInformation(Scanner sc) throws IOException
        {
            sc.nextLine();
            String metaInformation = readLine(sc);
            String[] splitMeta = metaInformation.split(" ");
            this.imageWidth = Integer.parseInt(splitMeta[0]);
            this.imageHeight = Integer.parseInt(splitMeta[1]);
            sc.nextLine();
        }

        private String readLine(Scanner sc)
        {
            String result = sc.nextLine();
            if (result.contains("#"))
            {
                return readLine(sc);
            }
            return result;
        }

        private int getRealHeight()
        {
            int result = imageHeight;
            if (imageHeight % strideHeight != 0)
            {
                result = ((imageHeight / strideHeight) + 1) * strideHeight;
            }
            return result;
        }

        private int getRealWidth()
        {
            int result = imageWidth;
            if (imageWidth % strideWidth != 0)
            {
                result = ((imageWidth / strideWidth) + 1) * strideWidth;
            }
            return result;
        }

        public RGBImage build()
        {
            return new RGBImage(red, green, blue, imageWidth, imageHeight);
        }
    }

    private int originalWidth;
    private int originalHeight;

    private RGBImage(ColorChannel r, ColorChannel g, ColorChannel b, int originalWidth, int originalHeight)
    {
        super(r, g, b);
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
    }

    public RGB getRGBAt(int x, int y)
    {
        if (x >= getWidth() || y >= getHeight())
        {
            throw new IllegalArgumentException();
        }
        if (x >= originalWidth)
        {
            x = originalWidth - 1;
        }
        if (y >= originalHeight)
        {
            y = originalHeight - 1;
        }
        return new RGB((int) getChannel1().getPixel(x, y),
                       (int) getChannel2().getPixel(x, y),
                       (int) getChannel3().getPixel(x, y));
    }

    public int getOriginalWidth()
    {
        return originalWidth;
    }

    public int getOriginalHeight()
    {
        return originalHeight;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getWidth(); i++)
        {
            for (int j = 0; j < getHeight(); j++)
            {
                RGB pixel = getRGBAt(i, j);
                sb.append(pixel.toString()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
