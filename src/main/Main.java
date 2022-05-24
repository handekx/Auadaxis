package main;

import GUI.MainUI;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.IntelliJTheme;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws Exception {


        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        IntelliJTheme.install(Main.class.getResourceAsStream(
                "arc-theme-orange.theme.json" ) );
        MainUI mainUI = new MainUI();
        /*main.JDBCUtils jdbcUtils = new main.JDBCUtils("oracle.jdbc.driver.OracleDriver",
                "jdbc:oracle:thin:@192.168.30.152:1521:oracledb",
                "haithem",
                "root",
                new main.FileUtils(),
                new main.FtpClient());

        jdbcUtils.getConnection();
        //jdbcUtils.userExist("10000751");
        jdbcUtils.getUser();
        //jdbcUtils.uploadUserTableContent("ad_user_content.txt");
        jdbcUtils.addManualRow();
        jdbcUtils.importFromTxt("ad_user_content.txt");
        jdbcUtils.closeConnection();*/



    }

}
