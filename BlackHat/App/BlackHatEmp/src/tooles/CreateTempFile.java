/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tooles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abaza
 */
public class CreateTempFile {

    public File createTempDir(int forhow, int Type) throws FileNotFoundException {
        try {
            CreateDownloadFile();
            String DownloadFile = null;

            String FileName;
            switch (Type) {
                case 1://Image
                    DownloadFile = CreateImagesFile();
                    break;
                case 2://Video
                    DownloadFile = CreateVideoFile();
                    break;
                case 3://Camera
                    DownloadFile = CreateCameraFile();
                    break;
                case 4:
                    DownloadFile = AudioFile();
                    break;
            }

            File baseDir = new File(DownloadFile);
            Random R = new Random();
            String Extintion = null;
            switch (forhow) {
                case 0:
                    Extintion = ".wav";
                    break;
                case 1:
                    Extintion = ".mp4";//cap
                    break;
                case 2:
                    Extintion = ".gif";
                    break;
                case 3:
                    Extintion = ".zip";
                    break;
            }
            FileName = baseDir.getAbsolutePath() + File.separator + "SystemFile_" + String.valueOf(R.nextInt()) + Extintion;
            File New = new File(FileName);
            New.createNewFile();
            return new File(FileName);
        } catch (IOException ex) {
            Logger.getLogger(CreateTempFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String CreateDownloadFile() {
        File theDir = new File("Download");

// if the directory does not exist, create it
        if (!theDir.exists()) {
            boolean result = false;
            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
            }
            if (result) {
                return theDir.getPath() + File.separator;
            } else {
                return null;
            }
        } else {
            return theDir.getPath() + File.separator;
        }
    }

    public String CreateImagesFile() {
        File theDir = new File("Download" + File.separator + "Images");

// if the directory does not exist, create it
        if (!theDir.exists()) {
            boolean result = false;
            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
            }
            if (result) {
                return theDir.getPath() + File.separator;
            } else {
                return null;
            }
        } else {
            return theDir.getPath() + File.separator;
        }
    }

    public String CreateVideoFile() {
        File theDir = new File("Download" + File.separator + "Video");

// if the directory does not exist, create it
        if (!theDir.exists()) {
            boolean result = false;
            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
            }
            if (result) {
                return theDir.getPath() + File.separator;
            } else {
                return null;
            }
        } else {
            return theDir.getPath() + File.separator;
        }
    }

    public String CreateCameraFile() {
        File theDir = new File("Download" + File.separator + "Camira");

// if the directory does not exist, create it
        if (!theDir.exists()) {
            boolean result = false;
            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
            }
            if (result) {
                return theDir.getPath() + File.separator;
            } else {
                return null;
            }
        } else {
            return theDir.getPath() + File.separator;
        }
    }

    public String AudioFile() {
        File theDir = new File("Download" + File.separator + "Audio");

// if the directory does not exist, create it
        if (!theDir.exists()) {
            boolean result = false;
            try {
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
            }
            if (result) {
                return theDir.getPath() + File.separator;
            } else {
                return null;
            }
        } else {
            return theDir.getPath() + File.separator;
        }
    }
}
