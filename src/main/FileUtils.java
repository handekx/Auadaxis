package main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public File createFile() {
        File myFile = null;
        try {
             myFile = new File("ad_user_content.txt");
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            } else {
                System.out.println("File already exists.");
                writeFile("", false);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return myFile;
    }

    public void addManualRow() {
    writeFile("7;1000000;0;Y;2010-03-18 16:09:28.0;100;2010-06-22 10:02:55.0;100;haithem;null;null;haithem@mail.com;null;1000783;N;null;null;1000653;1000001;null;null;null;null;null;2010-06-22 00:00:00.0;Facture: VTE10071;null;null;null;null;null;Y;null;E;null;cting;null;null;null;N;null;", true);
    }


    public void writeFile(String tableRow, boolean append) {
        if(tableRow.equals("")) {
            FileWriter myWriter = null;
            try {
                myWriter = new FileWriter("ad_user_content.txt", append);
                myWriter.write("");
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        else {
            try {
                FileWriter myWriter = new FileWriter("ad_user_content.txt", append);
                myWriter.write(tableRow+"\n");
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

    }




}
