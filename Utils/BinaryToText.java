package Utils;

import Constants.Constants;

public class BinaryToText {

    public static int binaryToDecimal(int[] binary){
        int decimal = 0;
        for(int i=0; i<binary.length; i++){
            decimal += binary[i] * Math.pow(Constants.BINARY_BASE, Constants.BIT_SIZE-1-i);
        }
        return decimal;
    }

    private static String decimalToAscii(int decimal) {
        return Character.toString((char) decimal);
    }
    

    public static String binaryToText(int[][] data){
        String text = "";
        for(int[] arr: data){
            text += decimalToAscii(binaryToDecimal(arr));
        }
        return text;
    }
}
