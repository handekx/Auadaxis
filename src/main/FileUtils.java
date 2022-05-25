package main;

import java.io.*;

public class FileUtils {


    public void setMyFile(File myFile) {
        this.myFile = myFile;
    }

    private File myFile;

    public File createFile(File selectedFile) {
        myFile = selectedFile;
        try {
            if (myFile.createNewFile()) {
                System.out.println("File created: " + myFile.getName());
            } else {
                System.out.println("File already exists! Cleaning file content");
                writeFile("", false);
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return myFile;
    }

    public void addManualRow() {
        writeFile("150;1000022;0;Y;2010-11-25 15:43:57.0;100;2010-11-25 15:43:57.0;100;Contact04;null;null;contact04@mail.com;null;1002396;N;null;null;null;null;null;null;null;null;null;null;null;null;null;null;null;null;Y;null;E;null;1000006;null;null;null;N;null", true);
    }


    public void writeFile(String tableRow, boolean append) {
        if (tableRow.equals("")) {
            try {

                PrintWriter writer = new PrintWriter(myFile);
                writer.print("");
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                FileWriter myWriter = new FileWriter(myFile, append);
                myWriter.write(tableRow + "\n");
                myWriter.close();

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

    }

    public File getMyFile() {
        return myFile;
    }
}
