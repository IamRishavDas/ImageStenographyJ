import Utils.TextToBinary;

public class Test {
    public static void main(String[] args) {
        var binMatrix = TextToBinary.asciiArrayToBinaryArray(TextToBinary.getAsciiArray("i love you"));
        for(int[] i: binMatrix){
            for(int j: i){
                System.out.print(j + " ");
            }
            System.out.println();
        }
    }
}
