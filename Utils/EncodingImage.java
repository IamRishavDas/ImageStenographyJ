package Utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Constants.Constants;

import java.awt.Color;
import java.awt.Graphics2D;

public class EncodingImage {

    private static Node[] tempImage;

    private static int imageWidth = 0;
    private static int imageHeight = 0;

    private static void printDetails() {
        Window.consoleLabel.setText("Image width: "      + imageWidth  +
                                    "Image height: "     + imageHeight + 
                                    "Color-Image size: " + tempImage.length);
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

        if(dataLength >= image.length) Window.consoleLabel.setText("Image is too small to hide the data!");

        Window.consoleLabel.setText("Data length: " + dataLength + " Node Image size: " + image.length);
        int[][] dataLengthArray = TextToBinary.asciiArrayToBinaryArray(TextToBinary.getAsciiArray(String.valueOf(dataLength)));
        DecodingImage.printData(dataLengthArray);


        // hiding the number at first, which represents the no of bits to be hidden
        int imageIndex = 0;
        for(int i=0; i<dataLengthArray.length; i++){
            for(int j=0; j<dataLengthArray[0].length; j++){
                if(!isEven(dataLengthArray[i][j])){
                    Color color = image[imageIndex].getColor();
                    int red   = color.getRed();
                    int green = color.getGreen();
                    int blue  = color.getBlue();
                    if(isEven(red)) image[imageIndex].setColor(new Color(++red, green, blue));
                    imageIndex++;
                }
            }
        }

        // hiding the actual data
        for(int i=0; i<data.length; i++){
            for(int j=0; j<data[0].length; j++){
                if(!isEven(data[i][j])){
                    Color color = image[imageIndex].getColor();
                    int red   = color.getRed();
                    int green = color.getGreen();
                    int blue  = color.getBlue();
                    if(isEven(red)) image[imageIndex].setColor(new Color(++red, green, blue));
                    imageIndex++;
                }
            }
        }

        displayEncodedImageOnFrame(image);

    }

    private static void displayEncodedImageOnFrame(Node[] image){
        BufferedImage new_image = new BufferedImage(EncodingImage.imageWidth, EncodingImage.imageHeight, BufferedImage.TYPE_INT_RGB);
        for(Node i: image){
            new_image.setRGB(i.getPosX(), i.getPosY(), i.getColor().getRGB());
        }
        Window.displayImage(new_image);
    }

    public static BufferedImage createNewImage(int[][] data, String filename) {
        if (data == null) return null;
        if(filename == null || filename.equals(" ")) filename = "generatedImage";
    
        int image_width = data.length * Constants.RECT_WIDTH;
        int image_height = data[0].length * Constants.RECT_HEIGHT;
    
        BufferedImage new_image = new BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = new_image.createGraphics();
    
        // Fill background with white
        g.setColor(Color.white);
        g.fillRect(0, 0, image_width, image_height);
    
        // Set color for rectangles
        g.setColor(Color.black);
    
        Window.consoleLabel.setText("Writing data in new image...");
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                // System.out.print(data[i][j] + " ");
                if (!isEven(data[i][j])) {
                    g.fillRect(i * Constants.RECT_WIDTH, j * Constants.RECT_HEIGHT, Constants.RECT_WIDTH, Constants.RECT_HEIGHT);
                } else {
                    g.setColor(Color.red);
                    g.fillRect(i * Constants.RECT_WIDTH, j * Constants.RECT_HEIGHT, Constants.RECT_WIDTH, Constants.RECT_HEIGHT);
                    g.setColor(Color.black);
                }
            } /*System.out.println();*/
        }
    
        g.dispose();
    
        try {
            File outputfile = new File(filename + ".png");
            ImageIO.write(new_image, "png", outputfile);
            Window.consoleLabel.setText("Image saved as " + outputfile.getAbsolutePath());
        } catch (IOException e) {
            Window.consoleLabel.setText("Error saving the image: " + e.getMessage());
        }

        Window.consoleLabel.setText("Save the image with the .png extension will help for further use...");
        return new_image;
    }
   
}
