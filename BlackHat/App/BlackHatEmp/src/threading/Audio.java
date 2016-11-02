/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author abaza
 */
public class Audio implements Runnable {

    private float peak = 0f;
    private ByteArrayOutputStream out = null;
    TargetDataLine line;
    protected Thread thread;
    String errStr;
    double duration, seconds;
    public AudioInputStream audioInputStream;

    public void start() {
        errStr = null;
        thread = new Thread(this);
        thread.setName("AudioCapture");
        thread.start();
    }

    public void setVolume(float volume) {
        if (volume > 6) {
            return;
        }
        Mixer mixer = null;
        float lvolume = volume * 10000;
        for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
            try {
                mixer = AudioSystem.getMixer(mixerInfo);
                if (!mixer.isOpen()) {
                    try {
                        mixer.open();
                    } catch (LineUnavailableException ex) {
                        mixer = null;
                        return;
                    }
                }
            } catch (java.lang.IllegalArgumentException e) {
                mixer = null;
                return;
            }

            for (Line.Info lineInfo : mixer.getSourceLineInfo()) {
                try {
                    Line linemixer = mixer.getLine(lineInfo);
                    if (linemixer instanceof Clip) {
                        continue;
                    }
                    linemixer.open();
                    if (linemixer.isControlSupported(FloatControl.Type.VOLUME)) {
                        FloatControl control = (FloatControl) linemixer.getControl(FloatControl.Type.VOLUME);
                        if (lvolume <= control.getMaximum()) {
                            control.setValue(lvolume);
                        }
                    }
                } catch (LineUnavailableException e) {
                    mixer = null;
                    return;
                }
            }
        }
    }

    public float getVolume() {
        //mute 10261.0
        //min 12561.0
        Mixer mixer = null;
        for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
            try {
                mixer = AudioSystem.getMixer(mixerInfo);
                if (!mixer.isOpen()) {
                    try {
                        mixer.open();
                    } catch (LineUnavailableException ex) {
                        mixer = null;
                        return 0;
                    }
                }
            } catch (java.lang.IllegalArgumentException e) {
                mixer = null;
                return 0;
            }

            for (Line.Info lineInfo : mixer.getSourceLineInfo()) {
                try {
                    Line linemixer = mixer.getLine(lineInfo);
                    if (linemixer instanceof Clip) {
                        continue;
                    }
                    linemixer.open();
                    if (linemixer.isControlSupported(FloatControl.Type.VOLUME)) {
                        FloatControl control = (FloatControl) linemixer.getControl(FloatControl.Type.VOLUME);
                        if ((control.getValue() >= 10261.0) || (control.getValue() >= 12561.0)) {
                            return control.getValue();
                        }
                    }
                } catch (LineUnavailableException e) {
                    mixer = null;
                    return 0;
                }
            }
        }
        return 0;
    }

    public void getLevel(byte[] data, int numBytesRead) {
        int h = 100;
        int p = Math.round(peak * (h - 2));
        if ((p >= 8 && p <= 15)) {
            out.write(data, 0, numBytesRead);
//            if (getVolume() != 0) {
//                setVolume(2.5f);
//            }
            if (blackhatemp.BlackHatEmp.Debug) {
                System.err.println("Low Sound PEAK:" + p);
            }
        } else if ((p >= 16 && p <= 40)) {
            out.write(data, 0, numBytesRead);
//            if (getVolume() != 0) {
//                setVolume(2f);
//            }
            if (blackhatemp.BlackHatEmp.Debug) {
                System.err.println("Normal Sound PEAK:" + p);
            }
        } else if ((p >= 41 && p <= 98)) {
            out.write(data, 0, numBytesRead);
//            if (getVolume() != 0) {
//                setVolume(1.5f);
//            }
            if (blackhatemp.BlackHatEmp.Debug) {
                System.err.println("Hige Sound PEAK:" + p);
            }
        }
    }

    public AudioInputStream stop() {
        thread = null;
        if (blackhatemp.BlackHatEmp.Debug) {
            System.err.println("Stop Audio");
        }
        return audioInputStream;
    }

    private void shutDown(String message) {
        if ((errStr = message) != null && thread != null) {
            thread = null;
            if (blackhatemp.BlackHatEmp.Debug) {
                System.err.println(errStr);
            }
        }
    }

    private AudioFormat getAudioFormat() {
        float sampleRate = 16000.0f; //44100f;
        int sampleInbits = 16;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleInbits, channels, signed, bigEndian);
    }

    @Override
    public synchronized void run() {
        duration = 0;
        audioInputStream = null;
        AudioFormat format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        if (!AudioSystem.isLineSupported(info)) {
            shutDown("Line matching " + info + " not supported.");
            return;
        }

        // get and open the target data line for capture.
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);

            line.open(format, line.getBufferSize());
        } catch (LineUnavailableException ex) {
            shutDown("Unable to open the line: " + ex);
            return;
        } catch (SecurityException ex) {
            shutDown(ex.toString());
            return;
        } catch (Exception ex) {
            shutDown(ex.toString());
            return;
        }

        // play back the captured audio data
        out = new ByteArrayOutputStream();
        int frameSizeInBytes = format.getFrameSize();
        int bufferLengthInFrames = line.getBufferSize() / 8;
        int bufferLengthInBytes = bufferLengthInFrames * frameSizeInBytes;
        byte[] data = new byte[bufferLengthInBytes];
        float[] samples = new float[line.getBufferSize() / 2];

        float lastPeak = 0f;
        line.start();
        int b;
        while (thread != null) {
            if ((b = line.read(data, 0, bufferLengthInBytes)) == -1) {
                break;
            }
            // convert bytes to samples here
            for (int i = 0, s = 0; i < b;) {
                int sample = 0;

                sample |= data[i++] & 0xFF; // (reverse these two lines
                sample |= data[i++] << 8;   //  if the format is big endian)

                // normalize to range of +/-1.0f
                samples[s++] = sample / 32768f;
            }

            float rms = 0f;
            float lpeak = 0f;
            for (float sample : samples) {

                float abs = Math.abs(sample);
                if (abs > lpeak) {
                    lpeak = abs;
                }

                rms += sample * sample;
            }

            rms = (float) Math.sqrt(rms / samples.length);

            if (lastPeak > lpeak) {
                lpeak = lastPeak * 0.875f;
            }

            lastPeak = lpeak;
            this.peak = Math.abs(lpeak);
            getLevel(data, b);
        }

        // we reached the end of the stream.  stop and close the line.
        line.stop();
        line.close();
        line = null;

        // stop and close the output stream
        try {
            out.flush();
            out.close();
        } catch (IOException ex) {
        }

        // load bytes into the audio input stream for playback
        byte audioBytes[] = out.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
        audioInputStream = new AudioInputStream(bais, format, audioBytes.length / frameSizeInBytes);

        long milliseconds = (long) ((audioInputStream.getFrameLength() * 1000) / format.getFrameRate());
        duration = milliseconds / 1000.0;

    }

}
