package GUI;

import main.JDBCUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
                    jdbcUtils.saveAsFile(currentResult);
                    JOptionPane.showMessageDialog (null, "File Saved!", "File", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {

                }

            }
        });
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jdbcUtils.uploadUserTableContent("ad_user_content.txt");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        addRowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jdbcUtils.addManualRow();
            }
        });

        previewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileEditor fileEditor = new FileEditor(jdbcUtils);
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

                System.out.println(resultSet.getObject(1));
                for (int j = 1; j < tab.length + 1; j++) {

                    data[i][j-1] = resultSet.getObject(j);
                }
                i++;


        }
        DefaultTableModel dtm = new DefaultTableModel(data, tab);

        table1.setModel(dtm);

    }


}
