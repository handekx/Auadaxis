package GUI;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.IntelliJTheme;
import main.FileUtils;
import main.FtpClient;
import main.JDBCUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




/*
 * Created by JFormDesigner on Tue May 24 08:15:46 CET 2022
 */


/**
 * @author unknown
 */


public class MainUI extends JFrame {
    public MainUI() {
        setUIFont (new javax.swing.plaf.FontUIResource("Open Sans",Font.PLAIN,14));
        initComponents();
        this.setSize(350, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void initComponents() {

        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents


        panel12 = new JPanel();
        panel8 = new JPanel();
        label6 = new JLabel();
        textField5 = new JTextField();
        label7 = new JLabel();
        textField6 = new JTextField();
        label8 = new JLabel();
        textField7 = new JTextField();
        label9 = new JLabel();
        textField8 = new JTextField();
        label10 = new JLabel();
        textField9 = new JTextField();
        button1 = new JButton();
        button2 = new JButton();
        panel9 = new JPanel();
        panel10 = new JPanel();
        panel11 = new JPanel();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== panel12 ========
        {
            panel12.setLayout(new FlowLayout());

        }
        contentPane.add(panel12, BorderLayout.SOUTH);

        //======== panel8 ========
        {
            panel8.setBorder(
                    new TitledBorder("Database Connection"));
            panel8.setLayout(new GridLayout(0, 1));


            //---- label6 ----
            label6.setText("Host");
            panel8.add(label6);
            panel8.add(textField5);

            //---- label7 ----
            label7.setText("Port");
            panel8.add(label7);
            panel8.add(textField6);

            //---- label8 ----
            label8.setText("SID");
            panel8.add(label8);
            panel8.add(textField7);

            //---- label9 ----
            label9.setText("Username");
            panel8.add(label9);
            panel8.add(textField8);

            //---- label10 ----
            label10.setText("Password");
            panel8.add(label10);
            panel8.add(textField9);


            //---- button1 ----
            button1.setText("Test");
            panel12.add(button1);

            //---- button2 ----
            button2.setText("Connect");
            panel12.add(button2);
        }
        contentPane.add(panel8, BorderLayout.CENTER);

        //======== panel9 ========
        {
            panel9.setLayout(new FlowLayout(FlowLayout.CENTER));
        }
        contentPane.add(panel9, BorderLayout.EAST);

        //======== panel10 ========
        {
            panel10.setLayout(new FlowLayout(FlowLayout.CENTER));
        }
        contentPane.add(panel10, BorderLayout.WEST);

        //======== panel11 ========
        {
            panel11.setLayout(new FlowLayout());
        }
        contentPane.add(panel11, BorderLayout.NORTH);
        pack();
        setLocationRelativeTo(getOwner());

        //Button Actions
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("jdbc:oracle:thin:@" + textField5.getText() + ":" + textField6.getText() + ":" + textField7.getText() + "username" + textField8.getText());
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            jdbcUtils = new JDBCUtils("oracle.jdbc.driver.OracleDriver",
                                    "jdbc:oracle:thin:@" + textField5.getText() + ":" + textField6.getText() + ":" + textField7.getText(),
                                    textField8.getText(),
                                    textField9.getText(),
                                    new FileUtils(),
                                    new FtpClient());
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        if (jdbcUtils.connect() == null) {
                            JOptionPane.showMessageDialog(null, "Cannot connect to database",
                                    "Error!", JOptionPane.ERROR_MESSAGE);

                        } else {
                            QueryFrame queryFrame = new QueryFrame(jdbcUtils);
                            MainUI.this.dispose();
                        }
                    }
                });
                t.start();

            }
        });
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }


    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel panel12;
    private JPanel panel8;
    private JLabel label6;
    private JTextField textField5;
    private JLabel label7;
    private JTextField textField6;
    private JLabel label8;
    private JTextField textField7;
    private JLabel label9;
    private JTextField textField8;
    private JLabel label10;
    private JTextField textField9;
    private JButton button1;
    private JButton button2;
    private JPanel panel9;
    private JPanel panel10;
    private JPanel panel11;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
    JDBCUtils jdbcUtils;
}
