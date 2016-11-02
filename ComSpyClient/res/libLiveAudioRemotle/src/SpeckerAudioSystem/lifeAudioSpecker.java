/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpeckerAudioSystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author abaza
 */
public class lifeAudioSpecker extends Thread {

    ByteArrayOutputStream byteOutputStream;
    AudioFormat adFormat;
    TargetDataLine targetDataLine;
    AudioInputStream InputStream;
    SourceDataLine Specker;
    int SIZE_BUFFR = 55000;
    Thread playThread = null;
    private boolean lSpicker = false;
    DatagramSocket serverSocket = null;
    public int ServerPort = 0;

    public void Status(boolean SwitchKey, int Port) {

        this.ServerPort = Port;

        if (SwitchKey) {
            lSpicker = true;
            this.start();
        } else {
            this.interrupt();
        }
    }

    private void Working() throws IOException, LineUnavailableException {
        if (!lSpicker) {
            this.interrupt();
        }
        byte[] receiveData = new byte[SIZE_BUFFR];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket); // Reseverd Mic Input sound 

        byte audioData[] = receivePacket.getData();//byteOutputStream.toByteArray();
        InputStream byteInputStream = new ByteArrayInputStream(audioData);
        if (adFormat == null) {
            adFormat = getAudioFormat();
        }
        InputStream = new AudioInputStream(byteInputStream, adFormat, audioData.length / adFormat.getFrameSize());
        DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, adFormat);
        if (dataLineInfo != null) {
            if (Specker == null) {
                Specker = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                if (!Specker.isOpen()) {
                    Specker.open(adFormat); // Open Mixer to Output Sound
                    Specker.start();
                }
            }

            if (playThread == null) {
                playThread = new Thread(new PlayThread());
                playThread.start();
            } else {
                playThread.interrupt();
                playThread = null;
                playThread = new Thread(new PlayThread());
                playThread.start();
            }

        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new DatagramSocket(ServerPort);
            while (lSpicker) {
                try {
                    Working();
                } catch (IOException | LineUnavailableException ex) {
                    Logger.getLogger(lifeAudioSpecker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(lifeAudioSpecker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    class PlayThread extends Thread {

        byte tempBuffer[] = new byte[SIZE_BUFFR];
        boolean done = false;

        public boolean isDone() {
            return done;
        }

        @Override
        public void run() {
            try {
                int cnt;
                while ((cnt = InputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
                    if (cnt > 0) {
                        Specker.write(tempBuffer, 0, cnt); // Write Sound Data to Spicker
                        Specker.flush();
                        done = true;
                    }
                }
//                tempBuffer = null;
//                done = true;
                return;
            } catch (Exception e) {
//                tempBuffer = null;
//                done = false;
            }
        }

        @Override
        public void interrupt() {
            Specker.drain();
            Specker.close();
        }
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 16000.0F;
        int sampleInbits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleInbits, channels, signed, bigEndian);
    }

    @Override
    public void interrupt() {
        lSpicker = false;
        if (Specker != null) {
            Specker.close();
            Specker = null;
        }
        if (playThread != null) {
            playThread = null;
        }
    }
}
