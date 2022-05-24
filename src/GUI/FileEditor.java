package GUI;

import main.JDBCUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileEditor {
    private JPanel panel1;
    JDBCUtils jdbcUtils;

    public FileEditor(JDBCUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;

        JFrame frame = new JFrame("FileEditor");

        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
        initTextPane();
    }

    public void initTextPane() {
        File f = new File("ad_user_content.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(f);


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNextLine()) {
            textPane1.setText(textPane1.getText() + "\n" + sc.nextLine() + "\n");
        }
    }

    private JTextPane textPane1;
}
