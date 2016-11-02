/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.awt.Robot;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author abaza
 */
class ReceiveEvents extends Thread {

    Socket socket = null;
    Robot robot = null;
    boolean continueLoop = true;

    public ReceiveEvents(Socket sc, Robot robot) {
        this.socket = sc;
        this.robot = robot;
        start();
    }

    @Override
    public void run() {
        try {
            Scanner scanner = null;

            scanner = new Scanner(socket.getInputStream());
            
            while (continueLoop) {
                if (scanner.hasNext()) {
                    int command = scanner.nextInt();
                    switch (command) {
                        case -1:
                            robot.mousePress(scanner.nextInt());
                            break;
                        case -2:
                            robot.mouseRelease(scanner.nextInt());
                            break;
                        case -3:
                            robot.mousePress(scanner.nextInt());
                            break;
                        case -4:
                            robot.mouseRelease(scanner.nextInt());
                            break;
                        case -5:
                            robot.mouseMove(scanner.nextInt(), scanner.nextInt());
                            break;
                        case -6:
                            int type = scanner.nextInt();
                            robot.keyPress(type);
                            robot.keyRelease(type);
                            robot.waitForIdle();
                            break;
                    }
                }
            }
        } catch (IOException | java.util.NoSuchElementException ex) {

        }
    }

}
