package Utils;

public class TextToBinary {

    public static final int BIT_SIZE = 9; // although it is 8 but, 9 for parsing convinience (multiple of 3)
    public static final int BINARY_BASE = 2;

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
        int[] binary = new int[TextToBinary.BIT_SIZE];
        int index = binary.length - 1;
        while (ascii != 0) {
            binary[index--] = ascii % TextToBinary.BINARY_BASE;
            ascii /= TextToBinary.BINARY_BASE;
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