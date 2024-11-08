package Utils;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class DecodingImage {


    public static String decodeImage(BufferedImage image){

        // parsing the number of bits to be parsed
        int[] noOfBitsToBeParsedArray = new int[9];
        int count = 0, index = 0;
        for(int i=0; i<image.getWidth(); i++){
            for(int j=0; j<image.getHeight(); j++){
                if(count >= 10 || count < 0) break;
                if(index >= 9) break;
                if(new Color(image.getRGB(j, i)).getRed() % 2 == 0) noOfBitsToBeParsedArray[index] = 0;
                else noOfBitsToBeParsedArray[index] = 1;
                index++; count++;
            }
        }

        printData(noOfBitsToBeParsedArray);
        int noOfBitsToBeParsed = BinaryToText.binaryToDecimal(noOfBitsToBeParsedArray);
        Window.consoleLabel.setText("The no of bits to be parsed: " + noOfBitsToBeParsed);

        return null;
    }

    public static int[][] parseImageToData(BufferedImage image, int rect_width, int rect_height) {

        int data_width = image.getWidth() / rect_width;
        int data_height = image.getHeight() / rect_height;
        int[][] data = new int[data_width][data_height];

        for (int i = 0; i < data_width; i++) {
            for (int j = 0; j < data_height; j++) {

                int pixelColor = image.getRGB(i * rect_width, j * rect_height);
                Color color = new Color(pixelColor);

                if (color.equals(Color.black)) {
                    data[i][j] = 1;
                } else {
                    data[i][j] = 0;
                }
            }
        }

        return data;
    }

    public static void printData(int[][] data) {
        for (int[] arr : data) {
            for (int i : arr) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    public static void printData(int[] data){
        for(int i: data){
            System.out.print(i + " ");
        }
    }
}
