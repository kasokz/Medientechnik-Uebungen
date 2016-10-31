package Blatt1;

import Blatt1.colors.ColorChannels;
import Blatt1.colors.rgb.RGB;
import Blatt1.colors.rgb.RGBPicture;
import Blatt1.colors.ycbcr.YCbCr;
import Blatt1.colors.ycbcr.YCbCrPicture;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
        BufferedImage img = readPpmAndRender();
        Image image = SwingFXUtils.toFXImage(img, null);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(1080);
        VBox verticalBox = new VBox();
        verticalBox.getChildren().add(imageView);
        Scene scene = new Scene(verticalBox, image.getWidth(), image.getHeight());
        primaryStage.setTitle("Test Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private BufferedImage readPpmAndConvertToYcbcr()
    {
        RGBPicture testPicture = null;
        try
        {
            testPicture = new RGBPicture(new FileInputStream(new File("test-pic.ppm")));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        YCbCrPicture yCbCrPicture = ColorChannels.RGBToYCbCr(testPicture);
        long start = System.currentTimeMillis();
        BufferedImage img = new BufferedImage(yCbCrPicture.getWidth(),
                                              yCbCrPicture.getHeight(),
                                              BufferedImage.TYPE_INT_RGB);
        int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < yCbCrPicture.getHeight(); i++)
        {
            for (int j = 0; j < yCbCrPicture.getWidth(); j++)
            {
                YCbCr pixel = yCbCrPicture.getPixelAt(j, i);
                pixels[j + i * yCbCrPicture.getWidth()] = pixel.getLuminanceChannel() << 16
                        | pixel.getCbChannel() << 8
                        | pixel.getCrChannel();
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
        RGBPicture testPicture = null;
        try
        {
//            testPicture = new RGBPicture(new FileInputStream(new File("test-pic.ppm")));
            testPicture = new RGBPicture(new FileInputStream(new File("test-pic.ppm")), 2, 2, 2);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        long start = System.currentTimeMillis();
        BufferedImage img = new BufferedImage(testPicture.getWidth(),
                                              testPicture.getHeight(),
                                              BufferedImage.TYPE_INT_RGB);
        int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < testPicture.getHeight(); i++)
        {
            for (int j = 0; j < testPicture.getWidth(); j++)
            {
                RGB pixel = testPicture.getRGBAt(j, i);
                pixels[j + i * testPicture.getWidth()] = pixel.getRed() << 16
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