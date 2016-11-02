/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MicAudioSystem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author abaza
 */
public class lifeAudio {

    boolean stopaudioCapture = false;
    ByteArrayOutputStream Mic = null;
    AudioFormat adFormat = null;
    TargetDataLine MicSource = null;
    AudioInputStream InputStream = null;
    SourceDataLine sourceLine = null;
    public String ServerIPAddress = "";
    public int ServerPort = 0;
    
    
    public void Status(boolean SwitchKey , String IP , int Port) {
        this.ServerIPAddress = IP;
        this.ServerPort = Port;
        
        if (SwitchKey) {
            captureAudio();
        } else {
            MicSource.stop();
            CaptureThread.interrupted();
        }
    }

    private void CallBack(){
        System.gc();
        adFormat = null;
        if(MicSource.isActive()){
            MicSource.stop();
        }
        MicSource = null;
        captureAudio();
    }
    private AudioFormat getAudioFormat() {
        float sampleRate = 16000.0F;
        int sampleInbits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleInbits, channels, signed, bigEndian);
    }

    private void captureAudio() {
        try {
            adFormat = getAudioFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, adFormat);
            MicSource = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
            MicSource.open(adFormat);
            MicSource.start();

            Thread thrcaptureThread = new Thread(new CaptureThread());
            thrcaptureThread.start();
        } catch (Exception e) {
            CallBack();
        }
    }

    class CaptureThread extends Thread {

        byte tempBuffer[] = new byte[MicSource.getBufferSize()];

        @Override
        public void run() {

            Mic = new ByteArrayOutputStream();
            stopaudioCapture = false;
            try {
                DatagramSocket clientSocket = new DatagramSocket();
                InetAddress IPAddress = InetAddress.getByName(lifeAudio.this.ServerIPAddress);

                while (!stopaudioCapture) {
                    int cnt = MicSource.read(tempBuffer, 0, MicSource.getBufferSize());
//                    MicSource.drain();
                    if (cnt > 0) {
                        DatagramPacket sendPacket = new DatagramPacket(tempBuffer, tempBuffer.length, IPAddress,lifeAudio.this.ServerPort);
                        clientSocket.send(sendPacket); //Send Mic Input Sound
                        
                        Mic.write(tempBuffer, 0, cnt);
                        tempBuffer = new byte[MicSource.getBufferSize()];
                        System.gc();
                    }
                }
                Mic.close();
            } catch (NumberFormatException | IOException e) {
                MicSource.stop();
                CaptureThread.interrupted();
            }
        }
    }
}
