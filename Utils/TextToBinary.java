package Utils;

import Constants.Constants;

public class TextToBinary {


    public static int[] getAsciiArray(String text) {
        text = text.toLowerCase(); 
        char[] charArray = text.toCharArray();
        int[] asciiArray = new int[charArray.length];
        int i = 0;
        for (char c : charArray) {
            asciiArray[i++] = c;
        }
        return asciiArray;
    }

    private static int[] convertAsciiBase(int ascii) {
        int[] binary = new int[Constants.BIT_SIZE];
        int index = binary.length - 1;
        while (ascii != 0) {
            binary[index--] = ascii % Constants.BINARY_BASE;
            ascii /= Constants.BINARY_BASE;
        }
        return binary;
    }

    public static int[][] asciiArrayToBinaryArray(int[] asciiArray) {
        int[][] binaryMatrix = new int[asciiArray.length][];
        int index = 0;
        for (int ascii : asciiArray) {
            binaryMatrix[index++] = TextToBinary.convertAsciiBase(ascii);
        }
        return binaryMatrix;
    }

}