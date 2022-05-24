package main;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.nio.file.Files;

public class FtpClient {
    FTPClient ftp;



    public FtpClient() throws Exception {
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        try {
            ftp.connect("192.168.30.230", 21);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("FTP URL is:" + ftp.getDefaultPort());
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login("audaxis", "audaxis");
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }


    public void uploadFTPFile(String localFileFullName, String fileName, String hostDir)
            throws Exception {
        try {
            File f = new File(localFileFullName);
            InputStream input = Files.newInputStream(f.toPath());

            this.ftp.storeFile(hostDir+fileName, input);
        } catch (Exception e) {

        }
    }


}
