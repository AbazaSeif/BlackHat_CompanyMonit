/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Camera;

import Database.Conf;
import Window.MainGUI;
import static blackhat.BlackHat.webcam;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamMotionDetector;
import com.github.sarxos.webcam.WebcamResolution;
import java.sql.SQLException;

/**
 *
 * @author abaza
 */
public class Motion implements Runnable {

    WebcamMotionDetector detector;
    private int Action = -1;
    private MainGUI MainClass = null;

    public Motion(MainGUI Class) {
        try {
            this.MainClass = Class;

            webcam = Webcam.getDefault();
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            Conf co = Conf.queryFirstRow(blackhat.BlackHat.ConnectDB, "");
            int Delay = Integer.parseInt((co.getStart_action_after_delay() == null) ? "2" : co.getStart_action_after_delay().toString());
            Delay *= 1000;

            Action = Integer.parseInt((co.getAction_whin_motion_stop() == null) ? "-1" : co.getAction_whin_motion_stop().toString());

            detector = new WebcamMotionDetector(webcam);
            detector.setInterval(100); // one check per 100 ms
            detector.setInertia(Delay); // keep "motion" state for user second
            detector.start();
        } catch (SQLException ex) {
        }
    }

    @Override
    public void run() {
        boolean motion = false;
        long now = 0;

        while (true) {
            now = System.currentTimeMillis();
            if (detector.isMotion()) {
                if (!motion) {
                    motion = true;
                    this.StartMotion();
                    if (blackhat.BlackHat.Debug) {
                        System.out.println("Start Motion Action");
                    }
                }
            } else {
                if (motion) {
                    motion = false;
                    this.StopMotion();
                    if (blackhat.BlackHat.Debug) {
                        System.out.println("Stop Motion Action");
                    }
                }
            }
            try {
                Thread.sleep(10); // must be smaller than interval
            } catch (InterruptedException e) {
            }
        }
    }

    private void StopMotion() {

        switch (Action) {
            case 0:
                if (!this.MainClass.isAction()) {
                    this.MainClass.EndApplication();
                }
                break;
            case 1:
                if (!this.MainClass.isAction()) {
                    this.MainClass.Logout();
                }
                break;
            case 2:
                if (!this.MainClass.isAction()) {
                    this.MainClass.ConnectionLocal();
                    break;
                }

        }
    }

    private void StartMotion() {
        this.MainClass.doAction();
    }

}
