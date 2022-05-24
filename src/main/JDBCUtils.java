package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class JDBCUtils {
    String className, URL, user, password;
    Connection connection;
    FileUtils fileUtils;
    FtpClient ftpClient;

    public JDBCUtils(String className, String URL, String user, String password, FileUtils fileUtils, FtpClient ftpClient) {
        this.className = className;
        this.URL = URL;
        this.user = user;
        this.password = password;
        this.connection = null;
        this.fileUtils = fileUtils;
        this.ftpClient = ftpClient;
    }

    public Connection connect() {
        //Load the driver class
        try {

            Class.forName(className);

        } catch (ClassNotFoundException ex) {
            System.out.println("Unable to load the class. Terminating the program");

        }
        //get the connection
        try {
            connection = DriverManager.getConnection(URL, user, password);
            System.out.println("Connected to Database!");


        } catch (SQLException ex) {
            System.out.println("Error getting connection: " + ex.getMessage());

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());

        }
        return connection;
    }


    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet getUser(String query) {
        try {
            ResultSet result = connection.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY).executeQuery();
            return result;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        }
    }

    public void saveAsFile(ResultSet resultSet) throws SQLException {
        fileUtils.createFile();
        resultSet.beforeFirst();
        String colName = "";
        for (int j = 1; j < resultSet.getMetaData().getColumnCount() + 1; j++) {
            if (j == resultSet.getMetaData().getColumnCount())
                colName = colName + resultSet.getMetaData().getColumnName(j);
            else colName = colName + resultSet.getMetaData().getColumnName(j) + ";";
        }
        this.fileUtils.writeFile(colName, true);
        while (resultSet.next()) {
            String row = "";
            for (int j = 1; j < resultSet.getMetaData().getColumnCount() + 1; j++) {
                if (j == resultSet.getMetaData().getColumnCount())
                    row = row + resultSet.getObject(j);
                else row = row + resultSet.getObject(j) + ";";

            }
            this.fileUtils.writeFile(row, true);
        }


    }

    public void uploadUserTableContent(String sFile) throws Exception {
        ftpClient.uploadFTPFile(sFile, "ad_user_content.txt", "/home/audaxis/Formation_2022/heithem/");
    }

    public void addManualRow() {
        fileUtils.addManualRow();
    }

    public void importFromTxt(String sfile) {
        File file = new File(sfile);
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            int count = 0;
            while (sc.hasNextLine()) {
                String row = sc.nextLine();
                String[] rowData = row.split(";");
                System.out.println(rowData[0]);
                if (!userExist(rowData[0])) {
                    System.out.println(Arrays.toString(rowData));
                    String query = "INSERT INTO AD_USER(AD_USER_ID, AD_CLIENT_ID, AD_ORG_ID, ISACTIVE, CREATED, CREATEDBY, UPDATED, UPDATEDBY, NAME, ISFULLBPACCESS, NOTIFICATIONTYPE, VALUE) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pst = null;
                    try {
                        count++;
                        pst = connection.prepareStatement(query);

                        pst.setBigDecimal(1, BigDecimal.valueOf(Double.valueOf(rowData[0])));
                        pst.setBigDecimal(2, BigDecimal.valueOf(Double.valueOf(rowData[1])));
                        pst.setBigDecimal(3, BigDecimal.valueOf(Double.valueOf(rowData[2])));
                        pst.setString(4, rowData[3]);
                        pst.setTimestamp(5, java.sql.Timestamp.valueOf(rowData[4]));
                        pst.setBigDecimal(6, BigDecimal.valueOf(Double.valueOf(rowData[5])));
                        pst.setTimestamp(7, java.sql.Timestamp.valueOf(rowData[6]));
                        pst.setBigDecimal(8, BigDecimal.valueOf(Double.valueOf(rowData[7])));
                        pst.setString(9, rowData[8]);
                        pst.setString(10, rowData[31]);
                        pst.setString(11, rowData[33]);
                        pst.setString(12, rowData[35]);
                        pst.executeQuery();
                    } catch (SQLException e) {
                        System.out.println("Error " + e + "  " + count);
                    }
                }


            }

        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public boolean userExist(String userID) throws SQLException {
        PreparedStatement pst = null;
        pst = connection.prepareStatement("SELECT * FROM AD_USER WHERE AD_USER_ID = ?");
        pst.setBigDecimal(1, BigDecimal.valueOf(Double.valueOf(userID)));
        ResultSet result = pst.executeQuery();
        return !(result.next() == false);
    }
}
