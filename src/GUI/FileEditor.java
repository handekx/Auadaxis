package GUI;

import main.JDBCUtils;

import javax.swing.*;
import java.awt.*;
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
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
        initTextPane();
    }

    public void initTextPane() {
        File f = jdbcUtils.getFileUtils().getMyFile();
        fileEditorLabel.setText(f.getName());
        Scanner sc = null;
        try {
            sc = new Scanner(f);
            while (sc.hasNextLine()) {
                textPane1.setText(textPane1.getText() + "\n" + sc.nextLine() + "\n");
            }
            sc.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private JTextPane textPane1;
    private JLabel fileEditorLabel;

}
