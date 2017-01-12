package jpegencoder.image;

import jpegencoder.image.colors.rgb.RGBImage;
import jpegencoder.image.colors.ycbcr.YCbCrImage;
import jpegencoder.image.colors.ColorChannels;
import jpegencoder.image.colors.rgb.RGB;
import jpegencoder.image.colors.ycbcr.YCbCr;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.*;

/**
 * Created by Long Bui on 26.10.16.
 * E-Mail: giaolong.bui@student.fhws.de
 */
public class TestApplication extends Application
{
    public static void main(String[] args)
    {
        launch();
    }

    public void start(Stage primaryStage) throws Exception
    {
        BufferedImage img = readPpmAndConvertToYcbcr();
        Image image = SwingFXUtils.toFXImage(img, null);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(900);
        VBox verticalBox = new VBox();
        verticalBox.getChildren().add(imageView);
        Scene scene = new Scene(verticalBox, image.getWidth(), image.getHeight());
        primaryStage.setTitle("Test Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BufferedImage readPpmAndConvertToYcbcr()
    {
        RGBImage testPicture = null;
        try
        {
            testPicture = new RGBImage(new FileInputStream(new File("test-pic.ppm")));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        YCbCrImage yCbCrImage = ColorChannels.RGBToYCbCr(testPicture);
        yCbCrImage.reduce(1, 1, 1);
        long start = System.currentTimeMillis();
        BufferedImage img = new BufferedImage(yCbCrImage.getWidth(),
                                              yCbCrImage.getHeight(),
                                              BufferedImage.TYPE_BYTE_GRAY);
        byte[] pixels = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < yCbCrImage.getHeight(); i++)
        {
            for (int j = 0; j < yCbCrImage.getWidth(); j++)
            {
                YCbCr pixel = yCbCrImage.getPixelAt(i, j);
                pixels[j + i * yCbCrImage.getWidth()] = (byte) pixel.getLuminanceChannel();
            }
        }
        System.out.println("Built image in "
                                   + ((System.currentTimeMillis() - start) / 1000d)
                                   + " seconds");
        return img;
    }

    // Aufgabe 1d) Demo
    private BufferedImage readPpmAndRender()
    {
        RGBImage testPicture = null;
        try
        {
            testPicture = new RGBImage(new FileInputStream(new File("test-pic.ppm")));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        long start = System.currentTimeMillis();
        BufferedImage img = new BufferedImage(testPicture.getStrideWidth(),
                                              testPicture.getStrideHeight(),
                                              BufferedImage.TYPE_INT_RGB);
        int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < testPicture.getStrideHeight(); i++)
        {
            for (int j = 0; j < testPicture.getStrideWidth(); j++)
            {
                RGB pixel = testPicture.getRGBAt(j, i);
                pixels[j + i * testPicture.getStrideWidth()] = pixel.getRed() << 16
                        | pixel.getGreen() << 8
                        | pixel.getBlue();
            }
        }
        System.out.println("Built image in "
                                   + ((System.currentTimeMillis() - start) / 1000d)
                                   + " seconds");
        return img;
    }
}