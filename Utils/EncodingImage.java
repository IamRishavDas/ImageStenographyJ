package Utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics2D;

public class EncodingImage {

    private static Node[] tempImage;

    private static int imageWidth = 0;
    private static int imageHeight = 0;

    private static void printDetails() {
        System.out.println("Image width: " + imageWidth);
        System.out.println("Image height: " + imageHeight);
        System.out.println("Color-Image size: " + tempImage.length);
    }

    public static Node[] loadImage(BufferedImage image) {
        if (image == null)
            return null;

        imageWidth = image.getWidth();
        imageHeight = image.getHeight();
        tempImage = new Node[imageWidth * imageHeight];

        int index = 0;
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                Color color = new Color(image.getRGB(x, y));
                tempImage[index++] = new Node(x, y, color);
            }
        }

        printDetails();

        return tempImage;
    }

    public static boolean isEven(int value) {
        return value % 2 == 0;
    }

    @SuppressWarnings("unused")
    public static void encode(Node[] image, int[][] data) {
        if (image == null || data == null)
            return;

        int rowData = data.length;
        int colData = data[0].length;
        int dataLength = rowData * colData;

        System.out.println("length: " + dataLength);

        // System.out.println("red: " + red + " green: " + green + " blue: " + blue);

        // int red = Integer.parseInt(String.valueOf(node.getColor().getRed()), 16);
        // int green = Integer.parseInt(String.valueOf(node.getColor().getGreen()), 16);
        // int blue = Integer.parseInt(String.valueOf(node.getColor().getBlue()), 16);

        // for (Node node : tempImage) {
        //     System.out.println(node);

        //     int red = node.getColor().getRed();
        //     int green = node.getColor().getGreen();
        //     int blue = node.getColor().getBlue();

        // }

    }

    public static void createNewImage(int[][] data, String filename) {
        if (data == null) return;
    
        int rect_width = 20;
        int rect_height = 20;
        int image_width = data.length * rect_width;
        int image_height = data[0].length * rect_height;
    
        BufferedImage new_image = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = new_image.createGraphics();
    
        // Fill background with white
        g.setColor(Color.white);
        g.fillRect(0, 0, image_width, image_height);
    
        // Set color for rectangles
        g.setColor(Color.black);
    
        System.out.println("Writing data in new image...");
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.print(data[i][j] + " ");
                if (!isEven(data[i][j])) {
                    g.fillRect(i * rect_width, j * rect_height, rect_width, rect_height);
                }
            } System.out.println();
        }
    
        g.dispose();
    
        try {
            File outputfile = new File(filename + ".png");
            ImageIO.write(new_image, "png", outputfile);
            System.out.println("Image saved as " + outputfile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving the image: " + e.getMessage());
        }
    }
   
}
