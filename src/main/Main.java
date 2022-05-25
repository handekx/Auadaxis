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
    }

}
