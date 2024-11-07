package Utils;

import java.io.FileWriter;
import java.io.IOException;

public class CreateFile {

    public static void createFile(String filename, String fileContent){

        if(filename == null || filename == "") filename = "output.txt";
        if(fileContent == null) fileContent = "";

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(fileContent);
            System.out.println("Successfully wrote content to " + filename);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

}
