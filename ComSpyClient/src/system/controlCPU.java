/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import Comman.Tool;
import java.io.IOException;

/**
 *
 * @author abaza
 */
public class controlCPU {

    Comman.Tool CTool = new Tool();

    public void doThis(int Key, int time) {
        try {
            switch (Key) {
                case 1:
                    CTool.Log("Shutdone form Admin", "COMPY");
                    Runtime.getRuntime().exec("shutdown -s");
                    break;
                case 2:
                    CTool.Log("Restart form Admin", "COMPY");
                    Runtime.getRuntime().exec("shutdown -r");
                    break;
                case 3:
                    CTool.Log("Shutdown after " + String.valueOf(time) + " form Admin", "COMPY");
                    System.out.println("Shutdown after " + String.valueOf(time));
                    Runtime.getRuntime().exec("shutdown -s -t " + String.valueOf(time));
                    break;
                case 4:
                    CTool.Log("Restart after " + String.valueOf(time) + " form Admin", "COMPY");
                    Runtime.getRuntime().exec("shutdown -r -t " + String.valueOf(time));
                    break;
            }
        } catch (IOException ex) {
        }
    }
}
