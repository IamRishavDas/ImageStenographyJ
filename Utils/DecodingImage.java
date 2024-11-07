package Utils;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class DecodingImage {
    
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
}
