package GUI;

import main.JDBCUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class QueryFrame {
    private JPanel panel1;
    private JTable table1;
    private JTextField textField1;
    private JLabel label;
    private JButton getAllUsersButton;
    private JButton executeQueryButton;
    private JButton saveAsFIleButton;
    private JButton clearButton;
    private JButton uploadButton;
    private JButton exportToDBButton;
    private JButton addRowButton;
    private JButton previewButton;
    private JButton browseButton;
    private JDBCUtils jdbcUtils;
    private ResultSet currentResult;

    public QueryFrame(JDBCUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
        JFrame frame = new JFrame("QueryFrame");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        initComponent();

    }

    private void initComponent() {
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        getAllUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    initTable("SELECT * FROM AD_USER");
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            }
        });
        executeQueryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    initTable(textField1.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tab[] = new String[0];
                Object[][] data = new Object[0][0];
                DefaultTableModel dtm = new DefaultTableModel(data, tab);
                table1.setModel(dtm);
                currentResult = null;
            }
        });
        saveAsFIleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser choix = new JFileChooser();
                    int retour = choix.showOpenDialog(null);
                    if (retour == JFileChooser.APPROVE_OPTION) {
                        choix.getSelectedFile().getName();
                        choix.getSelectedFile().getAbsolutePath();
                        jdbcUtils.saveAsFile(currentResult, choix.getSelectedFile());
                        JOptionPane.showMessageDialog(null, "File Saved!", "File", JOptionPane.INFORMATION_MESSAGE);
                    } else ;

                } catch (SQLException ex) {

                }

            }
        });
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (jdbcUtils.getFileUtils().getMyFile() != null) {
                        jdbcUtils.uploadUserTableContent("ad_user_content.txt");
                    } else
                        JOptionPane.showMessageDialog(null, "Please choose a file or save one!", "Error", JOptionPane.ERROR_MESSAGE);


                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jdbcUtils.getFileUtils().getMyFile() != null) {
                    jdbcUtils.addManualRow();
                    JOptionPane.showMessageDialog(null, "Row Added!", "Succes", JOptionPane.INFORMATION_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(null, "Please choose a file or save one!", "Error", JOptionPane.ERROR_MESSAGE);

            }
        });

        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jdbcUtils.getFileUtils().getMyFile() != null) {
                    FileEditor fileEditor = new FileEditor(jdbcUtils);
                } else
                    JOptionPane.showMessageDialog(null, "Please choose a file or save one!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        exportToDBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jdbcUtils.getFileUtils().getMyFile() != null)
                    jdbcUtils.importFromTxt();
                else
                    JOptionPane.showMessageDialog(null, "Please choose a file or save one!", "Error", JOptionPane.ERROR_MESSAGE);


            }
        });
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser choix = new JFileChooser();
                int retour = choix.showOpenDialog(null);
                if (retour == JFileChooser.APPROVE_OPTION) {
                    choix.getSelectedFile().getName();
                    choix.getSelectedFile().getAbsolutePath();
                    jdbcUtils.getFileUtils().setMyFile(choix.getSelectedFile());
                    JOptionPane.showMessageDialog(null, "File Saved!", "File", JOptionPane.INFORMATION_MESSAGE);
                } else ;
            }
        });
    }


    private void initTable(String query) throws SQLException {
        ResultSet resultSet = jdbcUtils.getUser(query);
        currentResult = resultSet;
        ResultSetMetaData resultMd = resultSet.getMetaData();
        String tab[] = new String[resultMd.getColumnCount()];
        for (int i = 1; i < resultMd.getColumnCount() + 1; i++) {
            tab[i - 1] = resultMd.getColumnName(i);
        }
        resultSet.last();
        Object[][] data = new Object[resultSet.getRow()][resultMd.getColumnCount()];
        resultSet.beforeFirst();
        int i = 0;
        while (resultSet.next()) {
            for (int j = 1; j < tab.length + 1; j++) {
                data[i][j - 1] = resultSet.getObject(j);
            }
            i++;
        }
        DefaultTableModel dtm = new DefaultTableModel(data, tab);
        table1.setModel(dtm);
    }


}
