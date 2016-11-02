/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tooles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author abaza
 */
public class BlackHatZIP {

    private static String Path;

    void BlackHatZIP() {

    }

    public void init(int Type) {
        try {
            CreateTempFile CTF = new CreateTempFile();
            Path = CTF.createTempDir(3, Type).getAbsolutePath();
        } catch (FileNotFoundException ex) {
        }
    }

    public boolean InsertToZip(String PathFile, int Type) {
        if (Path == null) {
            init(Type);
        }
        File ZipFile = new File(Path);
        if (ZipFile.exists()) {
            try {
                addFilesToExistingZip(ZipFile, new File(PathFile));
                return true;
            } catch (IOException ex) {
                return false;
            }

        } else {
            try (FileOutputStream fos = new FileOutputStream(ZipFile); ZipOutputStream Zos = new ZipOutputStream(fos)) {

                File file = new File(PathFile);
                try (FileInputStream fis = new FileInputStream(file)) {
                    String[] FileName = PathFile.split(File.separator);
                    ZipEntry zipEntry = new ZipEntry(FileName[FileName.length - 1]);
                    Zos.putNextEntry(zipEntry);
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        Zos.write(bytes, 0, length);
                    }
                    Zos.closeEntry();

                }
                return true;
            } catch (IOException ex) {
                return false;
            }
        }
    }

    private void addFilesToExistingZip(File zipFile, File files) throws IOException {
        // get a temp file
        File tempFile = File.createTempFile(zipFile.getName(), null);
        // delete it, otherwise you cannot rename your existing zip to it.
        tempFile.delete();

        boolean renameOk = zipFile.renameTo(tempFile);
        if (!renameOk) {
            if (blackhatemp.BlackHatEmp.Debug) {
                System.err.println("could not rename the file " + zipFile.getAbsolutePath() + " to " + tempFile.getAbsolutePath());
            }
        }
        byte[] buf = new byte[1024];

        ZipOutputStream out;
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(tempFile))) {
            out = new ZipOutputStream(new FileOutputStream(zipFile));
            ZipEntry entry = zin.getNextEntry();
            while (entry != null) {
                String name = entry.getName();
                boolean notInFiles = true;
                if (files.getName().equals(name)) {
                    notInFiles = false;
                    break;
                }
                if (notInFiles) {
                    // Add ZIP entry to output stream.
                    out.putNextEntry(new ZipEntry(name));
                    // Transfer bytes from the ZIP file to the output file
                    int len;
                    while ((len = zin.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                }
                entry = zin.getNextEntry();
            }
            // Close the streams
        }
        // Compress the files
        // Add ZIP entry to output stream.
        try (InputStream in = new FileInputStream(files)) {
            // Add ZIP entry to output stream.
            out.putNextEntry(new ZipEntry(files.getName()));
            // Transfer bytes from the file to the ZIP file
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            // Complete the entry
            out.closeEntry();
        }
        // Complete the ZIP file
        out.close();
        tempFile.delete();
    }

    public boolean CreateZip_AndInsertDir(String[] DirName, String ZipFile) throws FileNotFoundException, IOException {
        try (FileOutputStream fos = new FileOutputStream(ZipFile); ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (String fileName : DirName) {

                File file = new File(fileName);
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(fileName);
                    zos.putNextEntry(zipEntry);

                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zos.write(bytes, 0, length);
                    }

                    zos.closeEntry();
                }
            }
        }
        return true;
    }
}
